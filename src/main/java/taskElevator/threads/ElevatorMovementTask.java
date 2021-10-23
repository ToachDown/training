package taskElevator.threads;

import taskElevator.entities.Building;
import taskElevator.entities.Elevator;
import taskElevator.entities.Floor;
import taskElevator.service.Controller;

import java.util.List;
import java.util.logging.Logger;

public class ElevatorMovementTask implements Runnable{

    private Elevator elevator;

    private List<Floor> floors;

    private Controller controller;


    public ElevatorMovementTask(Building building) {
        this.elevator = building.getElevator();
        this.floors = building.getFloors();
        this.controller = new Controller();
    }

    @Override
    public void run() {
        try {

            Thread.sleep(1500);
            controller.startMoveElevator(elevator);
            Thread.sleep(300);
            while (true) {
                controller.stopElevator(elevator);
                Thread.sleep(200);

                var currentFloor = controller.getCurrentFloor(elevator, floors);
                var exitingPassengers = controller.getOutgoingPassengers(elevator);

                controller.openElevatorDoors();
                Thread.sleep(300);

                if(!exitingPassengers.isEmpty()) {

                    floors = controller.dropPassengersToFloor(floors, currentFloor, exitingPassengers);
                    elevator = controller.removePassengersFromElevator(elevator, exitingPassengers);
                    Thread.sleep(500);
                }
                currentFloor = controller.getCurrentFloor(elevator, floors);
                var drivePassengers = controller.getSeatedPassengers(currentFloor , elevator);

                if (!drivePassengers.isEmpty()) {

                    floors = controller.removePassengerFromFloor(floors, currentFloor, drivePassengers);
                    elevator = controller.addPassengerToElevator(elevator, drivePassengers);
                    Thread.sleep(500);
                }

                controller.closeElevatorDoors();
                Thread.sleep(300);

                elevator = controller.moveElevator(elevator);
                Thread.sleep(1000);

                var isEndWork = controller.isEndWork(elevator, floors);
                if(isEndWork) {
                    controller.endWordElevator();
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
