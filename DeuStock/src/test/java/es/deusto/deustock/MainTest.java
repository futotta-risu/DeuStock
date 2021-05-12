package es.deusto.deustock;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void testMain() throws IOException {
        // When
        HttpServer mockServer = mock(HttpServer.class);
        when(mockServer.shutdown()).thenReturn(null);

        String example = "some input line";
        InputStream stream = new ByteArrayInputStream((example+"\n")
                .getBytes(StandardCharsets.UTF_8));
        InputStream stdin = System.in;
        System.setIn(stream);

        // When

        // Then
        try(MockedStatic<GrizzlyHttpServerFactory> factory = mockStatic(GrizzlyHttpServerFactory.class)){
            factory.when(() -> GrizzlyHttpServerFactory.createHttpServer(any(),any())).thenReturn(mockServer);
            assertDoesNotThrow(() -> Main.main(new String[]{}));
        }

        System.setIn(stdin);
    }
}