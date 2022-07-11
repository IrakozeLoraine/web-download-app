package com.example.webdownloadapp.services.impl;

import com.example.webdownloadapp.dto.WebsiteDto;
import com.example.webdownloadapp.exceptions.RequiredFieldException;
import com.example.webdownloadapp.exceptions.ResourceNotFoundException;
import com.example.webdownloadapp.models.Link;
import com.example.webdownloadapp.models.Website;
import com.example.webdownloadapp.repositories.IWebsiteRepository;
import com.example.webdownloadapp.services.IWebsiteService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Primary
public class WebsiteService implements IWebsiteService {

    private String savingPath = "";
    private final IWebsiteRepository websiteRepository;

    public WebsiteService(IWebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    @Override
    public List<Website> getAllWebsites() {
        return websiteRepository.findAll();
    }

    @Override
    public Website getWebsiteByName(String name) {
        return websiteRepository.findByWebsiteName(name);
    }

    @Override
    public Website saveWebsite(URL url) throws Exception {
        Website website = new Website();
        website.setWebsiteName(url.getHost());

        long start = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        website.setDownloadStartDateTime(dateFormat.format(new Date(start)));

        String fileName = url.getFile();
        // use index.html on urls without filename
        if(fileName.isEmpty() || fileName.length() < 3){
            fileName = "index.html";
        }

        String filePath = savingPath+"/"+website.getWebsiteName()+"/";
        String linksPath = filePath+"links";

        createFolder(linksPath);

        BufferedReader readr =
                new BufferedReader(new InputStreamReader(url.openStream()));

        BufferedWriter writer =
                new BufferedWriter(new FileWriter(filePath+fileName));

        String line;
        while ((line = readr.readLine()) != null) {
            writer.write(line);
        }

        readr.close();
        writer.close();

        long end = System.currentTimeMillis();
        website.setDownloadEndDateTime(dateFormat.format(new Date(end)));

        long timeElapsed = end - start;
        website.setTotalElapsedTime(timeElapsed);

        website.setTotalDownloadedKilobytes(Files.size(Paths.get(filePath+fileName))/1024);

        return websiteRepository.save(website);
    }

    @Override
    public Optional<Website> getWesiteById(Long id) {
        return websiteRepository.findById(id);
    }

    public void createFolder(String path){
        File pathAsFile = new File(path);
        if (!Files.exists(Paths.get(path))) {
            pathAsFile.mkdirs();
        }
    }
    public static Set<String> findLinks(String url) throws IOException {
        Set<String> links = new HashSet<>();
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("a[href]");
        for (Element element : elements) {
            links.add(element.attr("href"));
        }
        return links;
    }

}
