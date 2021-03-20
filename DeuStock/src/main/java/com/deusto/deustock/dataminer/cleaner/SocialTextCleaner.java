package com.deusto.deustock.dataminer.cleaner;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SocialTextCleaner {

    public final static String charFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";

    public static String removeInvalidChars(String txt) {
        return txt.replaceAll(charFilter, "");
    }

    public static String removeURLS(String txt) {
        String urlRegex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
        return txt.replaceAll(urlRegex, "");
    }

    public static String removeHashtags(String txt) {
        return txt.replaceAll("#", "");
    }

    /**
     * Changes usernames based on the format "@[^\\s]+" to generic "Tom".
     *
     * @param txt
     * @return
     */
    public static String removeUsernames(String txt) {
        return txt.replaceAll("@[\\S]+", "Tom");
    }

    public static String removeExtraSpaces(String txt) {
        return txt.trim().replaceAll("[\\s]+", " ");
    }

    /**
     * Cleans completely a String so it can be used in NLP tasks
     *
     * @param txt
     * @return
     */
    public static String clean(String txt) {
        String result = removeInvalidChars(txt);
        result = removeURLS(result);
        result = removeHashtags(result);
        result = removeUsernames(result);
        result = removeExtraSpaces(result);
        return result;
    }

    public static List<String> clean(Collection<String> txt) {
        return txt.stream().map(SocialTextCleaner::clean).collect(Collectors.toList());
    }

}
