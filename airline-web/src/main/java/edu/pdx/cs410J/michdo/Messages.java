package edu.pdx.cs410J.michdo;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{

    /**
     * Used to print missing parameter
     * @param parameterName name of the parameter
     * @return A string declaring that the parameter is missing
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

}
