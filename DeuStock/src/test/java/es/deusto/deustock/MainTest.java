package es.deusto.deustock;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;

/**
 * @author Erik B. Terres
 */
class MainTest {

    @Test
    void startServer() {
        try(MockedStatic<GrizzlyHttpServerFactory> factory = mockStatic(GrizzlyHttpServerFactory.class)){
            factory.when(() -> GrizzlyHttpServerFactory.createHttpServer(any(),any())).thenAnswer((Answer<Void>) invocation -> null);
            assertDoesNotThrow(Main::startServer);
        }
    }
}