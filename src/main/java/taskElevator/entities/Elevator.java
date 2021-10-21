package taskElevator.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Elevator {

    private int numberTopFloor;
    private int capacity;
    private List<Passenger> passengers;
    private int numberCurrentRoom;
    private MovementDirection movementDirection;

    public Elevator(int numberTopFloor, int capacity, int numberCurrentRoom, MovementDirection movementDirection) {
        this.numberTopFloor = numberTopFloor;
        this.capacity = capacity;
        this.numberCurrentRoom = numberCurrentRoom;
        this.movementDirection = movementDirection;
    }

    public static enum MovementDirection {
        UP,
        DOWN
    }

    public int getNumberCurrentFloor() {
        return numberCurrentRoom;
    }

    public void changeMovementDirection (MovementDirection direction) {
        this.movementDirection = direction;
    }

    public void removePassengers(List<Passenger> passengers) {
        if(this.passengers.containsAll(passengers)) {
            this.passengers.removeAll(passengers);
        } else {
            this.passengers.removeAll(
                    passengers.stream()
                            .filter(passenger -> this.passengers.contains(passenger))
                            .collect(Collectors.toList())
            );
        }
    }

    public List<Passenger> addPassengers (List<Passenger> passengers) {
        if(passengers.size() + this.passengers.size() > capacity) {
            var successEntered = passengers.stream()
                    .limit(capacity - this.passengers.size())
                    .collect(Collectors.toList());
            this.passengers.addAll(successEntered);
            return passengers.stream()
                    .filter(passenger -> !successEntered.contains(passenger))
                    .collect(Collectors.toList());
        } else {
            this.passengers.addAll(passengers);
            return Collections.emptyList();
        }
    }

    public void move () {
        if(numberTopFloor == numberCurrentRoom) {
            movementDirection = MovementDirection.DOWN;
            numberCurrentRoom--;
            return;
        }
        if(numberCurrentRoom == 1) {
            movementDirection = MovementDirection.UP;
            numberCurrentRoom++;
            return;
        }
        if(movementDirection.toString().equals(MovementDirection.UP)) {
            numberCurrentRoom++;
            return;
        }
        if(movementDirection.toString().equals(MovementDirection.DOWN)) {
            numberCurrentRoom--;
            return;
        }
    }
}
