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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class WebsiteService implements IWebsiteService {
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
    public Website saveWebsite(WebsiteDto websiteDto) throws Exception {
        if (websiteDto.getWebsiteName().equals("")){
            throw new RequiredFieldException("name", "website");
        }
        if (websiteDto.getTotalDownloadedKilobytes().equals("")){
            throw new RequiredFieldException("total downloaded kilobytes", "website");
        }
        if (websiteDto.getTotalElapsedTime().equals("")){
            throw new RequiredFieldException("total elapsed time", "website");
        }
        if (websiteDto.getDownloadStartDateTime().equals("")){
            throw new RequiredFieldException("start time", "website");
        }
        if (websiteDto.getDownloadEndDateTime().equals("")){
            throw new RequiredFieldException("end time", "website");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String startDate = dateFormat.format(websiteDto.getDownloadStartDateTime());
        String endDate = dateFormat.format(websiteDto.getDownloadEndDateTime());

        return websiteRepository.save(new Website(websiteDto.getWebsiteName(), startDate, endDate, websiteDto.getTotalElapsedTime(), websiteDto.getTotalDownloadedKilobytes()));
    }

    @Override
    public Optional<Website> getWesiteById(Long id) {
        return websiteRepository.findById(id);
    }
}
