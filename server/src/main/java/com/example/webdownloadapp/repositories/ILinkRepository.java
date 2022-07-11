package com.example.webdownloadapp.repositories;

import com.example.webdownloadapp.models.Link;
import com.example.webdownloadapp.models.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILinkRepository extends JpaRepository<Link, Long> {
    public List<Link> findByWebsite(Website website);
}
