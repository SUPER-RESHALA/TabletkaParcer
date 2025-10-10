package com.simurg.Parser;

import com.simurg.Config.AppConfig;
import com.simurg.Models.Item;
import com.simurg.Models.Pharmacy;
import com.simurg.Models.Region;
import com.simurg.Models.SessionData;
import com.simurg.Network.Request;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.simurg.Config.AppConfig.LOAD_PAGE_LINK;
import static com.simurg.Config.AppConfig.NAME_LINK;

public class Parser {
    public static String baseUrl = "https://tabletka.by";


    public static  String  getCsrf(Document document){
        return  document.select("meta[name=csrf-token]").attr("content");
    }
    public static Document getPharmTable(Connection.Response response){
        JSONObject obj = new JSONObject(response.body());
        String html = obj.getString("data");
        return Jsoup.parse(html);
    }
    public static List<Pharmacy> pharmaciesFromTable(Document table){
        List<Pharmacy> pharma= new ArrayList<>();
        table.select("tr.tr-border").forEach(row -> pharma.add(getPharmFromRow(row)));
        return pharma;
        }
        public static Pharmacy getPharmFromRow(Element row){
            String pharmName = row.selectFirst("td.pharm-name a") != null
                    ? row.selectFirst("td.pharm-name a").text()
                    : "";
            String address = row.selectFirst("td.address .tooltip-info-header span") != null
                    ? row.selectFirst("td.address .tooltip-info-header span").text()
                    : "";
            String city=DataHandle.parseCity(address);
            String price = row.selectFirst("td.price .price-value") != null
                    ? row.selectFirst("td.price .price-value").text()
                    : "";
            Double truePrice=DataHandle.parsePrice(price);
            String packs = row.selectFirst("td.price .capture") != null
                    ? row.selectFirst("td.price .capture").text()
                    : "";
            Integer amount=DataHandle.parseAmount(packs);
            boolean isIndicated=!packs.isEmpty();
//TODO Сделать подсчет общего количества
//            int sum = 0;
//            for (Element td : row.select("td.price .tooltip-info-table-tr")) {
//                String digits = td.select(".tooltip-info-table-td").get(1).text().replaceAll("[^0-9]", "");
//                if (!digits.isEmpty()) {
//                    sum += Integer.parseInt(digits);
//                }
//            }
//            if (sum == 0) {
// Пропуск
//            }
              return new Pharmacy(pharmName,city,address,truePrice,amount,isIndicated);
        }
public static Connection connect(String url){
        return Jsoup.connect(url);
}
public static Connection.Response getResponse(String url) throws IOException {
        return connect(url)
                .method(Connection.Method.GET)
                .execute();
}
public static SessionData getSessionData(String url) throws IOException {
    Connection.Response initResp =  getResponse(baseUrl+url);
    Document doc = initResp.parse();
    String csrf = getCsrf(doc);
    Map<String, String> cookies = initResp.cookies();
    cookies.put("lim-result", "100");
    String ls=DataHandle.parseLs(url);
    return new SessionData(cookies,ls,csrf);
}
public static List<Pharmacy> pharmaciesFromUrl(String url, int page, String region) throws IOException {
   SessionData data=getSessionData(url);
   String ls=data.getLs();
   String csrf= data.getCsrfToken();
   Map<String,String> cookies= data.getCookies();
   Connection.Response response= Request.loadPage(LOAD_PAGE_LINK,page,ls,region,csrf,cookies);
   Document tableDoc = getPharmTable(response);
   return pharmaciesFromTable(tableDoc);
}
    public static List<Item> collectItems(String itemName, String regionNumber) throws IOException {
            String searchUrl=DataHandle.createSearchUrl(itemName,regionNumber);
           List<Item> items= getItemsFromUrl(searchUrl);
          processItems(items,regionNumber);
          return items;
    }
    private static void processItems(List<Item> items, String regionNumber) throws IOException {
        for (Item item:items){
            addPharmaToItem(item,regionNumber);
        }
    }

    public static void addPharmaToItem(Item item, String regionNumber ) throws IOException {
        int page=1;
        List<Pharmacy> pharmacies= new ArrayList<>();
       while (true){
           List<Pharmacy> newPharm=pharmaciesFromUrl(item.getLink(),page,regionNumber);
           if (newPharm.isEmpty()) break;
           //System.out.println("Pharm size " +newPharm.size());
           pharmacies.addAll(newPharm);
           page++;
       }
       item.setPharma(pharmacies);
    }
    public static Item getItemFromRow(Element row){
        Element nameTd = row.selectFirst(NAME_LINK);
        //Item name
        String name = nameTd != null ? nameTd.text() : "";
        String link = nameTd != null ? nameTd.attr("href") : "";
        // Форма выпуска
        Element formTd = row.selectFirst(AppConfig.FORM_LINK);
        String form = formTd != null ? formTd.text() : "";
        // Производитель
        Element producerTd = row.selectFirst(AppConfig.PRODUCER_LINK);
        String producer = producerTd != null ? producerTd.text().trim() : "";
        return new Item(name,form,producer,link);
    }
public static  List<Item> getItemsFromUrl(String searchUrl) throws IOException {
        List<Item> items= new ArrayList<>();
        Document doc= connect(baseUrl+searchUrl).get();
        Element table= getItemTable(doc);
        Elements rows= getItemRows(table);
        for (Element row:rows){
         items.add(getItemFromRow(row));
        }
        return items;
}

    public static Elements getItemRows(Element table){
        return    table.select(AppConfig.ITEM_ROWS);
    }
    public static Element getItemTable(Document doc){
     return   doc.getElementById(AppConfig.ITEM_TABLE_ID);
    }
    public static List<Region> getRegions(Document doc){
Elements elements =doc.select(".region-select-list .select-check-item");
List<Region> regions= new ArrayList<>();
for (Element element: elements){
  String value=  element.attr("value");
String name= element.text().trim();
  regions.add(new Region(name,value));
}
return regions;
    }


    public String chooseRegion() {

        return "Region";
    }
}
//                // Суммируем упаковки из детализации внутри tooltip-info-table
//                int totalPacks = 0;
//                Elements batchRows = row.select("td.price .tooltip-info-table-tr");
//                for (Element batch : batchRows) {
//                    Elements cols = batch.select(".tooltip-info-table-td");
//                    if (cols.size() >= 2) {
//                        String countText = cols.get(1).text().trim(); // "5 упаковок"
//                        // Убираем всё, что не цифра (включая пробелы, буквы, запятые)
//                        String digits = countText.replaceAll("\\D+", ""); // лучше \\D+ чем [^0-9]
//                        if (!digits.isEmpty()) {
//                            try {
//                                totalPacks += Integer.parseInt(digits);
//                            } catch (NumberFormatException e) {
//                                // на всякий случай — логируем и продолжаем
//                                System.out.println("Не могу распарсить число из: '" + countText + "'");
//                            }
//                        }
//                    }
//                }
//необязательные  .header("X-Requested-With", "XMLHttpRequest")(конкретно это обязательный)
//.header("Accept", "application/json, text/javascript, */*; q=0.01")
// .header("Referer", searchUrl)
// .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36")
