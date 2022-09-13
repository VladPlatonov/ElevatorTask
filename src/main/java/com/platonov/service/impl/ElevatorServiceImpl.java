package com.platonov.service.impl;

import com.platonov.model.Direction;
import com.platonov.model.Elevator;
import com.platonov.service.ElevatorService;

import java.util.*;
import java.util.function.Supplier;


public class ElevatorServiceImpl implements ElevatorService {

    @Override
    public void move(Elevator elevator, Integer floor) {
        elevator.setCurrentFloor(floor);
    }

    @Override
    public void addPassenger(Elevator elevator, List<Integer> passenger) {
        elevator.getPassengers().addAll(passenger);
    }

    @Override
    public void removePassenger(Elevator elevator, Integer passenger) {
        elevator.getPassengers().removeAll(Collections.singleton(passenger));
    }

    @Override
    public void setDirection(Elevator elevator, Direction direction) {
        elevator.setDirection(direction);
    }

    @Override
    public Integer getPassengerCurrentFloor(Elevator elevator, Integer floor) {
        return Math.toIntExact(elevator.getPassengers().stream().filter(passenger -> Objects.equals(passenger, floor)).count());
    }

    @Override
    public boolean isFull(Elevator elevator) {
        return elevator.getPassengers().size()==elevator.getSize();
    }

    @Override
    public boolean isEmpty(Elevator elevator) {
        return elevator.getPassengers().isEmpty();
    }

    /**
     *  if elevator is full, next station this is min floor passenger
     * @param elevator
     * @return next floor
     */
    @Override
    public Integer getNextFloor(Elevator elevator) {
        Map<Direction, Supplier<Integer>> moveMap = Map.of(
                Direction.UP, ()-> elevator.getPassengers().stream().mapToInt(p->p).min()
                .orElseThrow(()-> new IllegalArgumentException("Elevator is empty")),
                Direction.DOWN, () -> elevator.getPassengers().stream().mapToInt(p->p)
                .max().orElseThrow(()-> new IllegalArgumentException("Elevator is empty"))
        );
        return Optional.ofNullable(moveMap.get(elevator.getDirection())).orElse(elevator::getCurrentFloor).get();
    }
}
