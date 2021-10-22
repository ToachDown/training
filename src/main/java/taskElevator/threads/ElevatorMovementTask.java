package taskElevator.threads;

import taskElevator.entities.Building;
import taskElevator.entities.Elevator;
import taskElevator.entities.Floor;
import taskElevator.entities.Passenger;
import taskElevator.service.Controller;
import taskElevator.service.Initializer;

import javax.print.attribute.standard.Finishings;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Level.INFO;

public class ElevatorMovementTask implements Runnable{

    private Elevator elevator;

    private List<Floor> floors;

    private Controller controller;

    private static Logger LOGGER = Logger.getLogger("elevator");

    public ElevatorMovementTask(Building building) throws IOException {
        this.elevator = building.getElevator();
        this.floors = building.getFloors();
        this.controller = new Controller();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            while (true) {
                Thread.sleep(200);
                LOGGER.log(INFO, "elevator stopped");
                var currentFloor = controller.getCurrentFloor(elevator, floors);
                var exitingPassengers = controller.getOutgoingPassengers(elevator);
                LOGGER.log(INFO, "elevator open the doors");
                Thread.sleep(300);
                if(!exitingPassengers.isEmpty()) {
                    LOGGER.log(INFO, "{0} passengers exiting from elevator", exitingPassengers.size());

                    floors = controller.dropPassengersToFloor(floors, currentFloor, exitingPassengers);
                    elevator = controller.removePassengersFromElevator(elevator, exitingPassengers);

                    Thread.sleep(500);
                }
                currentFloor = controller.getCurrentFloor(elevator, floors);
                var drivePassengers = controller.getSeatedPassengers(currentFloor , elevator);
                if (!drivePassengers.isEmpty()) {
                    LOGGER.log(INFO, "{0} passengers entered to elevator", drivePassengers.size());

                    floors = controller.removePassengerFromFloor(floors, currentFloor, drivePassengers);
                    elevator = controller.addPassengerToElevator(elevator, drivePassengers);

                    Thread.sleep(500);
                }
                LOGGER.log(INFO, "elevator close doors");
                Thread.sleep(300);
                elevator = controller.moveElevator(elevator);
                Thread.sleep(1000);
                LOGGER.log(INFO, "elevator move {0} to {1} floor:",
                        new String[] {
                                elevator.getMovementDirection().toString(),
                                String.valueOf(elevator.getNumberCurrentFloor())
                });
                var hasPassengers= floors.stream().anyMatch(floor -> !floor.getDispatchContainer().isEmpty());
                if(elevator.getPassengers().isEmpty() && !hasPassengers) {
                    LOGGER.log(INFO, "elevator end work");
                    break;
                }
            }
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
