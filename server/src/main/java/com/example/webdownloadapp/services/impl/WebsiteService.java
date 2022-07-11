package com.example.webdownloadapp.services.impl;

import com.example.webdownloadapp.dto.LinkDto;
import com.example.webdownloadapp.dto.WebsiteDto;
import com.example.webdownloadapp.exceptions.ResourceNotFoundException;
import com.example.webdownloadapp.models.Link;
import com.example.webdownloadapp.models.Website;
import com.example.webdownloadapp.repositories.IWebsiteRepository;
import com.example.webdownloadapp.services.ILinkService;
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

    private String savingPath = "src/main/resources/static/websites/";
    private final IWebsiteRepository websiteRepository;
    private final ILinkService linkService;
    private String savingPath = "src/main/resources/static/websites/";

    public WebsiteService(IWebsiteRepository websiteRepository, ILinkService linkService) {
        this.websiteRepository = websiteRepository;
        this.linkService = linkService;
    }

    @Override
    public List<Website> getAllWebsites() {
        return websiteRepository.findAll();
    }

    @Override
    public List<Link> findLinksByWebsite(Long websiteId) {
        return linkService.findByWebsite(websiteId);
    }

    @Override
    public Website getWebsiteByName(String name) {
        return websiteRepository.findByWebsiteName(name);
    }

    public static Set<String> findLinks(String url) {

        Set<String> links = new HashSet<>();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Elements elements = doc.select("a[href]");
        for (Element element : elements) {
            links.add(element.attr("href"));
        }
        return links;
    }


    public void createFolder(String path){
        File pathAsFile = new File(path);

        if (!Files.exists(Paths.get(path))) {
            pathAsFile.mkdirs();
        }
    }

    @Override
    public Website saveWebsite(WebsiteDto websiteDto) throws Exception {
        Website website = new Website();
        URL url = new URL(websiteDto.getUrl());
        website.setWebsiteName(url.getHost());

        long start = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        website.setDownloadStartDateTime(dateFormat.format(new Date(start)));

        String fileName = url.getFile();
        // use index.html on urls without filename
        if (fileName.isEmpty() || fileName.length() < 3) {
            fileName = "index.html";
        }

        String filePath = savingPath + "/" + website.getWebsiteName() + "/";
        String linksPath = filePath + "links";

        createFolder(linksPath);

//        boolean fileDirectory = new File( savingPath+ url.getHost()).mkdir();

//        File file = new File("src/main/resources/static/websites/" + url.getHost() + "/" + url.getHost() + ".html");


        BufferedReader readr =
                new BufferedReader(new InputStreamReader(url.openStream()));

        BufferedWriter writer =
                new BufferedWriter(new FileWriter(filePath + fileName));

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

        website.setTotalDownloadedKilobytes(Files.size(Paths.get(filePath + fileName)) / 1024);

        Website saved = websiteRepository.save(website);

        Set<String> links = findLinks(url.toExternalForm());
        for (String link : links) {
            LinkDto linkDTO = new LinkDto();
            linkDTO.setWebsite(saved);
            if (link.isEmpty()) continue;
            Boolean isFromSameSite = link.charAt(0) == '/';
            if (isFromSameSite) {
                URL _url = new URL(url.toExternalForm() + link.substring(1));
                createFolder(filePath + link.substring(1));
                linkDTO.setUrl(_url);
                linkDTO.setPath(filePath + link.substring(1));
            } else {
                URL _url = new URL(link);
                linkDTO.setUrl(_url);
                createFolder(linksPath + "/" + _url.getHost());
                linkDTO.setPath(linksPath);
            }
            linkService.saveLink(linkDTO);
        }
        System.out.println("Website successfully downloaded");
        return saved;
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
    // public static Set<String> findLinks(String url) throws IOException {
    //     Set<String> links = new HashSet<>();
    //     Document doc = Jsoup.connect(url).get();
    //     Elements elements = doc.select("a[href]");
    //     for (Element element : elements) {
    //         links.add(element.attr("href"));
    //     }
    //     return links;
    // }

}
