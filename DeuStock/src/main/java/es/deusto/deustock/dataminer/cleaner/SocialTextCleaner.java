package es.deusto.deustock.dataminer.cleaner;

import es.deusto.deustock.data.SocialNetworkMessage;

import java.util.Collection;
import java.util.List;

public class SocialTextCleaner {

    public final static String charFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";

    public static String removeInvalidChars(String txt) {
        return txt.replaceAll(charFilter, "");
    }

    public static String removeURLS(String txt) {
        String urlRegex = "(https?://)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)";
        return txt.replaceAll(urlRegex, "");
    }

    public static String removeHashtags(String txt) {
        return txt.replaceAll("#", "");
    }

    /**
     * Changes usernames based on the format "@[^\\s]+" to generic "Tom"
     */
    public static String removeUsernames(String txt) {
        return txt.replaceAll("@[\\S]+", "Tom");
    }

    public static String removeExtraSpaces(String txt) {
        return txt.trim().replaceAll("[\\s]+", " ");
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
        for (int i = 0; i < messages.size(); i++)
            messages.set(i, clean(messages.get(i)));
    }

    public static void clean(Collection<SocialNetworkMessage> messages) {
        for (SocialNetworkMessage snm : messages)
            snm.setMessage(clean(snm.getMessage()));
    }


}