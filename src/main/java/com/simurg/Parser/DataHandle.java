package com.simurg.Parser;

import com.simurg.Models.Item;
import com.simurg.Models.Pharmacy;
import com.simurg.Utils.MyDate;
import org.htmlunit.html.*;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataHandle {
    public static final String anchorXpath=".//a";
    public static Pattern pricePattern=Pattern.compile("(\\d+[.,]?\\d*)\\s*р");
    public  static Pattern packPattern = Pattern.compile("(\\d+)\\s+упаков");
   public static Pattern cityPattern = Pattern.compile("^(.*?)(,|\\-|—)");
public static String getTypeFromStr(String type){
       return type.replaceAll("\\s*(Без)*","");
    }
public static String getItemNameFromStr(String name){
        return name.replaceAll("\\s*(Разное)","");
    }
public static String getNameFromStr(String pharmacyName){
    return pharmacyName.replaceAll("\\s*Обновлено.*", "");
}
public static String getAddressFromStr(String pharmAddress){
        return pharmAddress.replaceAll("\\s*(Открыто|Закрыто|Круглосуточно).*", "");
    }
public static Double parsePrice(String price){
    Matcher matcher= pricePattern.matcher(price);
    if (matcher.find()){
        return    Double.parseDouble(matcher.group(1).replace(",","."));
    }
    return null;
}
public static String parseCity(String cityStr){
        Matcher matcher= cityPattern.matcher(cityStr.trim());
        if (matcher.find()){
            return  matcher.group(1);
        }
        return "Unknown";
    }
public static Integer parseAmount(String pack){
Matcher matcher= packPattern.matcher(pack);
if (matcher.find()){
    return Integer.parseInt(matcher.group(1));}
return null;
}
public static String parseLs(String str){
return str.toLowerCase().replaceAll(".*[?&]ls=([0-9]+).*", "$1");
}
public static String getLsFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            String query = url.getQuery(); // пример: "ls=2500&region=42"
            if (query == null) return null;

            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length == 2 && pair[0].equals("ls")) {
                    return URLDecoder.decode(pair[1], "UTF-8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // если ls не найден
    }
    public static String createSearchUrl(String itemName, String regionNumber){
        return  String.format("/search/?request=%s&region=%s",itemName,regionNumber);
    }
}
