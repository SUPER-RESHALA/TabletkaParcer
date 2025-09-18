package com.simurg;

import com.simurg.Parcer.Parcer;
import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX)) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            final HtmlPage page = webClient.getPage("https://tabletka.by");
         //  HtmlForm form= page.getForms().get(0);
           HtmlElement input= Parcer.getInputField(page);
       //  HtmlElement reg= Parcer.getRegField(page);
       //  reg.type("Барановичи");
            String city=" BARANI";
            String d= String.format("HHHH %s",city);
            System.out.println("DEBIL "+d);
            HtmlElement cityItem = page.getFirstByXPath("//li[contains(@class, 'select-check-item') and normalize-space(text())='Барановичи']");
            cityItem.click();


         webClient.waitForBackgroundJavaScript(2000);
         HtmlElement btn=   Parcer.getSubmitBtn(page);
         input.type("Параскофен");
        HtmlPage newPage= btn.click();
            System.out.println("------------------ "+newPage.getUrl());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
