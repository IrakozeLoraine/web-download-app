package com.example.webdownloadapp.models;

import com.example.webdownloadapp.dto.WebsiteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "website")
public class Website {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String websiteName;
    private String downloadStartDateTime;
    private String downloadEndDateTime;

    private Long totalElapsedTime;
    private Long totalDownloadedKilobytes;

    public Website(String websiteName, String startDate, String endDate, Long totalElapsedTime, Long totalDownloadedKilobytes) {
        setWebsiteName(websiteName);
        setDownloadStartDateTime(startDate);
        setDownloadEndDateTime(endDate);
        setTotalElapsedTime(totalElapsedTime);
        setTotalDownloadedKilobytes(totalDownloadedKilobytes);
    }
}
