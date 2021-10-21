package taskElevator;

import taskElevator.service.Initializer;

import java.io.IOException;

public class MainPlace {

    public static void main(String[] args) throws IOException {
        Initializer initializer = new Initializer();
        initializer.init();
    }
}
