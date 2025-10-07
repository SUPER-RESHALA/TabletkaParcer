package com.simurg.Network;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class Request {
    public static  Connection.Response loadPage(String url, int page,String ls, String region, String csrfToken, Map<String,String> cookies) throws IOException {
      return     Jsoup.connect(url)
                .ignoreContentType(true)
                .cookies(cookies)
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .data("ls", ls) // ls из URL
                .data("region", region)
                .data("page", Integer.toString(page))   // номер страницы
                .data("is_open", "0")
                .data("is_order", "0")
                .data("is_reserv", "0")
                .data("is_delivery", "0")
                .data("install", "")
                .data("insure", "")
                .data("str", "")
                .data("sort", "price")
                .data("sort_type", "asc")
                .data("_csrf", csrfToken)
                .method(Connection.Method.POST)
                .execute();
    }
}
