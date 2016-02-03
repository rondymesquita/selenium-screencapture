package br.com.example.page;

import org.openqa.selenium.WebDriver;

/**
 * Created by rondymesquita on 26/09/15.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected String url;

    public BasePage(WebDriver driver, String url){
        this.driver = driver;
        this.url = url;
    }

    public void open(){
        driver.get(url);
    }


}