package es.deusto.deustock.util.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import static es.deusto.deustock.util.math.Statistics.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("math")
class StatisticsTest {

    private List<Integer> numbers;

    @BeforeEach
    public void setUp(){
        numbers = new ArrayList<>();
        for(int i = 1; i < 11; i++){
            numbers.add(i);
        }
    }

    @Test
    void testMean() {
        assertEquals(5.5, mean(numbers), 0.02);
    }

    @Test
    void testMeanZeroElements() {
        assertEquals(0, mean(new LinkedList<Integer>()), 0.01);
    }

    @Test
    void testVariance() {
        assertEquals(8.25, variance(numbers), 0.02);
    }

    @Test
    void testVarianceZeroElements() {
        assertEquals(0, variance(new LinkedList<Integer>()), 0.02);
    }

    @Test
    void testStd() {
        assertEquals(2.8722813, std(numbers), 0.02);
    }
    @Test
    void testStdZeroElements() {
        assertEquals(0, std(new LinkedList<Integer>()), 0.02);
    }
}