package com.simurg;

import com.simurg.Parser.Parser;
import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.html.*;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
           HtmlElement input= Parser.getInputField(page);
       //  HtmlElement reg= Parcer.getRegField(page);
       //  reg.type("Барановичи");
            HtmlElement cityItem = page.getFirstByXPath("//li[contains(@class, 'select-check-item') and normalize-space(text())='Барановичи']");
            cityItem.click();
         webClient.waitForBackgroundJavaScript(2000);
         HtmlElement btn=   Parser.getSubmitBtn(page);
        // input.type("Пессарий кубический перфорированный 37");
            input.type("Пессарий силиконовый кольцо 70 пессарии N1");
        HtmlPage newPage= btn.click();

            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
       HtmlTable table= Parser.getTable(newPage);
       HtmlTableHeader header= Parser.getTHeader(table);
      // HtmlTableFooter footer= Parser.getTFooter(table);
       List<HtmlTableRow> hRows= Parser.getTHeadRows(header);
         //   List<HtmlTableRow> fRows= Parser.getTFooterRows(footer);
//            int count=0;
//           for(HtmlTableRow row:hRows){
//               System.out.println("================");
//               System.out.print(row.getTextContent());
//               System.out.println("()()()()()()()()()()()()()()()()()()()()()()()()()()()()()()()()");
//               count++;
//           }
            System.out.println("--------------------------------------------------"+newPage.getUrl());
List<HtmlTableBody> bodies = table.getBodies();
            System.out.println("BODY "+ bodies.size());
            List<HtmlTableRow> groups=bodies.get(0).getRows();
            System.out.println("GROUPS "+ bodies.size());
         // for (HtmlTableRow row:groups){
        List<HtmlTableCell> cells= groups.get(0).getCells();
            System.out.println(groups.get(0).getCell(1).getTextContent()+"//////////ХУЕЧЕК////////////"+groups.get(0).getCell(2).getTextContent());
//              for (HtmlTableCell cell:cells){
//                  System.out.println(cell.getTextContent());
//                  System.out.println("+=++==+==+++++===++==++==+==+==++=++GHGHGHGHHGHGHGHGHGHGHGHG");
//              }
HtmlAnchor anchor = cells.get(1).getFirstByXPath(".//a");
            System.out.println("ANCHOR: "+ anchor);
          //   table.get("/html/body/main/div[2]/div[2]/table[1]/tbody/tr/td[5]/span[2]/a");
         //  DomNode d= cells.get(cells.size()-1).getFirstByXPath("/html/body/main/div[2]/div[2]/table[1]/tbody/tr/td[5]/span[2]/a");
         HtmlTable newTable=  Parser.getTableFromAnchor(anchor);
       List< HtmlTableBody> bod= Parser.getTBodies(newTable);
      List<HtmlTableRow> mainRows=  Parser.getRowsFromFirstBody(bod);//as/dasd
            Set<List<HtmlTableCell>> dataCells= Parser.getTCells(mainRows);
            for (List<HtmlTableCell> cells1 :dataCells){
                System.out.println(
                        cells1.get(1).getTextContent().replaceAll("\\s+"," ").trim()+"//////////////////"
                        +cells1.get(2).getTextContent().replaceAll("\\s+"," ").trim()+"//////////////////"
                        +cells1.get(5).getTextContent().replaceAll("\\s+"," ").trim()+"//////////////////");
                System.out.println("8888888888888888888888888888888");
            }
            HtmlPage pag3=   cells.get(2).click();;
            System.out.println("PAGE3 "+ pag3.getUrl());
            //  String s1= s.replaceAll("\\s+", "XYI").trim();
              //System.out.print(s1);
        //   }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
