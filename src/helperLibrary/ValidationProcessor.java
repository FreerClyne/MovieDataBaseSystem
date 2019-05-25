package helperLibrary;

/**
 * ValidationProcessor validates information load 
 * into the system
 * 
 * @author Clyne
 * @version 25-May-2019
 */

public class ValidationProcessor {

    private static final int MIN_RATING = 0;
    private static final int MAX_RATING = 10;

    /**
     * @return the status of whether the input string is 
     * a valid number.
     */
    public static boolean isNumber(String userInput)
    {
        for (int i = 0 ; i < userInput.length(); i++)
        {
            if (userInput.charAt(i) < '0' || userInput.charAt(i) > '9')
            {
                return false;
            }
        }

        return true;
    }
    
    /**
     * @return the status of whether the input string is 
     * empty
     */
    public static boolean isEmpty(String userInput)
    {
        if (userInput == null || userInput.trim().isEmpty())
        {
            return true;
        }

        return false;
    }

    /**
     * @return the status of whether the input rating is
     * valid.
     */
    public static boolean isRating(String userInput)
    {
        if (MIN_RATING <= Integer.parseInt(userInput) && Integer.parseInt(userInput) <= MAX_RATING)
        {
            return true;
        }
        
        return false;
    }
}