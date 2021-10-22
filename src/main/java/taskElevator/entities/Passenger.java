package taskElevator.entities;

import java.util.UUID;

public class Passenger {

    private String id;
    private int numberSourceFloor;
    private int numberDestinationFloor;
    private TransportationState state;

    public enum TransportationState {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }

    public Passenger(int numberSourceFloor, int numberDestinationFloor) {
        this.id = UUID.randomUUID().toString();
        this.numberSourceFloor = numberSourceFloor;
        this.numberDestinationFloor = numberDestinationFloor;
        this.state = TransportationState.NOT_STARTED;
    }

    public Passenger changeState (TransportationState state) {
        this.state = state;
        return this;
    }

    public TransportationState getState() {
        return state;
    }

    public int getNumberSourceFloor() {
        return numberSourceFloor;
    }

    public int getNumberDestinationFloor() {
        return numberDestinationFloor;
    }


    @Override
    public String toString() {
        return "Passenger{" +
                "id='" + id + '\'' +
                ", numberSourceFloor=" + numberSourceFloor +
                ", numberDestinationFloor=" + numberDestinationFloor +
                ", state=" + state +
                '}';
    }
}
