package com.simurg.Parser;

import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.html.*;

import java.io.IOException;
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
public static List<HtmlTableBody> getTBodies(final HtmlTable table){
        return table.getBodies();
}
public static List<HtmlTableRow> getRowsFromBodies(List<HtmlTableBody> bodies){
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