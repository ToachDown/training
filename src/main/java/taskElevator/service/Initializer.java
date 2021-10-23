package taskElevator.service;

import taskElevator.entities.Building;
import taskElevator.entities.Elevator;
import taskElevator.entities.Floor;
import taskElevator.entities.Passenger;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Initializer {

    private static final Logger LOGGER = Logger.getLogger("INITIALIZER");

    private List<Floor> floors;

    private PropertiesHandler propsHandler;

    public Initializer() throws IOException {
        propsHandler = new PropertiesHandler();
        propsHandler.enableFileLogging();
    }

    public Building initStructure () {
        return new Building(initialFloors(), initialElevator());
    }

    public List<Passenger> FloorsToPassengerList(List<Floor> floors) {
        return floors.stream()
                .map(floor -> floor.getDispatchContainer())
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Floor> initialFloors () {
        var passengers = initialPersons();
        LOGGER.log(MyLogLevel.STARTING_TRANSPORTATION, "filling {0} floors with passengers...", PropertiesHandler.getCountFloors());
        floors = IntStream.range(1, propsHandler.getCountFloors() + 1)
                .boxed()
                .map(numberFloor -> {
                    var currentFloorPassenger = passengers.stream()
                            .filter(passenger -> passenger.getNumberSourceFloor() == numberFloor)
                            .collect(Collectors.toList());
                    return new Floor(currentFloorPassenger ,numberFloor);
                })
                .collect(Collectors.toList());
        return floors;
    }

    private Elevator initialElevator () {
        LOGGER.log(MyLogLevel.STARTING_TRANSPORTATION, "create elevator with {0} seats...", PropertiesHandler.getElevatorCapacity());
        return new Elevator(
                floors.size(),
                propsHandler.getElevatorCapacity(),
                1,
                Elevator.MovementDirection.UP
        );
    }

    private List<Passenger> initialPersons () {
        LOGGER.log(MyLogLevel.STARTING_TRANSPORTATION, "create {0} passengers...", PropertiesHandler.getPassengersCount());
        return IntStream.range(0, propsHandler.getPassengersCount())
                .boxed()
                .map(x -> {
                    var random = new Random();
                    var sourceNumb = random.nextInt(propsHandler.getCountFloors()) + 1;
                    var index = random.nextInt(propsHandler.getCountFloors() - 1);
                    var destinationNumb = IntStream.range(1, propsHandler.getCountFloors() + 1)
                            .filter(numb -> numb != sourceNumb)
                            .toArray()[index];
                    var p = new Passenger(sourceNumb, destinationNumb);
                    return p;
                })
                .collect(Collectors.toList());
    }
}
