package io.cucumber.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elements extends Page {

    public Elements(ChromeDriver driver) {
        super(driver);
    }

    public WebElement link(String linkText) {
        return driver.findElement(By.xpath("//a[normalize-space()='" + linkText + "']"));
    }

    public WebElement message(String message) {
        return driver.findElement(By.xpath("//p[normalize-space()='" + message + "']"));
    }

    public void verifyTableData(String tableName, List<Map<String, String>> expectedData) {

        WebElement table = driver.findElement(
                By.xpath("//h4[text()='" + tableName + "']/following-sibling::table")
        );

        List<WebElement> headers = table.findElements(By.xpath(".//thead/tr/th"));
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));

        for (Map<String, String> expectedRow : expectedData) {
            boolean rowFound = false;

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.xpath("./td"));
                Map<String, String> actualRow = new HashMap<>();
                //Get the actual row using only the columns defined in the expected value
                for (int i = 0; i < headers.size(); i++) {
                    String header = headers.get(i).getText();

                    if (expectedRow.containsKey(header)) {
                        actualRow.put(header, cells.get(i).getText());
                    }
                }
                //To mark the row as found when expected and actual value matches
                if (actualRow.equals(expectedRow)) {
                    rowFound = true;
                    break;
                }
            }
            //To fail the test when expected row is not found
            assert rowFound : "Expected row was not found: " + expectedRow;
        }
    }
}