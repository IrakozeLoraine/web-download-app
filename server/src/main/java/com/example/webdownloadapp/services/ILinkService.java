package com.example.webdownloadapp.services;

import com.example.webdownloadapp.dto.LinkDto;
import com.example.webdownloadapp.exceptions.RequiredFieldException;
import com.example.webdownloadapp.models.Link;

import javax.naming.directory.InvalidAttributesException;
import java.util.List;

public interface ILinkService {
    Link saveLink(LinkDto linkDto) throws InvalidAttributesException, RequiredFieldException, Exception;
    List<Link> getLinksByWebsite(Long id) throws Exception;
}
