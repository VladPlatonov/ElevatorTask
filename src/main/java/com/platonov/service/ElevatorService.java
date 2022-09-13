package com.platonov.service;

import com.platonov.model.Direction;
import com.platonov.model.Elevator;

import java.util.List;

public interface ElevatorService {
    void move(Elevator elevator,Integer floor);
    void addPassenger(Elevator elevator,List<Integer> passenger);
    void removePassenger(Elevator elevator,Integer passenger);
    void setDirection(Elevator elevator, Direction direction);
    Integer getPassengerCurrentFloor(Elevator elevator, Integer floor);
    boolean isFull(Elevator elevator);
    boolean isEmpty(Elevator elevator);
    Integer getNextFloor(Elevator elevator);
}
