package com.example.webdownloadapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LinkDto {
    private String name;

    @JsonProperty("website_id")
    private Long websiteId;

    @JsonProperty("total_elapsed_time")
    private String totalElapsedTime;

    @JsonProperty("total_downloaded_kilobytes")
    private String totalDownloadedKilobytes;
}
