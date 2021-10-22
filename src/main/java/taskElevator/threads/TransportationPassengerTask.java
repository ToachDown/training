package taskElevator.threads;

import taskElevator.entities.Building;
import taskElevator.entities.Floor;
import taskElevator.entities.Passenger;
import taskElevator.service.Controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class TransportationPassengerTask implements Runnable{

    private Logger LOGGER = Logger.getLogger("Passengers");

    private List<Floor> floors;
    private Passenger passenger;

    public TransportationPassengerTask(Passenger passenger, Building building) {
        this.passenger = passenger;
        this.floors = building.getFloors();
    }

    @Override
    public void run() {
        try {
            LOGGER.log(INFO, "{0} begin way", passenger.toString());
            passenger.changeState(Passenger.TransportationState.IN_PROGRESS);
            while (true) {
                Thread.sleep(1000);
                var arrivalFloor = floors.stream()
                        .filter(floor -> floor.getCurrentNumber() == passenger.getNumberDestinationFloor())
                        .filter(floor -> floor.getArrivalContainer().contains(passenger))
                        .findFirst();
                if (arrivalFloor.isPresent()) {
                    LOGGER.log(INFO, "{0} passenger completed way to floor", passenger.toString());
                    passenger.changeState(Passenger.TransportationState.COMPLETED);
                    break;
                }
            }
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
