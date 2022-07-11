package com.example.client.utils;

import com.example.client.models.WebsiteEntry;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

public class Downloading {
    public static String getDomainName(String domain) throws URISyntaxException {
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
