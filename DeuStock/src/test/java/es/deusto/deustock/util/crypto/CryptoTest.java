package es.deusto.deustock.util.crypto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoTest {

    @Test
    @DisplayName("Test getHash returns correct hash")
    void testGetHash() {
        // Given
        String hash = "9B9FAB23AFD71E2FA950A4220EDD9D5F0E8CDE4C79720AE184428759CD77EB8EF69170D9710E0A0BF6BE6D4BBFFDD35119820C860EFDB89A4A05306636302D09";
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