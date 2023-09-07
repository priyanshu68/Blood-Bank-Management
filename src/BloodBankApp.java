import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

public class BloodBankApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String URL = "jdbc:mysql://localhost:3306/BloodBank"; 
        String USERNAME = "root"; 
        String PASSWORD = "";
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

            
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            if (connection != null) {
                System.out.println("Connected to the database!");
                System.out.println();
                // You can perform database operations here.
                // Don't forget to close the connection when done.
                
                while (true) {
                	
                	
                    System.out.println("Welcome to Blood Bank Management System");
                    System.out.println("1. Sign Up");
                    System.out.println("2. Log In");
                    System.out.println("3. Exit");
                    System.out.print("Please select an option: ");

                    int choice = sc.nextInt();
                    sc.nextLine(); // Consume the newline character

                    switch (choice) {
                        case 1:
                            signUp(connection);
                            break;
                        case 2:
                            logIn(connection);
                            break;
                        case 3:
                            System.out.println("Exiting...");
                            connection.close();
                            sc.close();
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                    }
                }
                
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }

	}
	
	public static void signUp(Connection connection)
	{
		System.out.println("Sign In");
		
		System.out.println("Please fill up your details");
		
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter your Full Name: ");
            String fullName = sc.nextLine();

            
            
            String username;
             
            boolean isUsernameTaken;
            
            do
            {
            	System.out.println("Enter a username: ");
            	
            	username = sc.nextLine();
            	isUsernameTaken = checkUser(connection,username);
            	if(isUsernameTaken)
            	{
            		System.out.println("Username already taken. Try another");
            	}
            }while(isUsernameTaken);
            
            
            System.out.println("Enter your password: ");
            String password = sc.nextLine();
            
            String query = "INSERT INTO users (full_name,username,password) VALUES (?,?,?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, fullName);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);

                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration failed.");
                }
            }
         catch (SQLException e) {
            System.err.println("Sign-up error: " + e.getMessage());
        }
            
      
		
	}
	public static boolean checkUser(Connection connection ,String username)
	{
		String query ="SELECT * FROM users WHERE username= ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setString(1,username);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();
			
		} catch (SQLException e) {
			System.err.println("Error checking username availability: " + e.getMessage());
            return true;
		}
	}
	public static void logIn(Connection connection)
	{
		System.out.println("Log In");
		
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("Username: ");
		
		String username = sc.nextLine();
		
		System.out.println("Password");
		
		String password = sc.nextLine();
		
		
		String query = "SELECT * FROM users WHERE username =?";
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(query))
		{
			preparedStatement.setString(1,username);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next())
			{
				String passDb=resultSet.getString("password");
				if (password.equals(passDb)) {
                    System.out.println("Logged In Successfully");
                    displayUserMenu(connection);
                    //  allow access to the system
                    
                    System.out.println("Want to be a donor or blood in need?");
                } else {
                    System.out.println("Authentication failed.");
                }
			}
			else
			{
				System.out.println("User not found");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Login-in error: " + e.getMessage());
		}
	}


public static void displayUserMenu(Connection connection) {
    Scanner sc = new Scanner(System.in);

    while (true) {
        System.out.println("User Menu:");
        System.out.println("1. View Donor Information");
        System.out.println("2. View Recipient Information");
        System.out.println("3. Schedule Blood Donation");
        System.out.println("4. Search for Donors");
        System.out.println("5. Logout");
        System.out.print("Please select an option: ");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                viewDonorInformation(connection);
                break;
            case 2:
                viewRecipientInformation(connection);
                break;
            case 3:
                scheduleBloodDonation(connection);
                break;
            case 4:
                searchForDonors(connection);
                break;
            case 5:
                System.out.println("Logging out...");
                return; // Exit the user menu and return to the main menu
            default:
                System.out.println("Invalid choice. Please select a valid option.");
        }
    }
}

public static void viewDonorInformation(Connection connection) {
    // Implement the logic to view donor information here
}

public static void viewRecipientInformation(Connection connection) {
    // Implement the logic to view recipient information here
}

public static void scheduleBloodDonation(Connection connection) {
    // Implement the logic to schedule blood donation here
}

public static void searchForDonors(Connection connection) {
    // Implement the logic to search for donors here
}
}