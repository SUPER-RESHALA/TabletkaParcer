package com.simurg.Parser;

import com.simurg.Models.Item;
import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.html.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser {
    public static String url="https://tabletka.by/";
    public static String inputXpath="//input[contains(@class, 'ls-select-input')]";
    public static String regXpath="//ul[contains(@class, 'select-check-list') and contains(@class, 'region-select-list')]";
    public  static String submitXpath="//button[contains(@class, 'header-search-bttn')]";
    public static String resultTableXpath="//table[@class='table-border']";
    public static void connect(){
        try (final  WebClient webClient= new WebClient(BrowserVersion.CHROME)){
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage page= webClient.getPage(url);
            HtmlElement input= Parser.getInputField(page);
            HtmlElement cityItem = page.getFirstByXPath("//li[contains(@class, 'select-check-item') and normalize-space(text())='Барановичи']");
            cityItem.click();
            webClient.waitForBackgroundJavaScript(2000);
            HtmlElement btn=   Parser.getSubmitBtn(page);
            input.type("Пессарий силиконовый кольцо 70 пессарии N1");
            HtmlPage newPage= btn.click();
            HtmlTable table= Parser.getTable(newPage);
            List<HtmlTableBody> bodies = table.getBodies();
           List<HtmlTableRow> rows= Parser.getRowsFromFirstBody(bodies);
       List<Item> items =   DataHandle.getItemsFromRows(rows);
      List<HtmlAnchor> anchors=  DataHandle.getAnchorsFromRows(rows);
      List<HtmlTable> tables= getTablesFromAnchors(anchors);
     List<HtmlTableBody> bodies1= getFirstTBodies(tables);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//     WebClient webClient;
//    public void createWebClient(){
//        if (webClient==null){
//            webClient = new WebClient(BrowserVersion.CHROME);
//        }
//    }
//    public void configureClient(WebClient webClient){
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//    }
//    public void closeConnection(){
//        if (webClient!=null){
//            webClient.close();
//            webClient=null;
//        }
//    }
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
    public static HtmlTableHeader getTHeader(HtmlTable table){
        return table.getHeader();
    }
public static List<HtmlTableRow> getTHeadRows(HtmlTableHeader header){
return    header.getRows();
}
public static HtmlTableFooter getTFooter(final HtmlTable table){
        return table.getFooter();
}
public static List<HtmlTableRow> getTFooterRows(final HtmlTableFooter footer){
return footer.getRows();
}
public static HtmlTable getTable(final HtmlPage page){
return page.getHtmlElementById("base-select");
}
    public static HtmlTableBody getFirstTBody(final HtmlTable table){
        return table.getBodies().get(0);
    }
public static List<HtmlTableBody> getTBodies(final HtmlTable table){
        return table.getBodies();
}
    public static List<List<HtmlTableBody>> getAllTBodies(final List<HtmlTable> tables){
        List<HtmlTableBody> bodyOfOneT;
        List<List<HtmlTableBody>> bodies= new ArrayList<>();
        for (HtmlTable table:tables){
            bodyOfOneT= getTBodies(table);
            bodies.add(bodyOfOneT);
        }
        return bodies;
    }
    public static List<HtmlTableBody> getFirstTBodies(final List<HtmlTable> tables){
      List<HtmlTableBody> bodies= new ArrayList<>();
      for (HtmlTable table:tables){
          bodies.add(getFirstTBody(table));
      }
        return bodies;
    }

public static List<HtmlTableRow> getRowsFromFirstBody(List<HtmlTableBody> bodies){
        return bodies.get(0).getRows();
}
public static Set<List<HtmlTableCell>> getTCells(List<HtmlTableRow> rows){
Set<List<HtmlTableCell>> set= new HashSet<>();
        for (HtmlTableRow row: rows){
           set.add( row.getCells());
        }
        return set;
}
public static HtmlTable getTableFromAnchor(HtmlAnchor anchor) throws IOException {
       HtmlPage page= anchor.click();
      return page.getFirstByXPath(resultTableXpath);
}
public static List<HtmlTable> getTablesFromAnchors(List<HtmlAnchor> anchors) throws IOException {
        List<HtmlTable> tables= new ArrayList<>();
        for (HtmlAnchor anchor:anchors){
            tables.add(getTableFromAnchor(anchor));
        }
        return tables;
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