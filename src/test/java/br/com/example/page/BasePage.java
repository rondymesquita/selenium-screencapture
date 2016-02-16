package br.com.example.page;

import org.openqa.selenium.WebDriver;

import br.com.example.util.Logger;

public abstract class BasePage {

    protected WebDriver driver;
    protected String url;

    public BasePage(WebDriver driver, String url){
    	Logger.logInfo(url);
        this.driver = driver;
        this.url = url;
    }

    public void open(){
        driver.get(url);
    }


}