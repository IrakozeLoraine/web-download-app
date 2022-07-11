package com.example.client.controllers;

import com.example.client.Dao.ResponseDTO;
import com.example.client.models.WebsiteEntry;
import com.example.client.utils.Downloading;
import com.example.client.utils.Validation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class WebsiteController {

    private static String url = "http://localhost:8082/api/v1";

    @GetMapping("")
    public String homePage(){
        return "index";
    }

    @GetMapping("websites")
    public String report( Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseDTO> websiteResponse = restTemplate.getForEntity(url+"/websites", ResponseDTO.class);

        model.addAttribute("websites", websiteResponse.getBody().getData());
        return "websites";
    }

    @GetMapping("/links/{websiteId}")
    public String links(HttpServletRequest request, Model model, @PathVariable Long websiteId) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ResponseDTO> websitesResponse = restTemplate.exchange(url+"/websites/links/"+websiteId, HttpMethod.GET, entity, ResponseDTO.class);

        model.addAttribute("links", Objects.requireNonNull(websitesResponse.getBody().getData()));

        return "links";
    }

    @PostMapping("download-website")
    public String downloadWebsite(HttpServletRequest request, Model model) throws URISyntaxException {
        String websiteUrl = request.getParameter("websiteUrl");
        RestTemplate restTemplate = new RestTemplate();
        WebsiteEntry websiteEntry = new WebsiteEntry(websiteUrl);

        if (Validation.isValidURL(websiteUrl)) {
            ResponseEntity<ResponseDTO> responseEntity = restTemplate.postForEntity(url + "/websites", websiteEntry, ResponseDTO.class);

            model.addAttribute("website", responseEntity.getBody().getData());

        return "redirect:/websites";
        } else {
            model.addAttribute("error",
                    "This URL link seems to be invalid. Please Check again");
            return "index";
        }
    }
}
