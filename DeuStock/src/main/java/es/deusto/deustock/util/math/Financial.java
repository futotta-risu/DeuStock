package es.deusto.deustock.util.math;

import java.util.List;

/**
 * Static class that contain financial based math functions.
 *
 * @author Erik B. Terres
 */
public class Financial {

    private Financial(){}

    public static < E extends Number & Comparable<E>> double rsi(List<E> list, int n){
        if(n < 2 || list.size() < n){
            throw new IllegalArgumentException("Invalid parameters. List size: " + list.size() + ", n:" + n
            + "\n Minimum n = 2 and n <= list.size()");
        }

        List<E> tail = list.subList(list.size() - n , list.size());

        double positive = 0;

        for(var i = 1; i < n ; i++){
            if(tail.get(i).compareTo(tail.get(i-1)) > 0){
                positive++;
            }
        }

        return positive/(n-1);
    }

}
