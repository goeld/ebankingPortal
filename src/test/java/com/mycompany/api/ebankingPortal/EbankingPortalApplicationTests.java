package com.mycompany.api.ebankingPortal;

import com.mycompany.api.ebankingPortal.customerAccountTransaction.CustomerAccountTransactionService;
import com.mycompany.api.ebankingPortal.customerAccountTransaction.CustomerAccountTransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Configuration
public class EbankingPortalApplicationTests {

    @Test
    void contextLoads() {
    }


    @Bean
    @Primary
    private CustomerAccountTransactionService getCustomerAccountTransactionService(){
        return Mockito.mock(CustomerAccountTransactionServiceImpl.class);
    }

}
