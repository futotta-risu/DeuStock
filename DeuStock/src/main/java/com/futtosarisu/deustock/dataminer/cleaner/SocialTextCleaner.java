package com.futtosarisu.deustock.dataminer.cleaner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SocialTextCleaner {

    public static String clean(String txt){
        String text = txt.trim()
                // remove links
                .replaceAll("http.*?[\\S]+", "")
                // remove usernames
                .replaceAll("@[\\S]+", "")
                // replace hashtags by just words
                .replaceAll("#", "")
                // correct all multiple white spaces to a single white space
                .replaceAll("[\\s]+", " ");
        return text;
    }

    public static List<String> clean(Collection<String> txt){
        return txt.stream().map(c -> clean(c)).collect(Collectors.toList());
    }

}
