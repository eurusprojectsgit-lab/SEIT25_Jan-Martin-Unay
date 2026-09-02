package io.cucumber.pages;

import org.openqa.selenium.chrome.ChromeDriver;

public class BasicAuth extends Page {
    public BasicAuth(ChromeDriver driver) {
        super(driver);
    }

    public void authenticate(String username, String password) {
        String url = "https://" + username + ":" + password
                + "@the-internet.herokuapp.com/basic_auth";

        driver.get(url);
        waitForPageLoad();
    }
}
