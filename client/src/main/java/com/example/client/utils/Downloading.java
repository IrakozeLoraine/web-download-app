package com.example.client.utils;

import java.net.URISyntaxException;

public class Downloading {

    public static String getDomainName(String domain) throws URISyntaxException {
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
