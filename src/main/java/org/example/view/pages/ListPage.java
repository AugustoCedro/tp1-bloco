package org.example.view.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ListPage extends BasePage {
    public ListPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a[href='/clients/new']")
    private WebElement createClientButton;


    public void goToCreateClientPage() {
        createClientButton.click();
    }

}
