package taskElevator.entities;

import java.util.List;

public class Floor {

    private List<Passenger> dispatchContainer;
    private List<Passenger> arrivalContainer;
    private int currentNumber;

    public Floor(List<Passenger> dispatchContainer, int currentNumber) {
        this.dispatchContainer = dispatchContainer;
        this.currentNumber = currentNumber;
    }

    public void addArrivalPassengers (List<Passenger> passengers) {
        arrivalContainer.addAll(passengers);
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void addArrivalPassengers (Passenger passenger) {
        arrivalContainer.add(passenger);
    }

    public void leaveFloor (List<Passenger> passengers) {
        arrivalContainer.removeAll(passengers);
    }

    public void leaveFloor (Passenger passenger) {
        arrivalContainer.remove(passenger);
    }

    @Override
    public String toString() {
        return "Floor{" +
                "dispatchContainer=" + dispatchContainer +
                ", arrivalContainer=" + arrivalContainer +
                ", currentNumber=" + currentNumber +
                '}';
    }
}
