package es.deusto.deustock.util.crypto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoTest {

    @Test
    @DisplayName("Test getHash returns correct hash")
    void testGetHash() {
        // Given
        String hash = "35454B055CC325EA1AF2126E27707052";
        String testHash = "ILoveJava";

        // When
        String result = Crypto.getHash(testHash);

        // Then
        assertEquals(hash, result);

    }

    @Test
    @DisplayName("Test getHash throws NullPointerException on null input")
    void testGetHashThrowsNullPointerExceptionOnNull() {
        // Given

        // When

        // Then
        assertThrows(
                NullPointerException.class,
                () -> Crypto.getHash(null)
        );

    }
}