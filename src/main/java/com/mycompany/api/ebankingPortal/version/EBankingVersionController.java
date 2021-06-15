package com.mycompany.api.ebankingPortal.version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EBankingVersionController {
    @GetMapping("/ebanking/version")
    public String getVersion() {
        return "1.0";
    }

    Logger logger = LoggerFactory.getLogger(EBankingVersionController.class);

    @GetMapping("/")
    public String index() {
        return "EBanking Portal is up!!!";
    }
}