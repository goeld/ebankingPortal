package com.mycompany.api.ebankingPortal.application.health;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EBankingVersionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_application_version_is_10() throws Exception {
        mockMvc.perform(get("/ebanking/version"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("1.0"));
    }

}
