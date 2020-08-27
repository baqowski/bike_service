package app.core.service;

import app.bike_app.AbstractIntegrationTest;
import app.jwt.dto.PayUResponseAuthDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Karol Bąk
 */
class PayUServiceTest extends AbstractIntegrationTest {

    @Autowired
    private PayUService payUService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void shouldAuthorizeWithPayU() {
        PayUResponseAuthDTO payUResponseAuthDTO = payUService.authorize();

        Assertions.assertNotNull(payUResponseAuthDTO);
        Assertions.assertNotNull(payUResponseAuthDTO.getAccess_token());
        Assertions.assertNotNull(payUResponseAuthDTO.getExpires_in());
        Assertions.assertNotNull(payUResponseAuthDTO.getGrant_type());
        Assertions.assertNotNull(payUResponseAuthDTO.getToken_type());
    }
}
