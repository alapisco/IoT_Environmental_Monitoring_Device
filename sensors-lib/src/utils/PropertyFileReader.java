package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

    String propertyFile;

    // M2X properties
    public final String DEVICE_ID = "DEVICE_ID";
    public final String HUMIDITY_STREAM = "HUMIDITY_STREAM";
    public final String TEMPERATURE_STREAM = "TEMPERATURE_STREAM";
    public final String PRESSURE_STREAM = "PRESSURE_STREAM";
    public final String LUMINOSITY_STREAM = "LUMINOSITY_STREAM";
    public final String HEAT_INDEX_STREAM = "HEAT_INDEX_STREAM";

    public final String SPL_STREAM = "SPL_STREAM";

    public final String M2X_KEY = "M2X_KEY";

    // HTU21DF properties
    public final String HTU21DF_DISPLAY_TEMP_IN_CONSOLE = "HTU21DF_DISPLAY_TEMP_IN_CONSOLE";
    public final String HTU21DF_DISPLAY_HUM_IN_CONSOLE = "HTU21DF_DISPLAY_HUM_IN_CONSOLE";
    public final String HTU21DF_SEND_TEMP_TO_M2X = "HTU21DF_SEND_TEMP_TO_M2X";
    public final String HTU21DF_SEND_HUM_TO_M2X = "HTU21DF_SEND_HUM_TO_M2X";
    public final String HTU21DF_READ_TEMP = "HTU21DF_READ_TEMP";
    public final String HTU21DF_READ_HUM = "HTU21DF_READ_HUM";
    public final String HTU21DF_MEASUREMENT_INTERVAL_SECONDS = "HTU21DF_MEASUREMENT_INTERVAL_SECONDS";
    public final String HTU21DF_WRITE_TEMP_TO_FILE = "HTU21DF_WRITE_TEMP_TO_FILE";
    public final String HTU21DF_WRITE_HUM_TO_FILE = "HTU21DF_WRITE_HUM_TO_FILE";
    public final String HTU21DF_TEMP_FILE = "HTU21DF_TEMP_FILE";
    public final String HTU21DF_HUM_FILE = "HTU21DF_HUM_FILE";
    public final String HTU21DF_DISPLAY_HEAT_INDEX_IN_CONSOLE = "HTU21DF_DISPLAY_HEAT_INDEX_IN_CONSOLE";
    public final String HTU21DF_SEND_HEAT_INDEX_TO_M2X = "HTU21DF_SEND_HEAT_INDEX_TO_M2X";
    public final String HTU21DF_WRITE_HEAT_INDEX_TO_FILE = "HTU21DF_WRITE_HEAT_INDEX_TO_FILE";
    public final String HTU21DF_HEAT_INDEX_FILE = "HTU21DF_HEAT_INDEX_FILE";

    // MPL3115A2 properties
    public final String MPL3115A2_DISPLAY_TEMP_IN_CONSOLE = "MPL3115A2_DISPLAY_TEMP_IN_CONSOLE";
    public final String MPL3115A2_SEND_TEMP_TO_M2X = "MPL3115A2_SEND_TEMP_TO_M2X";
    public final String MPL3115A2_READ_TEMP = "MPL3115A2_READ_TEMP";
    public final String MPL3115A2_MEASUREMENT_INTERVAL_SECONDS = "MPL3115A2_MEASUREMENT_INTERVAL_SECONDS";
    public final String MPL3115A2_WRITE_TEMP_TO_FILE = "MPL3115A2_WRITE_TEMP_TO_FILE";
    public final String MPL3115A2_TEMP_FILE = "MPL3115A2_TEMP_FILE";
    public final String MPL3115A2_DISPLAY_PRES_IN_CONSOLE = "MPL3115A2_DISPLAY_PRES_IN_CONSOLE";
    public final String MPL3115A2_SEND_PRES_TO_M2X = "MPL3115A2_SEND_PRES_TO_M2X";
    public final String MPL3115A2_READ_PRES = "MPL3115A2_READ_PRES";
    public final String MPL3115A2_WRITE_PRES_TO_FILE = "MPL3115A2_WRITE_PRES_TO_FILE";
    public final String MPL3115A2_PRES_FILE = "MPL3115A2_PRES_FILE";

    // TSL2561 properties
    public final String TSL2561_DISPLAY_LUM_IN_CONSOLE = "TSL2561_DISPLAY_LUM_IN_CONSOLE";
    public final String TSL2561_SEND_LUM_TO_M2X = "TSL2561_SEND_LUM_TO_M2X";
    public final String TSL2561_READ_LUM = "TSL2561_READ_LUM";
    public final String TSL2561_MEASUREMENT_INTERVAL_SECONDS = "TSL2561_MEASUREMENT_INTERVAL_SECONDS";
    public final String TSL2561_WRITE_LUM_TO_FILE = "TSL2561_WRITE_LUM_TO_FILE";
    public final String TSL2561_LUM_FILE = "TSL2561_LUM_FILE";

    // MCP3008 properties
    public final String MCP3008_DISPLAY_SPL_IN_CONSOLE = "MCP3008_DISPLAY_SPL_IN_CONSOLE";
    public final String MCP3008_SEND_SPL_TO_M2X = "MCP3008_SEND_SPL_TO_M2X";
    public final String MCP3008_READ_SPL = "MCP3008_READ_SPL";
    public final String MCP3008_MEASUREMENT_INTERVAL_SECONDS = "MCP3008_MEASUREMENT_INTERVAL_SECONDS";
    public final String MCP3008_WRITE_SPL_TO_FILE = "MCP3008_WRITE_SPL_TO_FILE";
    public final String MCP3008_SPL_FILE = "MCP3008_SPL_FILE";

    public PropertyFileReader(String propertFile) {

        this.propertyFile = propertFile;

    }

    public String getPropertyValue(String propertyName) {

        String propertyValue;

        Properties prop = new Properties();
        InputStream input;

        try {

            input = new FileInputStream(propertyFile);

            prop.load(input);
            propertyValue = prop.getProperty(propertyName);

        } catch (Exception e) {

            return null;
        }

        return propertyValue;

    }
}
