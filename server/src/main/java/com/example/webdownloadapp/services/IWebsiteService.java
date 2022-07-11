package com.example.webdownloadapp.services;

import com.example.webdownloadapp.dto.WebsiteDto;
import com.example.webdownloadapp.models.Website;

import java.util.List;
import java.util.Optional;

public interface IWebsiteService {
    Website getWebsiteByName(String name);
    Website saveWebsite(WebsiteDto websiteDto) throws Exception;
    Optional<Website> getWesiteById(Long id);
    public List<Website> getAllWebsites();
}
