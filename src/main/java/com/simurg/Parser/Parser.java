package com.simurg.Parser;

import com.simurg.Models.Item;
import com.simurg.Models.Pharmacy;
import org.htmlunit.BrowserVersion;
import org.htmlunit.SilentCssErrorHandler;
import org.htmlunit.WebClient;
import org.htmlunit.html.*;
import org.htmlunit.javascript.SilentJavaScriptErrorListener;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    public static String url = "https://tabletka.by/";
    public static String inputXpath = "//input[contains(@class, 'ls-select-input')]";
    public static String regXpath = "//ul[contains(@class, 'select-check-list') and contains(@class, 'region-select-list')]";
    public static String submitXpath = "//button[contains(@class, 'header-search-bttn')]";
    public static String resultTableXpath = "//table[@class='table-border']";

    public static void connect() {
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {

            Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            Logger.getLogger("org.htmlunit").setLevel(Level.OFF);
            Logger.getLogger("org.apache.http").setLevel(Level.OFF);
            webClient.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
            webClient.setCssErrorHandler(new SilentCssErrorHandler());
            webClient.setHTMLParserListener(null); // или свой пустой listener
            

            webClient.getOptions().setThrowExceptionOnScriptError(false);

            HtmlPage page = webClient.getPage(url);

            HtmlElement input = Parser.getInputField(page);

            HtmlElement cityItem = page.getFirstByXPath("//li[contains(@class, 'select-check-item') and normalize-space(text())='Барановичи']");

            cityItem.click();

            webClient.waitForBackgroundJavaScript(2000);

            HtmlElement btn = Parser.getSubmitBtn(page);

            input.type("Пессарий силиконовый кольцо 70 пессарии N1");

            HtmlPage resultSearchPage = btn.click();

            HtmlTable tableWResult = Parser.getTable(resultSearchPage);

            List<HtmlTableBody> bodies = tableWResult.getBodies();

            List<HtmlTableRow> resultRows = Parser.getRowsFromFirstBody(bodies);

            List<Item> items = DataHandle.getItemsFromRows(resultRows);
            List<HtmlAnchor> anchors = DataHandle.getAnchorsFromRows(resultRows);

            List<HtmlTable> tables = getTablesFromAnchors(anchors);
            List<List<Pharmacy>> pharma=DataHandle.getPharmFromTables(tables);
            DataHandle.addPharmaToItem(items,pharma);
            printItems(items);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
public static void printItems(List<Item> items){
        for (Item item:items){
         item.printItem();
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
    public static HtmlElement getRegField(final HtmlPage page) {
        return page.getFirstByXPath("//input[contains(@class, 'region-select-input')]");
    }

    public static List<String> getRegions(final HtmlPage page) throws IOException {
        HtmlElement table1 = page.getFirstByXPath(regXpath);
        List<String> regList = new ArrayList<>();
        for (DomNode d : table1.getChildren()) {
            regList.add(d.getTextContent());
        }
        return regList;
    }

    public static HtmlTableHeader getTHeader(HtmlTable table) {
        return table.getHeader();
    }

    public static List<HtmlTableRow> getTHeadRows(HtmlTableHeader header) {
        return header.getRows();
    }

    public static HtmlTableFooter getTFooter(final HtmlTable table) {
        return table.getFooter();
    }

    public static List<HtmlTableRow> getTFooterRows(final HtmlTableFooter footer) {
        return footer.getRows();
    }

    public static HtmlTable getTable(final HtmlPage page) {
        return page.getHtmlElementById("base-select");
    }

    public static HtmlTableBody getFirstTBody(final HtmlTable table) {
        return table.getBodies().get(0);
    }

    public static List<HtmlTableBody> getTBodies(final HtmlTable table) {
        return table.getBodies();
    }

    public static List<List<HtmlTableBody>> getAllTBodies(final List<HtmlTable> tables) {
        List<HtmlTableBody> bodyOfOneT;
        List<List<HtmlTableBody>> bodies = new ArrayList<>();
        for (HtmlTable table : tables) {
            bodyOfOneT = getTBodies(table);
            bodies.add(bodyOfOneT);
        }
        return bodies;
    }

    public static List<HtmlTableBody> getFirstTBodies(final List<HtmlTable> tables) {
        List<HtmlTableBody> bodies = new ArrayList<>();
        for (HtmlTable table : tables) {
            bodies.add(getFirstTBody(table));
        }
        return bodies;
    }

    public static List<HtmlTableRow> getRowsFromFirstBody(List<HtmlTableBody> bodies) {
        return bodies.get(0).getRows();
    }
public static List<HtmlTableRow>  getRowsFromFirstBody(HtmlTableBody body){
        return body.getRows();
}
    public static Set<List<HtmlTableCell>> getTCells(List<HtmlTableRow> rows) {
        Set<List<HtmlTableCell>> set = new HashSet<>();
        for (HtmlTableRow row : rows) {
            set.add(row.getCells());
        }
        return set;
    }

    public static HtmlTable getTableFromAnchor(HtmlAnchor anchor) throws IOException {
        HtmlPage page = anchor.click();
        return page.getFirstByXPath(resultTableXpath);
    }

    public static List<HtmlTable> getTablesFromAnchors(List<HtmlAnchor> anchors) throws IOException {
        List<HtmlTable> tables = new ArrayList<>();
        for (HtmlAnchor anchor : anchors) {
            tables.add(getTableFromAnchor(anchor));
        }
        return tables;
    }

    public static HtmlElement getInputField(final HtmlPage page) {
        return page.getFirstByXPath(inputXpath);
    }

    public static HtmlElement getSubmitBtn(final HtmlPage page) {
        return page.getFirstByXPath(submitXpath);
    }

    public String chooseRegion() {

        return "Region";
    }
}