package es.deusto.deustock.dataminer.cleaner;

import es.deusto.deustock.data.SocialNetworkMessage;

import java.util.Collection;
import java.util.List;

/**
 * Social text cleaner class with all the functionality for data cleaning.
 *
 * @author Erik B. Terres
 */
public class SocialTextCleaner {

    private final static String charFilter      = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";
    private final static String urlFilter       = "(https?://)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)";
    private final static String usernameFilter  = "@[\\S]+";
    private final static String hashtagFilter   = "#";
    private final static String spaceFilter     = "[\\s]+";

    private SocialTextCleaner(){}

    public static String removeInvalidChars(String txt) {
        return txt.replaceAll(charFilter, "");
    }

    public static String removeURLS(String txt) {
        return txt.replaceAll(urlFilter, "");
    }

    public static String removeHashtags(String txt) {
        return txt.replaceAll(hashtagFilter, "");
    }

    /**
     * Changes usernames based on the format "@[^\\s]+" to generic "Tom"
     */
    public static String removeUsernames(String txt) {
        return txt.replaceAll(usernameFilter, "Tom");
    }

    public static String removeExtraSpaces(String txt) {
        return txt.trim().replaceAll(spaceFilter, " ");
    }

    /**
     * Cleans completely a String so it can be used in NLP tasks
     */
    public static String clean(String txt) {
        String result = removeInvalidChars(txt);
        result = removeURLS(result);
        result = removeHashtags(result);
        result = removeUsernames(result);
        result = removeExtraSpaces(result);
        return result;
    }

    public static void clean(List<String> messages) {
        messages.replaceAll(SocialTextCleaner::clean);
    }

    public static void clean(Collection<SocialNetworkMessage> messages) {
        messages.forEach(c -> c.setMessage(clean(c.getMessage())));
    }


}
