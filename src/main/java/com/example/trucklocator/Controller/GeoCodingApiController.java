package com.example.trucklocator.Controller;


import com.example.trucklocator.Model.GeoCoding;
import com.example.trucklocator.Model.GeoCodingResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
public class GeoCodingApiController {

    private static final Logger log = LoggerFactory.getLogger(GeoCodingApiController.class);
    private static final String GEOCODING_URI = "https://maps.googleapis.com/maps/api/geocode/json";
    @Value("${apiKey}")
    private String apikey;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/getGeoCoding",method = RequestMethod.GET)
    public GeoCodingResult getGeoCodingForLoc(@RequestParam(value = "address", defaultValue="silicon+valley") String address) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String encodedAddress = URLEncoder.encode(address, "UTF-8");
        Request request = new Request.Builder().url("https://maps.googleapis.com/maps/api/geocode/json?address="+address+"?key="+apikey).build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        ObjectMapper objectMapper = new ObjectMapper();
        GeoCodingResult result = objectMapper.readValue(responseBody.string(), GeoCodingResult.class);
        return result;
    }
}