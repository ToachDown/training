package taskElevator.service;

import java.util.logging.Level;

public class MyLogLevel extends Level {

    public static final Level STARTING_TRANSPORTATION = new MyLogLevel("STARTING_TRANSPORTATION", 850);
    public static final Level COMPLETION_TRANSPORTATION = new MyLogLevel("COMPLETION_TRANSPORTATION", 890);
    public static final Level MOVING_ELEVATOR  = new MyLogLevel("MOVING_ELEVATOR ", 860);
    public static final Level BOARDING_OF_PASSENGER  = new MyLogLevel("BOARDING_OF_PASSENGER ", 870);
    public static final Level DEBOARDING_OF_PASSENGER  = new MyLogLevel("DEBOARDING_OF_PASSENGER ", 880);


    protected MyLogLevel(String name, int value) {
        super(name, value);
    }
}
