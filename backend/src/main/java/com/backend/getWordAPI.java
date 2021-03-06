package com.backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.backend.getAPIKey;
import com.backend.getAPIKey.KeyType;

@RestController
@CrossOrigin(origins = "*") // @CrossOrigin(origins = "http://localhost:8080")
public class getWordAPI{
    getAPIKey getkey = new getAPIKey();
    private String DEFAULT_URL = "https://opendict.korean.go.kr/api/search?";
    private final String API_KEY = "key="+getkey.GetKey(KeyType.DictKey);
    private String type = "&target_type=search";
    private String part = "&part=word";
    private String q = "&q=나무";
    private String sort = "&sort=dict";
    @PostMapping("/test")
    public Map addMember(){
        System.out.println("test");
        Map map = new HashMap();
        map.put("action", "create");
        return map;
    }
    @GetMapping("/callDictAPI")
    public String addmember(@RequestParam("word") String word){
        StringBuffer result = new StringBuffer();
        try{
            q = "&q=" + word;
            String urlStr=DEFAULT_URL+API_KEY+type+part+q+sort;
            URL url = new URL(urlStr);

            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));

            String returnLine;
            while((returnLine=br. readLine()) != null){
                result.append((returnLine+"\n"));
            }
            urlconnection.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    public RedirectView temp(){
        String fullUrl = DEFAULT_URL+API_KEY+type+part+q+sort;
        System.out.println(fullUrl);
        return new RedirectView(fullUrl);
    }
}
