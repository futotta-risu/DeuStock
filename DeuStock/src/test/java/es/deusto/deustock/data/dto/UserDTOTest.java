package es.deusto.deustock.data.dto;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Erik B. Terres
 */
class UserDTOTest {

    @Test
    @DisplayName("Test Constructor is not null")
    void testConstructorNotNull(){
        UserDTO userDTO = new UserDTO();
        assertNotNull(userDTO);
    }

    @Test
    @DisplayName("Test Constructor does not throw exception")
    void testConstructorDoesNotThrowException(){
        assertDoesNotThrow(UserDTO::new);
    }

    @Test
    @DisplayName("Test Format returns correct String")
    void testFormatReturnsCorrectFormat(){
        UserDTO userDTO = new UserDTO()
                .setUsername("TestUsername")
                .setFullName("Test Username");

        assertEquals("Test Username(TestUsername)", userDTO.format());
    }

    @Test
    @DisplayName("Test Format does not throw exception on null values")
    void testFormatDoesNotThrowExceptionOnNullValues(){
        assertDoesNotThrow( () -> new UserDTO().format() );
    }

}
