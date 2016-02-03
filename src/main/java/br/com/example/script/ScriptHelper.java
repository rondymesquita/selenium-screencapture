package br.com.example.script;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ScriptHelper {
	
	private WebDriver driver;
	
	public ScriptHelper(WebDriver driver) {
		this.driver = driver;
	}

	public void loadScript(String script){
		((JavascriptExecutor) driver).executeScript("var s=window.document.createElement('script'); s.src='" + script + "'; window.document.head.appendChild(s);");
	}
	
	public void executeScript(String script){
		((JavascriptExecutor) driver).executeScript(script);
	}

}
