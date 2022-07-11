package com.example.webdownloadapp.services.impl;

import com.example.webdownloadapp.dto.LinkDto;
import com.example.webdownloadapp.exceptions.RequiredFieldException;
import com.example.webdownloadapp.exceptions.ResourceNotFoundException;
import com.example.webdownloadapp.models.Link;
import com.example.webdownloadapp.models.Website;
import com.example.webdownloadapp.repositories.ILinkRepository;
import com.example.webdownloadapp.repositories.IWebsiteRepository;
import com.example.webdownloadapp.services.ILinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class LinkService implements ILinkService {
    private final ILinkRepository linkRepository;

    @Autowired
    private IWebsiteRepository websiteRepository;

    public LinkService(ILinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Link saveLink(LinkDto linkDto) throws Exception {
        Link link = new Link();

        String filePath= linkDto.getPath() + "/index.html";
        link.setName(linkDto.getUrl().getFile());
        link.setWebsite(linkDto.getWebsite());


        Long start = System.currentTimeMillis();


        if (!linkDto.getUrl().getFile().toString().contains("?")) {
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(linkDto.getUrl().openStream()));

            // Enter filename in which you want to download
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(filePath));

            // read each line from stream till end
            String line;
            while ((line = readr.readLine()) != null) {
                writer.write(line);
            }

            readr.close();
            writer.close();
            long end = System.currentTimeMillis();
            long timeElapsed = end - start;
            link.setTotalElapsedTime(timeElapsed);

            link.setTotalDownloadedKilobytes(Files.size(Paths.get(filePath)) / 1024);
            return linkRepository.save(link);
        }
        return null;
    }

    @Override
    public List<Link> findByWebsite(Long websiteId) {
        Website website = websiteRepository.findById(websiteId).get();

        return linkRepository.findByWebsite(website);
    }
}
