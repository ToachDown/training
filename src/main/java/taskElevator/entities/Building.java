package taskElevator.entities;

import java.util.List;

public class Building {

    private List<Floor> floors;
    private Elevator elevator;

    public Building(List<Floor> floors, Elevator elevator) {
        this.floors = floors;
        this.elevator = elevator;
    }


}
