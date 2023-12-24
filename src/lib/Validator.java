package lib;

public class Validator
{
    public static int isNumeric(String string)
    {
        try
        {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }

    public static boolean isStringValid(String string, int maxLen)
    {
        if (string.isEmpty() || string.length() > maxLen)
        {
            return false;
        }

        return true;
    }

    public static boolean isStringSplit(String string)
    {
        try
        {
            String[] strings = string.split(" ");

            return strings.length == 4;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static int[] isCoordsValid(String[] coords)
    {
        int[] parsedCoords = new int[4];
        int index = 0;

        for (String coord: coords)
        {
            int parsedCord = isNumeric(coord);

            if (parsedCord < 0 || parsedCord > 7)
            {
                return null;
            }

            parsedCoords[index++] = parsedCord;
        }

        return parsedCoords;
    }
}