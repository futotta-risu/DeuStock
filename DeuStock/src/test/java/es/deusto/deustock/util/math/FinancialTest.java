package es.deusto.deustock.util.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("math")
class FinancialTest {

    private List<Integer> numbers;

    @BeforeEach
    public void setUp(){
        this.numbers = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            numbers.add(i);
        }
        numbers.add(18);
    }

    @Test
    void rsiDouble(){
        double result = Financial.rsi(this.numbers, 5);

        assertTrue(result > 0.5);
        assertEquals(0.75, result, 0.01);
    }

    @Test
    void rsiFailsOnNegativeN(){
        assertThrows(IllegalArgumentException.class, () -> Financial.rsi(this.numbers, -2));
    }

    @Test
    void rsiFailsOnZeroN(){
        assertThrows(IllegalArgumentException.class, () -> Financial.rsi(this.numbers, 0));
    }

    @Test
    void rsiFailsOnOneN(){
        assertThrows(IllegalArgumentException.class, () -> Financial.rsi(this.numbers, 1));
    }

    @Test
    void rsiFailsOnNBiggerThanListSize(){
        assertThrows(IllegalArgumentException.class, () -> Financial.rsi(this.numbers, 200));
    }

}