package br.com.example.page;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sun.org.apache.bcel.internal.generic.F2D;

import br.com.example.util.Logger;

/**
 * Created by rondymesquita on 26/09/15.
 */
public class DuckDuckGoPage extends BasePage{

//    private static final String URL = "https://duckduckgo.com";
	private static final String URL = "file://" + new File("./test-assets/public/index.html").getAbsolutePath();
    public final By searchFieldSelectorBy = By.id("search_field");
    public final By searchButtonSelectorBy = By.id("search_button");

    public DuckDuckGoPage(WebDriver driver) {
        super(driver, URL);
    }

    public void setSearchField(String search){
        Logger.logInfo(searchFieldSelectorBy.toString());
        WebElement searchField = driver.findElement(searchFieldSelectorBy);
        searchField.clear();
        searchField.sendKeys(search);
    }
    
    public void setAnyTextOnSearchField(){
    	setSearchField("Darth Vader");
    }

    public void clickOnSearchButton(){
        driver.findElement(searchButtonSelectorBy).click();
    }
    
    public void focusOnSearchField(){
        driver.findElement(searchFieldSelectorBy).click();
    }

    public void doSearch(String search){
        setSearchField(search);
        clickOnSearchButton();
    }









}