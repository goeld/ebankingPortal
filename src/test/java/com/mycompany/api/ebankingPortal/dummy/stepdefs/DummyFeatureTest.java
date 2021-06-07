package com.mycompany.api.ebankingPortal.dummy.stepdefs;

import com.mycompany.api.ebankingPortal.configuration.CucumberSpringContextConfiguration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.platform.engine.Cucumber;

@Cucumber
public class DummyFeatureTest extends CucumberSpringContextConfiguration {

    @Given("Nothing")
    public void given_method(){
        System.out.println("Cucumber - Given ");
    }

    @When("I click on the run button")
    public void when_method(){
        System.out.println("Cucumber - when ");
    }

    @Then("test runs")
    public void then_method(){
        System.out.println("Cucumber - Then");
    }

}