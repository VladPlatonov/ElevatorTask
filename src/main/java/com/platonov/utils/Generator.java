package com.platonov.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Generator {
    private static final Random random = new Random();

    public static int getRandomFloor(){
        return random.nextInt(16)+5;
    }
    public static Map<Integer, List<Integer>> generatePassengersInBuilding(int floors){
        Map<Integer,List<Integer>> passengers = new HashMap<>();
        for(int i =1;i<=floors;i++){
            passengers.put(i,generatePassengers(floors));
        }
        return passengers;
    }
    public static int getRandomPassenger(Integer floor){
        return random.nextInt(floor)+1;
    }
    private static List<Integer> generatePassengers(int floors){
        return random.ints(1,floors)
                .limit(random.nextInt(11)).boxed()
                .collect(Collectors.toList());
    }
}
