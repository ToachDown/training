package taskElevator.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class PropertiesReader {

    private Logger LOGGER = Logger.getLogger("PROPS-READER");

    private Properties properties;

    public PropertiesReader(String path) throws IOException {
        properties = readProperties(path);
    }

    public int getCountFloors() {
        return Integer.parseInt(properties.getProperty("floorsNumber"));
    }

    public int getElevatorCapacity () {
        return Integer.parseInt(properties.getProperty("elevatorCapacity"));
    }

    public int getPassengersCount () {
        return Integer.parseInt(properties.getProperty("passengersNumber"));
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

}
