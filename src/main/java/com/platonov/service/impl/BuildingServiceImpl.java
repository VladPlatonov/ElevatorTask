package com.platonov.service.impl;

import com.platonov.model.Building;
import com.platonov.model.Direction;
import com.platonov.model.Elevator;
import com.platonov.service.BuildingService;
import com.platonov.service.ElevatorService;
import com.platonov.utils.Generator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BuildingServiceImpl implements BuildingService {

    private final ElevatorService elevatorService = new ElevatorServiceImpl();

    /**
     * This method first remove passenger then take new passenger in elevator if floor is not empty
     * after all actions add new passenger in floor
     * @param building
     * @param floor
     */
    @Override
    public void addPassengersToFloor(Building building, Integer floor) {
        int size = elevatorService.getPassengerCurrentFloor(building.getElevator(), floor);
        elevatorService.removePassenger(building.getElevator(), floor);
        if(!building.getPassengersInFloor().get(floor).isEmpty()) {
            addPassengersToElevator(building,floor);
        }
        for (int i = 0; i < size; i++) {
            building.getPassengersInFloor().get(floor).add(Generator.getRandomPassenger(building.getFloors()));
        }
    }
    /**
     * This method  take new passenger in elevator
     * @param building
     * @param floor
     */
    @Override
    public void addPassengersToElevator(Building building, Integer floor) {
        Elevator elevator = building.getElevator();
        Map<Direction, Consumer<Direction>> moveMap = Map.of(Direction.UP, (value) -> {
            List<Integer> passenger = getPassengersCanUp(building, elevator, floor);
            elevatorService.addPassenger(elevator, passenger);
            removePassengerInFloor(building, passenger, floor);
        }, Direction.DOWN, (value) -> {
            List<Integer> passenger = getPassengersCanDown(building, elevator, floor);
            elevatorService.addPassenger(elevator, passenger);
            removePassengerInFloor(building, passenger, floor);
        });
        Optional.ofNullable(moveMap.get(elevator.getDirection())).orElse((value) -> System.out.println("Not found Direction")).accept(elevator.getDirection());
    }
    @Override
    public void removePassengerInFloor(Building building, List<Integer> passengers, Integer floor) {
        passengers.forEach(s -> building.getPassengersInFloor().get(floor).remove(s));
    }
    @Override
    public void moveElevator(Building building) {
        Elevator elevator = building.getElevator();
        if(building.getPassengersInFloor().get(elevator.getCurrentFloor()).isEmpty()|| !elevatorService.isFull(elevator)){
            elevatorService.move(elevator,getNextFloor(building));
        }
        else{
            elevatorService.move(elevator,elevatorService.getNextFloor(elevator));
        }

    }

    private List<Integer> getPassengersCanUp(Building building, Elevator elevator, Integer floor) {
        return building.getPassengersInFloor().get(floor).stream()
                .filter(passenger -> passenger > floor)
                .limit(elevator.getSize() - elevator.getPassengers().size())
                .collect(Collectors.toList());
    }

    private List<Integer> getPassengersCanDown(Building building, Elevator elevator, Integer floor) {
        return building.getPassengersInFloor().get(floor).stream()
                .filter(passenger -> passenger < floor)
                .limit(elevator.getSize() - elevator.getPassengers().size())
                .collect(Collectors.toList());
    }
    @Override
    public void setElevatorDirectionByMajorPart(Building building, Integer floor) {
        boolean result = building.getPassengersInFloor().get(floor).stream().filter(passenger -> passenger > floor).count() >
                building.getPassengersInFloor().get(floor).stream().filter(passenger -> passenger < floor).count();
        if (building.getElevator().getCurrentFloor()==1 || result) {
            elevatorService.setDirection(building.getElevator(), Direction.UP);
        } else{
            elevatorService.setDirection(building.getElevator(), Direction.DOWN);
        }
    }

    private Integer getNextFloor(Building building) {
        Elevator elevator = building.getElevator();
        Map<Direction, Supplier<Integer>> moveMap = Map.of(
                Direction.UP, () ->
                building.getPassengersInFloor().values().stream()
                        .flatMap(List::stream)
                        .mapToInt(Integer::valueOf)
                        .filter(s->s>building.getElevator().getCurrentFloor())
                        .min().orElse((building.getElevator().getCurrentFloor()-1)),
                Direction.DOWN, () -> building.getPassengersInFloor().values().stream()
                        .flatMap(List::stream)
                        .mapToInt(Integer::valueOf)
                        .filter(s->s<building.getElevator().getCurrentFloor())
                        .max().orElse((building.getElevator().getCurrentFloor()+1)));
        return Optional.ofNullable(moveMap.get(elevator.getDirection())).orElse(()->building.getElevator().getCurrentFloor()).get();
    }
}
