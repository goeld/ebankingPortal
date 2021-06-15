package com.mycompany.api.ebankingPortal.health.stepdefs;

import com.mycompany.api.ebankingPortal.configuration.CucumberSpringContextConfiguration;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.platform.engine.Cucumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Cucumber
public class HealthFeatureTest extends CucumberSpringContextConfiguration {

    @Autowired
    private MockMvc mockMvc;

    @When("Application starts")
    public void when_method() {
        System.out.println("Cucumber - when ");
    }

    @Then("health status is up")
    public void then_method() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{\"status\":\"UP\"}"));
    }
}