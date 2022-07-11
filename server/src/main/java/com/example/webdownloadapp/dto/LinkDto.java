package com.example.webdownloadapp.dto;

import com.example.webdownloadapp.models.Website;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.net.URL;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LinkDto {
    private URL url;
    private String path;
    private Website website;
}
