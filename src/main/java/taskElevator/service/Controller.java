package taskElevator.service;

import taskElevator.entities.Elevator;
import taskElevator.entities.Floor;
import taskElevator.entities.Passenger;

import java.util.List;
import java.util.stream.Collectors;

public class Controller {

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

    private List<Passenger> completeStatePassengers (List<Passenger> exitingPassengers) {
        return exitingPassengers
                .stream()
                .map(passenger -> passenger.changeState(Passenger.TransportationState.COMPLETED))
                .collect(Collectors.toList());
    }

    public Elevator removePassengersFromElevator (Elevator elevator, List<Passenger> passengers) {
        elevator.removePassengers(passengers);
        return elevator;
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
        floors.remove(currentFloor);
        currentFloor.leaveFloor(seatedPassengers);
        floors.add(currentFloor);
        return floors;
    }

    public List<Floor> dropPassengersToFloor (List<Floor> floors,
                                       Floor currentFloor,
                                       List<Passenger> outgoingPassengers) {
        floors.remove(currentFloor);
        outgoingPassengers = completeStatePassengers(outgoingPassengers);
        currentFloor.addArrivalPassengers(outgoingPassengers);
        floors.add(currentFloor);
        return floors;
    }

    public Elevator moveElevator (Elevator elevator) {
            if(elevator.getNumberTopFloor() == elevator.getNumberCurrentFloor()) {
                elevator.changeMovementDirection(Elevator.MovementDirection.DOWN);
                elevator.changeNumberCurrentFloor(elevator.getNumberCurrentFloor() - 1);
                return elevator;
            }
            if(elevator.getNumberCurrentFloor() <= 1) {
                elevator.changeMovementDirection(Elevator.MovementDirection.UP);
                elevator.changeNumberCurrentFloor(elevator.getNumberCurrentFloor() + 1);
                return elevator;
            }
            if(elevator.getMovementDirection().toString().equals(Elevator.MovementDirection.UP.toString())) {
                elevator.changeNumberCurrentFloor(elevator.getNumberCurrentFloor() + 1);
                return elevator;
            }
            if(elevator.getMovementDirection().toString().equals(Elevator.MovementDirection.DOWN.toString())) {
                elevator.changeNumberCurrentFloor(elevator.getNumberCurrentFloor() - 1);
                return elevator;
            }
            return elevator;
    }

}
