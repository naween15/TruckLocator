package com.example.trucklocator.Service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruckService {
    private List<String> longitudes=new ArrayList<>();
   private List<String> latitudes=new ArrayList<>();


    public void getLongitudeOfAllTrucks(){
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/trucks.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=1;i<483;i++){

            String longitude=(records.get(i).get(15));
            longitudes.add(longitude);

            String latitude=(records.get(i).get(14));
            latitudes.add(latitude);
       }

    }



    public List<Double> getNearByTrucks(Double Longitude, Double latitude){
        List<Double> longitudes1 =longitudes.stream().map(ds->Double.parseDouble(ds)).collect(Collectors.toList());
        List<Double> latitudes1=latitudes.stream().map(ds->Double.parseDouble(ds)).collect(Collectors.toList());
//        Using haversine formula to use the longitude to generate distance
//        Converting longitude in degree to radian
        List<Double> longInRad=new ArrayList<>();
        List<Double> latInRad=new ArrayList<>();
        List<Double> distance=new ArrayList<>();

        for (int i=0;i<483;i++){
            longInRad.add(Double.valueOf(longitudes.get(i)));
            latInRad.add(Double.valueOf(latitudes.get(i)));
            distance.add((6378.8)*Math.cos((Math.sin(latitude)*Math.sin(latInRad.get(i)))+(Math.cos(latitude)*Math.cos(latInRad.get(i))*(Math.cos(Longitude-longInRad.get(i))))));
        }
        return distance;

    }
}
