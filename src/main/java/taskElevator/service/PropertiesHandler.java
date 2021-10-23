package taskElevator.service;

import taskElevator.MainPlace;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class PropertiesHandler {

    private static Logger LOGGER = Logger.getLogger("PROPERTIES");

    private String propertiesPath = "src/main/resources/config.properties";
    private String loggerPath = "/logger.properties";
    private static Properties properties;

    public PropertiesHandler() throws IOException {
        properties = readProperties(propertiesPath);
    }

    public void enableFileLogging () {
        try {
            LogManager.getLogManager()
                      .readConfiguration(MainPlace.class.getResourceAsStream(loggerPath));

        } catch (IOException e) {
            LOGGER.log(SEVERE, "logger properties not found", e);
        }
    }

    private Properties readProperties (String path) throws IOException {
        try (var input = new FileInputStream(path)) {
            var properties = new Properties();

            properties.load(input);

            return properties;
        } catch (FileNotFoundException e) {
            LOGGER.log(SEVERE,"Properties file not found: ", e);
            throw new NoSuchFileException(e.getMessage());
        } catch (IOException e) {
            LOGGER.log(SEVERE,"Error to read properties file: ", e);
            throw new IOException(e);
        }
    }

    public static int getCountFloors() {
        return Integer.parseInt(properties.getProperty("floorsNumber"));
    }

    public static int getElevatorCapacity () {
        return Integer.parseInt(properties.getProperty("elevatorCapacity"));
    }

    public static int getPassengersCount () {
        return Integer.parseInt(properties.getProperty("passengersNumber"));
    }

}
