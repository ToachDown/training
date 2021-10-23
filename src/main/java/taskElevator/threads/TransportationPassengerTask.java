package taskElevator.threads;

import taskElevator.entities.Building;
import taskElevator.entities.Floor;
import taskElevator.entities.Passenger;
import taskElevator.service.Controller;

import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class TransportationPassengerTask implements Runnable{

    private Logger LOGGER = Logger.getLogger("Passengers");

    private List<Floor> floors;
    private Passenger passenger;
    private Controller controller;

    public TransportationPassengerTask(Passenger passenger, Building building) {
        this.passenger = passenger;
        this.floors = building.getFloors();
        controller = new Controller();
    }

    @Override
    public void run() {
        try {
            passenger = controller.startMovePassenger(passenger);
            while (true) {
                Thread.sleep(200);
                var isCompleteMove = controller.checkEndPassengerMove(floors, passenger);
                if (isCompleteMove) {
                    passenger = controller.completePassengerMoving(passenger);
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
