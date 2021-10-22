package taskElevator.threads;

import taskElevator.entities.Building;
import taskElevator.entities.Passenger;
import taskElevator.service.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TransportationPassengerTask implements Runnable{

    private Logger LOGGER = Logger.getLogger("Passengers");

    private Passenger passenger;

    public TransportationPassengerTask(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "{0} begin work", passenger.toString());
        passenger.changeState(Passenger.TransportationState.IN_PROGRESS);
    }
}
