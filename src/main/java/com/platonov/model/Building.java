package com.platonov.model;

import com.platonov.utils.Generator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Building {
    private final Integer floors;
    private final Elevator elevator;
    private Map<Integer, List<Integer>> passengersInFloor;

    public Building(Integer floors, Elevator elevator) {
        this.floors = floors;
        this.elevator = elevator;
        this.passengersInFloor = Generator.generatePassengersInBuilding(floors);
    }

    public int getFloors() {
        return floors;
    }

    public Elevator getElevator() {
        return elevator;
    }


    public Map<Integer, List<Integer>> getPassengersInFloor() {
        return passengersInFloor;
    }

    public void setPassengersInFloor(Map<Integer, List<Integer>> passengersInFloor) {
        this.passengersInFloor = passengersInFloor;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        passengersInFloor.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).forEach(floor -> {
                    if (elevator.getCurrentFloor() == floor.getKey()) {
                        result.append("Floor ").append(floor.getKey()).append(": ")
                                .append(floor.getValue().toString()).append(" Elevator: ")
                                .append(elevator).append("\n");
                    }
                    else {
                        result.append("Floor ").append(floor.getKey()).append(": ")
                                .append(floor.getValue().toString()).append("\n");
                    }
        });
        return result.toString();
    }
}
