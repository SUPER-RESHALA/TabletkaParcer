package com.simurg.Models;

import java.util.Map;

public class SessionData {
    Map<String, String> cookies;
    String ls;
    String csrfToken;

    public SessionData(Map<String, String> cookies, String ls, String csrfToken) {
        this.cookies = cookies;
        this.ls = ls;
        this.csrfToken = csrfToken;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getLs() {
        return ls;
    }

    public String getCsrfToken() {
        return csrfToken;
    }
}
