package es.deusto.deustock.resources.auth;

import es.deusto.deustock.services.auth.AuthService;
import es.deusto.deustock.services.auth.exceptions.InvalidTokenException;
import es.deusto.deustock.services.investment.stock.exceptions.InvalidStockQueryDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthenticationFilterTest {

    private AuthService mockAuthService;
    private ContainerRequestContext mockContainerRequestContext;
    private AuthenticationFilter filter;

    @BeforeEach
    public void setUp(){
        filter = new AuthenticationFilter();
        mockAuthService = mock(AuthService.class);
        mockContainerRequestContext = mock(ContainerRequestContext.class);

        filter.setAuthService(mockAuthService);
    }

    @Test
    void testFilterDoesNotThrow() {
        when(mockContainerRequestContext.getHeaderString(anyString())).thenReturn("Bearer TestToken");
        when(mockAuthService.validateToken(anyString())).thenReturn("TestUsername");

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        when(mockSecurityContext.isSecure()).thenReturn(true);
        when(mockContainerRequestContext.getSecurityContext()).thenReturn(mockSecurityContext);
        doNothing().when(mockContainerRequestContext).setSecurityContext(any());

        assertDoesNotThrow( () -> filter.filter(mockContainerRequestContext));
    }

    @Test
    void testFilterDoesNotThrowOnAbortedCauseNull(){
        when(mockContainerRequestContext.getHeaderString(anyString())).thenReturn(null);
        when(mockAuthService.validateToken(anyString())).thenReturn("TestUsername");

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        when(mockSecurityContext.isSecure()).thenReturn(true);
        when(mockContainerRequestContext.getSecurityContext()).thenReturn(mockSecurityContext);
        doNothing().when(mockContainerRequestContext).setSecurityContext(any());
        doNothing().when(mockContainerRequestContext).abortWith(any());

        assertDoesNotThrow( () -> filter.filter(mockContainerRequestContext));
    }

    @Test
    void testFilterDoesNotThrowOnAbortedCauseWrongString() {
        when(mockContainerRequestContext.getHeaderString(anyString())).thenReturn("Test TestToken");
        when(mockAuthService.validateToken(anyString())).thenReturn("TestUsername");

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        when(mockSecurityContext.isSecure()).thenReturn(true);
        when(mockContainerRequestContext.getSecurityContext()).thenReturn(mockSecurityContext);
        doNothing().when(mockContainerRequestContext).setSecurityContext(any());
        doNothing().when(mockContainerRequestContext).abortWith(any());

        assertDoesNotThrow( () -> filter.filter(mockContainerRequestContext));
    }

    @Test
    void testFilterDoesNotThrowOnInvalidTokenException() {
        when(mockContainerRequestContext.getHeaderString(anyString())).thenReturn("Test TestToken");
        when(mockAuthService.validateToken(anyString())).thenThrow(new InvalidTokenException("Exception"));

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        when(mockSecurityContext.isSecure()).thenReturn(true);
        when(mockContainerRequestContext.getSecurityContext()).thenReturn(mockSecurityContext);
        doNothing().when(mockContainerRequestContext).setSecurityContext(any());
        doNothing().when(mockContainerRequestContext).abortWith(any());

        assertDoesNotThrow( () -> filter.filter(mockContainerRequestContext));
    }
}