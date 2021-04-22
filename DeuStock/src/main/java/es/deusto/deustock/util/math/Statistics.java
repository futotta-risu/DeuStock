package es.deusto.deustock.util.math;

import java.util.Collection;

/**
 * Static class that contain basic statistics functions.
 *
 * @author Erik B. Terres
 */
public class Statistics {

    private Statistics(){}

    /**
     * Returns the mean of a List of Number.
     *
     * @param numbers Collection of Number
     */
    public static double mean(Collection<? extends Number> numbers){
        return numbers.stream()
                .mapToDouble( Number::doubleValue )
                .average()
                .orElse(0);
    }


    /**
     * Returns the variance of a List of Number.
     *
     * @param numbers Collection of Number
     */
    public static double variance(Collection<? extends Number> numbers){
        double mean = mean(numbers);
        return numbers.stream()
                .mapToDouble(Number::doubleValue)
                .map(c -> (c - mean)*(c - mean))
                .average()
                .orElse(0);
    }

    /**
     * Returns the std of a List of Number.
     *
     * @param numbers Collection of Number
     */
    public static double std(Collection<? extends Number> numbers){
        return Math.sqrt(variance(numbers));
    }
}
