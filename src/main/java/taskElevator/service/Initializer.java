package taskElevator.service;

import taskElevator.entities.Elevator;
import taskElevator.entities.Floor;
import taskElevator.entities.Passenger;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Initializer {

    private PropertiesReader reader;

    private List<Passenger> initPassengers;
    private List<Floor> initFloors;

    public Initializer() throws IOException {
        reader = new PropertiesReader("src/main/resources/config.properties");
    }

    public void init() {
        initialFloors();
    }

    public void initialFloors () {
        var passengers = initialPersons();
        var floors = IntStream.range(1, reader.getCountFloors() + 1)
                .boxed()
                .map(numberFloor -> {
                    var currentFloorPassenger = passengers.stream()
                            .filter(passenger -> passenger.getNumberSourceFloor() == numberFloor)
                            .collect(Collectors.toList());
                    return new Floor(currentFloorPassenger ,numberFloor);
                })
                .collect(Collectors.toList());
    }

    public List<Passenger> initialPersons () {
        return IntStream.range(0, reader.getPassengersCount())
                .boxed()
                .map(x -> {
                    var random = new Random();
                    var sourceNumb = random.nextInt(reader.getCountFloors()) + 1;
                    var index = random.nextInt(reader.getCountFloors() - 1);
                    var destinationNumb = IntStream.range(1, reader.getCountFloors() + 1)
                            .filter(numb -> numb != sourceNumb)
                            .toArray()[index];
                    var p = new Passenger(sourceNumb, destinationNumb);
                    return p;
                })
                .collect(Collectors.toList());
    }
}
