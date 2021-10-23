package taskElevator.service;

import taskElevator.entities.Elevator;
import taskElevator.entities.Floor;
import taskElevator.entities.Passenger;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Controller {

    private static final Logger LOGGER = Logger.getLogger("CONTROLLER");

    public Passenger startMovePassenger (Passenger passenger) {
        LOGGER.log(MyLogLevel.STARTING_TRANSPORTATION, "passenger [{0}] begin move", passenger.getId());
        passenger.changeState(Passenger.TransportationState.IN_PROGRESS);
        return passenger;
    }

    public void closeElevatorDoors () {
        LOGGER.log(MyLogLevel.MOVING_ELEVATOR, "elevator close th doors");
    }

    public void openElevatorDoors () {
        LOGGER.log(MyLogLevel.MOVING_ELEVATOR, "elevator open the doors");
    }

    public void startMoveElevator (Elevator elevator) {
        LOGGER.log(MyLogLevel.STARTING_TRANSPORTATION, "elevator start moving on {0} floor", elevator.getNumberCurrentFloor());
    }

    public void stopElevator (Elevator elevator) {
        LOGGER.log(MyLogLevel.MOVING_ELEVATOR, "elevator stopped on {0} floor", elevator.getNumberCurrentFloor());
    }

    public void endWordElevator () {
        LOGGER.log(MyLogLevel.COMPLETION_TRANSPORTATION, "elevator end work ");
    }

    public Passenger completePassengerMoving (Passenger passenger) {
        LOGGER.log(MyLogLevel.COMPLETION_TRANSPORTATION, "passenger [{0}] end moving", passenger.getId());
        passenger.changeState(Passenger.TransportationState.COMPLETED);
        return passenger;
    }

    public Floor getCurrentFloor (Elevator elevator, List<Floor> floors) {
        return floors.stream()
                .filter(floor -> floor.getCurrentNumber() == (elevator.getNumberCurrentFloor()))
                .findFirst()
                .get();
    }

    public List<Passenger> getOutgoingPassengers (Elevator elevator) {
        return elevator.getPassengers()
                .stream()
                .filter(passenger -> passenger.getNumberDestinationFloor() == elevator.getNumberCurrentFloor())
                .collect(Collectors.toList());
    }

    public Elevator removePassengersFromElevator (Elevator elevator, List<Passenger> passengers) {
        elevator.removePassengers(passengers);
        return elevator;
    }

    public boolean isEndWork (Elevator elevator, List<Floor> floors) {
        var hasPassengers= floors.stream()
                .anyMatch(floor -> !floor.getDispatchContainer().isEmpty());
        var allCompletedPassengers = floors.stream()
                .map(Floor::getArrivalContainer)
                .flatMap(List::stream)
                .filter(passenger -> passenger.getState().toString().equals(Passenger.TransportationState.COMPLETED.toString()))
                .count();
        var isAllPassengersOnHisFloor = floors.stream()
                .anyMatch(floor -> floor.getArrivalContainer()
                        .stream()
                        .noneMatch(passenger -> passenger.getNumberDestinationFloor() != floor.getCurrentNumber())
                );
        var isAllReleasedPassengers = allCompletedPassengers == PropertiesHandler.getPassengersCount();
        return elevator.getPassengers().isEmpty() && !hasPassengers && isAllReleasedPassengers && isAllPassengersOnHisFloor;
    }

    public boolean checkEndPassengerMove (List<Floor> floors, Passenger passenger) {
        return floors.stream()
                .filter(floor -> floor.getCurrentNumber() == passenger.getNumberDestinationFloor())
                .anyMatch(floor -> floor.getArrivalContainer().contains(passenger));
    }

    public List<Passenger> getSeatedPassengers (Floor floor, Elevator elevator) {
        return floor.getDispatchContainer()
                .stream()
                .filter(passenger -> passenger.getState().toString().equals(Passenger.TransportationState.IN_PROGRESS.toString()))
                .limit(elevator.getCapacity() - elevator.getPassengers().size())
                .collect(Collectors.toList());
    }

    public Elevator addPassengerToElevator (Elevator elevator, List<Passenger> passengers) {
        elevator.addPassengers(passengers);
        return elevator;
    }

    public List<Floor> removePassengerFromFloor (List<Floor> floors,
                                                 Floor currentFloor,
                                                 List<Passenger> seatedPassengers) {
        seatedPassengers.forEach(passenger ->
                LOGGER.log(MyLogLevel.BOARDING_OF_PASSENGER, "passenger [{0}] on floor-{1}", new String[]{
                        passenger.getId(),
                        String.valueOf(currentFloor.getCurrentNumber())
                }));
        floors.remove(currentFloor);
        currentFloor.leaveFloor(seatedPassengers);
        floors.add(currentFloor);
        return floors;
    }

    public List<Floor> dropPassengersToFloor (List<Floor> floors,
                                       Floor currentFloor,
                                       List<Passenger> outgoingPassengers) {
        outgoingPassengers.forEach(passenger ->
                LOGGER.log(MyLogLevel.DEBOARDING_OF_PASSENGER, "passenger [{0}] on floor-{1}", new String[]{
                        passenger.getId(),
                        String.valueOf(currentFloor.getCurrentNumber())
                }));
        floors.remove(currentFloor);
        currentFloor.addArrivalPassengers(outgoingPassengers);
        floors.add(currentFloor);
        return floors;
    }

    public Elevator moveElevator (Elevator elevator) {
            if(elevator.getNumberTopFloor() == elevator.getNumberCurrentFloor()) {
                LOGGER.log(MyLogLevel.MOVING_ELEVATOR, "from {0} to {1} floor:", new String[] {
                        String.valueOf(elevator.getNumberCurrentFloor()),
                        String.valueOf(elevator.getNumberCurrentFloor() - 1)
                });
                elevator.changeNumberCurrentFloor(elevator.getNumberCurrentFloor() - 1);
                elevator.changeMovementDirection(Elevator.MovementDirection.DOWN);
                return elevator;
            }
            if(elevator.getNumberCurrentFloor() <= 1) {
                LOGGER.log(MyLogLevel.MOVING_ELEVATOR, "from {0} to {1} floor:", new String[] {
                        String.valueOf(elevator.getNumberCurrentFloor()),
                        String.valueOf(elevator.getNumberCurrentFloor() + 1)
                });
                elevator.changeNumberCurrentFloor(elevator.getNumberCurrentFloor() + 1);
                elevator.changeMovementDirection(Elevator.MovementDirection.UP);
                return elevator;
            }
            if(elevator.getMovementDirection().toString().equals(Elevator.MovementDirection.UP.toString())) {
                LOGGER.log(MyLogLevel.MOVING_ELEVATOR, "from {0} to {1} floor:", new String[] {
                        String.valueOf(elevator.getNumberCurrentFloor()),
                        String.valueOf(elevator.getNumberCurrentFloor() + 1)
                });
                elevator.changeNumberCurrentFloor(elevator.getNumberCurrentFloor() + 1);
                return elevator;
            }
            if(elevator.getMovementDirection().toString().equals(Elevator.MovementDirection.DOWN.toString())) {
                LOGGER.log(MyLogLevel.MOVING_ELEVATOR, "from {0} to {1} floor:", new String[] {
                        String.valueOf(elevator.getNumberCurrentFloor()),
                        String.valueOf(elevator.getNumberCurrentFloor() -1)
                });
                elevator.changeNumberCurrentFloor(elevator.getNumberCurrentFloor() - 1);
                return elevator;
            }
            return elevator;
    }

}
