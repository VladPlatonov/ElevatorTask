package com.platonov.service;

import com.platonov.model.Building;

import java.util.List;

public interface BuildingService {
    void addPassengersToFloor(Building building, Integer floor);
    void addPassengersToElevator(Building building, Integer floor);
    void removePassengerInFloor(Building building, List<Integer> passengers, Integer floor);
    void moveElevator(Building building);
    void setElevatorDirectionByMajorPart(Building building, Integer floor);
}
