package com.example.trucklocator.Controller;


import com.example.trucklocator.Service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@RestController
public class TruckController {


    @Autowired
    private TruckService truckService;

    @GetMapping("/getTruckNames")
    public void getAllNearbyTrucks(@RequestBody Double longitude,@RequestBody Double latitude) {

        truckService.getLongitudeOfAllTrucks();
        List<Double> distances=truckService.getNearByTrucks(longitude,latitude);
        Collections.sort(distances);
        for(int i=0;1<10;i++){
            System.out.println("the "+i+1+"th Truck is available at"+distances.get(i)+"distances");
        }
    }
}
