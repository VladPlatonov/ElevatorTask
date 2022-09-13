package com.platonov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Elevator {
    private final Integer size = 5;
    private Integer currentFloor = 1;
    private final List<Integer> passengers = new ArrayList<>();
    Direction direction = Direction.UP;


    public Direction getDirection() {
        return direction;
    }

    public void setCurrentFloor(Integer currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Integer getSize() {
        return size;
    }

    public List<Integer> getPassengers() {
        return passengers;
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }
    public String toString() {
        return  passengers.stream().filter(passenger->passenger!=0)
                .map(Object::toString)
                .collect(Collectors.joining(", ","[","]"));
    }

}
