package com.mycompany.api.ebankingPortal.application.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EBankingVersionController {
    @GetMapping("/ebanking/version")
    public String getVersion() {
        return "1.0";
    }

}