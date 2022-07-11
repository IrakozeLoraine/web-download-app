package com.example.client.utils;

import com.example.client.models.WebsiteEntry;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;

public class Downloading {
    public static WebsiteEntry DownloadWebPage(String webpage)
    {
        try {
            long start = System.currentTimeMillis();

            // Create URL object
            URL url = new URL(webpage);
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            // Enter filename in which you want to download
            String fileName = "index.html";
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(fileName));

            // read each line from stream till end
            String line;
            while ((line = readr.readLine()) != null) {
                writer.write(line);
            }

            long end = System.currentTimeMillis();
            long timeElapsed = end - start;

            File file = new File(fileName);
            long fileSizeInBytes = file.length();
            long fileSizeInKB = fileSizeInBytes / 1024;

            readr.close();
            writer.close();
            System.out.println("Page downloaded.");

            String domainName = Downloading.getDomainName(url.getHost());
            WebsiteEntry websiteEntry = new WebsiteEntry(domainName, new Date(start), new Date(end), fileSizeInKB, timeElapsed);

            return websiteEntry;
        }

        // Exceptions
        catch (MalformedURLException mue) {
            System.out.println("Malformed URL Exception raised");
            mue.printStackTrace();
        }
        catch (IOException ie) {
            System.out.println("IOException raised");
            ie.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDomainName(String domain) throws URISyntaxException {
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
