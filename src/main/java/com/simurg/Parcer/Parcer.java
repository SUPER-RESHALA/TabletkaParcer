package com.simurg.Parcer;

import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlElement;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parcer {
    public static String url="https://tabletka.by/";
    public static String inputXpath="//input[contains(@class, 'ls-select-input')]";
    public static String regXpath="//ul[contains(@class, 'select-check-list') and contains(@class, 'region-select-list')]";
    public  static String submitXpath="//button[contains(@class, 'header-search-bttn')]";
    public void connect(){
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            final HtmlPage page = webClient.getPage(url);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static HtmlElement getRegField(final HtmlPage page){
        return page.getFirstByXPath("//input[contains(@class, 'region-select-input')]");
    }
public static List<String> getRegions(final HtmlPage page) throws IOException {
    HtmlElement table1= page.getFirstByXPath(regXpath);
    List<String> regList= new ArrayList<>();
    for (DomNode d:table1.getChildren()){
        regList.add(d.getTextContent());
    }
    return regList;
}
public static HtmlElement  getInputField(final  HtmlPage page){
    return page.getFirstByXPath(inputXpath);
}

public static HtmlElement getSubmitBtn(final  HtmlPage page){
       return page.getFirstByXPath(submitXpath);
}
    public String chooseRegion(){

return "Region";
    }
}
