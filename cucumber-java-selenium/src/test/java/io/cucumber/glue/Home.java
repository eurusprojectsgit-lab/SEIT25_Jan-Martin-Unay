package io.cucumber.glue;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.core.Context;
import io.cucumber.core.Manager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.pages.BasicAuth;
import io.cucumber.pages.Elements;
import io.cucumber.pages.Page;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;
import java.util.Map;

public class Home extends Context {
    Elements element = new Elements(getDriver());
    Page page = new Page(getDriver());

    public Home(Manager manager) {
        super(manager);
    }

//    @Given("an example Home Page step")
//    public void exampleHomePageStep() {
//        System.out.println("printing shared stash :" + getTestStash().toString());
//    }

    @When("the {string} link is opened")
    public void the_link_is_opened(String linkName) {
        page.waitForPageLoad();

        if(element.link(linkName).isDisplayed()) {
            element.link(linkName).click();
            System.out.println(linkName + " is opened");
        } else {
            System.out.println(linkName + " is not visible");
        }
    }

    @And("I verify the following links")
    public void i_verify_the_following_links(DataTable dataTable) {
        page.waitForPageLoad();

        List<Map<String, String>> linkName = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> link : linkName) {
            String expectedLink = link.get("Expected Result");

            try {
                assert element.link(expectedLink).isDisplayed()
                        : "Link was not displayed: " + expectedLink;

            } catch (NoSuchElementException e) {
                throw new AssertionError("Link was not found: " + expectedLink);
            }
        }
    }

    @And("credentials are supplied")
    public void credentials_are_supplied(DataTable credentials) {
        Map<String, String> data = credentials.asMap(String.class, String.class);

        String username = data.get("username");
        String password = data.get("password");

        BasicAuth auth = new BasicAuth(getDriver());
        auth.authenticate(username, password);

        page.waitForPageLoad();
    }

    @Then("I verify {string} message is displayed")
    public void i_verify_message_is_displayed(String message) {
        page.waitForPageLoad();

        if(element.message(message).isDisplayed()) {
            System.out.println(message + " is displayed");
        } else {
            System.out.println(message + " is not displayed");
        }
    }

    @Then("the {string} table should contain the following data")
    public void the_table_should_contain_the_following_data(String tableName, DataTable dataTable) {
        page.waitForPageLoad();
        List<Map<String, String>> expectedData = dataTable.asMaps(String.class, String.class);

        element.verifyTableData(tableName, expectedData);
    }

}