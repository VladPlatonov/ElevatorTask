package com.platonov;

import com.platonov.model.Building;
import com.platonov.model.Elevator;
import com.platonov.service.BuildingService;
import com.platonov.service.ElevatorService;
import com.platonov.service.impl.BuildingServiceImpl;
import com.platonov.service.impl.ElevatorServiceImpl;
import com.platonov.utils.Generator;

public class Application {
    private final BuildingService buildingService = new BuildingServiceImpl();
    private final ElevatorService elevatorService = new ElevatorServiceImpl();

    /**
     * This method starts the generation of application data, the first step and the steps of the initial cycle
     */
    public void start(){
        int floor = Generator.getRandomFloor();
        Building building = new Building(floor,new Elevator());
        int currentFloor  = building.getElevator().getCurrentFloor();
        System.out.println(building);
        if(!building.getPassengersInFloor().get(currentFloor).isEmpty()) {
            buildingService.addPassengersToElevator(building,currentFloor);
        }
        buildingService.moveElevator(building);
        for (int i = 0; i < 10; i++) {
            startCycle(building);
        }
    }
    public  void startCycle(Building building){
        int currentFloor  = building.getElevator().getCurrentFloor();
        System.out.println(building);
        if(!elevatorService.isEmpty(building.getElevator())){
            buildingService.addPassengersToFloor(building,currentFloor);
        }
        else {
            if( !building.getPassengersInFloor().get(currentFloor).isEmpty() && building.getElevator().getPassengers().isEmpty()) {
                buildingService.setElevatorDirectionByMajorPart(building, currentFloor);
                buildingService.addPassengersToElevator(building,currentFloor);
            }
        }
        buildingService.moveElevator(building);
    }
}
