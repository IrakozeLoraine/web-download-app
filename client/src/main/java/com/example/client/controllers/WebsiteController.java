package com.example.client.controllers;

import com.example.client.Dao.ResponseDTO;
import com.example.client.models.WebsiteEntry;
import com.example.client.utils.Downloading;
import com.example.client.utils.Validation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.net.URL;

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

    @PostMapping("download-website")
    public String downloadWebsite(HttpServletRequest request, Model model) throws URISyntaxException {
        String websiteUrl = request.getParameter("websiteUrl");

        if (Validation.isValidURL(websiteUrl)) {
            WebsiteEntry websiteEntry  = Downloading.DownloadWebPage(websiteUrl);
            saveWebsiteRecord(websiteEntry, model);

        return "redirect:/websites";
        } else {
            model.addAttribute("error",
                    "This URL link seems to be invalid. Please Check again");
            return "redirect:/";
        }
    }

    public void saveWebsiteRecord(WebsiteEntry websiteEntry, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ResponseDTO> responseEntity = restTemplate.postForEntity(url + "/websites", websiteEntry, ResponseDTO.class);

        model.addAttribute("website", responseEntity.getBody().getData());

    }
}
