package com.simurg.Parser;

import com.simurg.Models.Item;
import com.simurg.Utils.MyDate;
import org.htmlunit.html.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHandle {
    public static final String anchorXpath=".//a";
    public static Pattern pricePattern=Pattern.compile("(\\d+[.,]?\\d*)\\s*р");
    public  static Pattern packPattern = Pattern.compile("(\\d+)\\s+упаков");
   public static Pattern cityPattern = Pattern.compile("^(.*?)(,|-)");
    public static List<HtmlAnchor> getAnchorsFromCells(Set <List<HtmlTableCell>> tCells){
    List<HtmlAnchor> newList= new ArrayList<>();
    for (List<HtmlTableCell> list:tCells){
      newList.add(list.get(1).getFirstByXPath(anchorXpath));
    }
    return newList;
}
    public static List<HtmlAnchor> getAnchorsFromRows(List<HtmlTableRow> rows){
        List<HtmlAnchor> newList= new ArrayList<>();
        for (HtmlTableRow row:rows){
            newList.add(row.getCell(1).getFirstByXPath(anchorXpath));
        }
        return newList;
    }
public  static Item getItemFromRow(HtmlTableRow row){
       String name= getItemNameFromStr(row.getCell(1).getTextContent());
    String type=getTypeFromStr(row.getCell(2).getTextContent());
    return new Item(name,type);
}
    public static List<Item> getItemsFromRows(List<HtmlTableRow> tRows){
        List<Item> newList= new ArrayList<>();
        for (HtmlTableRow row:tRows){
          newList.add(getItemFromRow(row));
        }
        return newList;
    }
    ///I know that this name is p.o.s// item from getItemsFromRows
public static Item completeItem(HtmlTableRow row, Item item){
    String pharmName= getNameFromStr( row.getCell(1).getTextContent());
    String addressCell= row.getCell(2).getTextContent();
    String priceCell= row.getCell(5).getTextContent();
    String address=getAddressFromStr(addressCell);
    String cityName=getCityFromStr(addressCell);
   Double price =getPriceFromStr(priceCell);
 Integer amount=getAmountFromStr(priceCell);
 Boolean isIndicated=price!=null;
 //TODO TYPRE ITEMNAME
 return new Item(pharmName,cityName,address,price,"ItemName", amount, MyDate.getCurrentDate(),"TYPE",isIndicated);
}
//public static void completeItemFromTable(HtmlTable table,Item item){
// List<HtmlTableBody> bodies= Parser.getTBodies(table);
//  List<HtmlTableRow> rows=Parser.getRowsFromFirstBody(bodies);
//
//
//}
public static String getTypeFromStr(String type){
        return type.replaceAll("\\s*(Без)","");
    }
public static String getItemNameFromStr(String name){
        return name.replaceAll("\\s*(Разное)","");
    }
public static String getNameFromStr(String pharmacyName){
return  pharmacyName.replaceAll("\\s*Обновлено*", "");
}
public static String getAddressFromStr(String pharmAddress){
        return  pharmAddress.replaceAll("\\s*(Открыто|Закрыто|Круглосуточно)", "");
    }
public static Double getPriceFromStr(String price){
    Matcher matcher= pricePattern.matcher(price);
    if (matcher.find()){
        return    Double.parseDouble(matcher.group(1).replace(",","."));
    }
    return null;
}
public static String getCityFromStr(String cityStr){
        Matcher matcher= cityPattern.matcher(cityStr);
        if (matcher.find()){
            return    matcher.group(1);
        }
        return "Unknown";
    }
public static Integer getAmountFromStr(String pack){
Matcher matcher= packPattern.matcher(pack);
if (matcher.find()){
    return Integer.parseInt(matcher.group(1));}
return null;
}
//public static List<Item> getItemsFromTables(List<HtmlTable> tables){
//
//}
}
