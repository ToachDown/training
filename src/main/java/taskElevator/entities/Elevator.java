package taskElevator.entities;

import java.util.ArrayList;
import java.util.List;

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
        this.passengers = new ArrayList<>();
    }

    public enum MovementDirection {
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
        this.passengers.removeAll(passengers);
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void changeNumberCurrentFloor (int number) {
        this.numberCurrentRoom = number;
    }

    public int getNumberTopFloor() {
        return numberTopFloor;
    }

    public void addPassengers (List<Passenger> passengers) {
       this.passengers.addAll(passengers);
    }

    public MovementDirection getMovementDirection() {
        return movementDirection;
    }


}
