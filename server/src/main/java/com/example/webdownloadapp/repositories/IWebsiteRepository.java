package com.example.webdownloadapp.repositories;

import com.example.webdownloadapp.models.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWebsiteRepository extends JpaRepository<Website, Long> {
    public Website findByWebsiteName(String name);
}
