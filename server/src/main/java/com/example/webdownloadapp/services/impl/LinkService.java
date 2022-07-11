package com.example.webdownloadapp.services.impl;

import com.example.webdownloadapp.dto.LinkDto;
import com.example.webdownloadapp.exceptions.RequiredFieldException;
import com.example.webdownloadapp.exceptions.ResourceNotFoundException;
import com.example.webdownloadapp.models.Link;
import com.example.webdownloadapp.models.Website;
import com.example.webdownloadapp.repositories.ILinkRepository;
import com.example.webdownloadapp.services.ILinkService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class LinkService implements ILinkService {

    private final ILinkRepository linkRepository;
    private final WebsiteService websiteService;

    public LinkService(ILinkRepository linkRepository, WebsiteService websiteService) {
        this.linkRepository = linkRepository;
        this.websiteService = websiteService;
    }

    @Override
    public Link saveLink(LinkDto linkDto) throws Exception {
        if (linkDto.getName().equals("")){
            throw new RequiredFieldException("name", "link");
        }
        if (linkDto.getTotalDownloadedKilobytes().equals("")){
            throw new RequiredFieldException("total downloaded kilobytes", "link");
        }
        if (linkDto.getTotalElapsedTime().equals("")){
            throw new RequiredFieldException("total elapsed time", "link");
        }
        if (linkDto.getWebsiteId().equals("")){
            throw new RequiredFieldException("website id", "link");
        }

        Optional<Website> website = websiteService.getWesiteById(linkDto.getWebsiteId());

        if (website.isEmpty()) throw new ResourceNotFoundException("Website", "id");

        return linkRepository.save(new Link(linkDto.getName(), linkDto.getTotalDownloadedKilobytes(), linkDto.getTotalElapsedTime(), website.get()));
    }

    @Override
    public List<Link> getLinksByWebsite(Long id) throws Exception {
        Optional<Website> website = websiteService.getWesiteById(id);
        if (website.isEmpty()) throw new ResourceNotFoundException("Website", "id");
        return linkRepository.findByWebsite(website.get());
    }
}
