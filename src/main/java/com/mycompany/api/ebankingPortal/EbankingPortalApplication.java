package com.mycompany.api.ebankingPortal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "eBanking Portal API", version = "0.0.1", description = "eBanking API for customer account transactions"))
public class EbankingPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingPortalApplication.class, args);


    }

}
