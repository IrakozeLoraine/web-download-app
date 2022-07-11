package com.example.webdownloadapp.services;

import com.example.webdownloadapp.dto.WebsiteDto;
import com.example.webdownloadapp.models.Link;
import com.example.webdownloadapp.models.Website;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface IWebsiteService {
    Website getWebsiteByName(String name);
    Website saveWebsite(URL url) throws Exception;
    Optional<Website> getWesiteById(Long id);
    List<Website> getAllWebsites();
    List<Link> findLinksByWebsite(Long websiteId);
}
