package app.bike_app;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootTest
@EnableTransactionManagement
@ActiveProfiles("test")
@TestPropertySource("classpath:application.properties")
public abstract class AbstractIntegrationTest {

}
