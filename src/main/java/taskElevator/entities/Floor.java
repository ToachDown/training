package taskElevator.entities;

import java.util.ArrayList;
import java.util.List;

public class Floor {

    private List<Passenger> dispatchContainer;
    private List<Passenger> arrivalContainer;
    private int currentNumber;

    public Floor(List<Passenger> dispatchContainer, int currentNumber) {
        this.dispatchContainer = dispatchContainer;
        this.currentNumber = currentNumber;
        this.arrivalContainer = new ArrayList<>();
    }

    public void addArrivalPassengers (List<Passenger> passengers) {
        arrivalContainer.addAll(passengers);
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public List<Passenger> getArrivalContainer() {
        return arrivalContainer;
    }

    public void leaveFloor (List<Passenger> passengers) {
        dispatchContainer.removeAll(passengers);
    }

    public List<Passenger> getDispatchContainer() {
        return dispatchContainer;
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
