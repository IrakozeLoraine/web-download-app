package com.example.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteEntry {
    private String url;
//    @JsonProperty("website_name")
//    private String websiteName;
//
//    @JsonProperty("download_start_date_time")
//    private Date downloadStartDateTime;
//
//    @JsonProperty("download_end_date_time")
//    private Date downloadEndDateTime;
//
//    @JsonProperty("total_elapsed_time")
//    private Long totalElapsedTime;
//
//    @JsonProperty("total_downloaded_kilobytes")
//    private Long totalDownloadedKilobytes;
}
