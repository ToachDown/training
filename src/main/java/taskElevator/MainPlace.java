package taskElevator;

import taskElevator.service.Initializer;
import taskElevator.threads.ElevatorMovementTask;
import taskElevator.threads.TransportationPassengerTask;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainPlace {

    public static void main(String[] args) throws IOException {
        var initializer = new Initializer();
        var building = initializer.initStructure();
        var passengers = initializer.FloorsToPassengerList(building.getFloors());

        var executorElevator = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        var executorPassengers = (ThreadPoolExecutor) Executors.newFixedThreadPool(passengers.size());

        passengers.forEach(passenger -> executorPassengers.execute(new TransportationPassengerTask(passenger, building)));
        executorElevator.execute(new ElevatorMovementTask(building));
        executorElevator.shutdown();
        executorPassengers.shutdown();
    }
}
