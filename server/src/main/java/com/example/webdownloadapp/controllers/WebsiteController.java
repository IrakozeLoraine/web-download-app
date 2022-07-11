package com.example.webdownloadapp.controllers;

import com.example.webdownloadapp.dto.WebsiteDto;
import com.example.webdownloadapp.models.Website;
import com.example.webdownloadapp.services.IWebsiteService;
import com.example.webdownloadapp.utils.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/v1/websites")
public class WebsiteController {

    private final IWebsiteService websiteService;

    public WebsiteController(IWebsiteService websiteService) {
        this.websiteService = websiteService;
    }


    @GetMapping()
    public ResponseEntity<APIResponse> getAllWebsites() {
        List<Website> websites = websiteService.getAllWebsites();
        return ResponseEntity.ok().body(new APIResponse(HttpStatus.OK, "", websites));
    }

    @GetMapping("/{name}")
    public ResponseEntity<APIResponse> getWebsiteByName(@RequestParam String name) {
        Website website = websiteService.getWebsiteByName(name);
        return ResponseEntity.ok().body(new APIResponse(HttpStatus.OK, "", website));
    }

    @PostMapping()
    public ResponseEntity<APIResponse> createWebsite(@Valid @RequestBody WebsiteDto websiteDto) throws Exception {
        URL url = new URL(websiteDto.getUrl());
        Website website = websiteService.saveWebsite(url);
        return ResponseEntity.ok().body(new APIResponse(HttpStatus.OK, "SUCCESSFULLY RECORDED", website));
    }
}
