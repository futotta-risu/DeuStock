package es.deusto.deustock.util.math;

import es.deusto.deustock.Main;

import java.util.Collection;

/**
 * @author Erik B. Terres
 */
public class Statistics {

    public static double mean(Collection<? extends Number> numbers){
        return numbers.stream()
                .mapToDouble( Number::doubleValue )
                .average()
                .orElse(0);
    }

    public static double variance(Collection<? extends Number> numbers){
        double mean = mean(numbers);
        return numbers.stream()
                .mapToDouble(Number::doubleValue)
                .map(c -> (c - mean)*(c - mean))
                .average()
                .orElse(0);
    }

    public static double std(Collection<? extends Number> numbers){
        return Math.sqrt(variance(numbers));
    }
}
