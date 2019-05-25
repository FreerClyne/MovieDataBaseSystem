package helperLibrary;

/**
 * StandardMessage provides messages displayed on UI
 * according to users' input
 * 
 * @author Clyne
 * @version 25-May-2019
 */

public class StandardMessage 
{
    public static void errorMessage(String errorType)
    {
        switch (errorType)
        {
            case "number":
                System.out.println("\nError: Invalid number!");
                break;
            case "empty":
                System.out.println("\nError: Empty input!");
                break;
            case "movie":
                System.out.println("\nError: Movie doesn't exist!");
                break;
            case "actorNum":
                System.out.println("\nError: Need at least one actor!");
                break;
            case "rating":
                System.out.println("\nError: Invalid rating number!");
                break;
            case "detailLength":
                System.out.println("\nError: Wrong number of information! Must be 6");
                System.out.println("and seperate by comma!");
                break;
            case "detailEmpty":
                System.out.println("\nError: Title, director and rating cannot be empty!");
                break;
            case "movieExists":
                System.out.println("\nError: Movie already exists!");
                break;
            default:
                System.out.println("\nError: Invalid input!");
                break;
        }
        
    }

    public static void successMessage(String successType)
    {
        switch (successType)
        {
            case "add":
                System.out.println("\nMovie successfully added.");
                break;
            case "delete":
                System.out.println("\nMovie successfully deleted.");
                break;
            case "update":
                System.out.println("\nUpdate completed.");
                break;
            default:
                break;
        }
    }

    public static void exitMessage()
    {
        System.out.println("System Exited. Goodbye!");
    }
}