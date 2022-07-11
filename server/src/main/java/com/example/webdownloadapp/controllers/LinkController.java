package com.example.webdownloadapp.controllers;

import com.example.webdownloadapp.dto.LinkDto;
import com.example.webdownloadapp.models.Link;
import com.example.webdownloadapp.services.ILinkService;
import com.example.webdownloadapp.utils.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/links")
public class LinkController {

    private final ILinkService linkService;

    public LinkController(ILinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/{websiteId}")
    public ResponseEntity<APIResponse> getWebsiteLinks(@PathVariable Long websiteId) throws Exception {
        List<Link> links = linkService.getLinksByWebsite(websiteId);
        return ResponseEntity.ok().body(new APIResponse(HttpStatus.OK, "", links));
    }

    @PostMapping()
    public ResponseEntity<APIResponse> createLink(@Valid @RequestBody LinkDto linkDto) throws Exception {
        Link link = linkService.saveLink(linkDto);
        return ResponseEntity.ok().body(new APIResponse(HttpStatus.OK, "SUCCESSFULLY RECORDED", link));
    }
}
