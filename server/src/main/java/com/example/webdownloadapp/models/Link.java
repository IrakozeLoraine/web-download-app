package com.example.webdownloadapp.models;

import com.example.webdownloadapp.dto.LinkDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    Website website;

    private Long totalElapsedTime;
    private Long totalDownloadedKilobytes;


    public Link(String name, Long totalDownloadedKilobytes, Long totalElapsedTime, Website website) {
        this.name = name;
        this.totalDownloadedKilobytes = totalDownloadedKilobytes;
        this.totalElapsedTime = totalElapsedTime;
        this.website = website;
    }
}
