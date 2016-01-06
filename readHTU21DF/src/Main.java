
import i2c.HTU21DF;
import java.text.DecimalFormat;
import utils.FileUtils;
import utils.M2X;
import utils.PropertyFileReader;

public class Main {

    public static void main(String[] args) {

        // Read property file
        PropertyFileReader reader = new PropertyFileReader("sensors.properties");
        int measurementInterval = Integer.parseInt(reader.getPropertyValue(reader.HTU21DF_MEASUREMENT_INTERVAL_SECONDS));
        boolean sendHumToM2X = reader.getPropertyValue(reader.HTU21DF_SEND_HUM_TO_M2X).equalsIgnoreCase("true");
        boolean sendTempToM2X = reader.getPropertyValue(reader.HTU21DF_SEND_TEMP_TO_M2X).equalsIgnoreCase("true");
        boolean displayTempInConsole = reader.getPropertyValue(reader.HTU21DF_DISPLAY_TEMP_IN_CONSOLE).equalsIgnoreCase("true");
        boolean displayHumInConsole = reader.getPropertyValue(reader.HTU21DF_DISPLAY_HUM_IN_CONSOLE).equalsIgnoreCase("true");
        boolean readTemp = reader.getPropertyValue(reader.HTU21DF_READ_TEMP).equalsIgnoreCase("true");
        boolean readHum = reader.getPropertyValue(reader.HTU21DF_READ_HUM).equalsIgnoreCase("true");
        boolean writeTempToFile = reader.getPropertyValue(reader.HTU21DF_WRITE_TEMP_TO_FILE).equalsIgnoreCase("true");
        boolean writeHumToFile = reader.getPropertyValue(reader.HTU21DF_WRITE_HUM_TO_FILE).equalsIgnoreCase("true");
        String tempFile = reader.getPropertyValue(reader.HTU21DF_TEMP_FILE);
        String humFile = reader.getPropertyValue(reader.HTU21DF_HUM_FILE);
        String htFile = reader.getPropertyValue(reader.HTU21DF_HEAT_INDEX_FILE);

        boolean displayHTInConsole = reader.getPropertyValue(reader.HTU21DF_DISPLAY_HEAT_INDEX_IN_CONSOLE).equalsIgnoreCase("true");

        // m2x settings
        String deviceID = reader.getPropertyValue(reader.DEVICE_ID);
        String humStream = reader.getPropertyValue(reader.HUMIDITY_STREAM);
        String tempStream = reader.getPropertyValue(reader.TEMPERATURE_STREAM);
        String htStream = reader.getPropertyValue(reader.HEAT_INDEX_STREAM);
        String m2xKey = reader.getPropertyValue(reader.M2X_KEY);

        try {
            HTU21DF device = new HTU21DF();
            device.initialize();
            DecimalFormat df = new DecimalFormat(".##");
            double temperature = 0, humidity = 0;
            while (true) {

                if (readTemp) {

                    temperature = device.getTemperature();
                    String temperatureStr = df.format(temperature);
                    // temperatureStr += Double.toString(temperature);

                    if (displayTempInConsole) {

                        System.out.println(temperature);

                    }

                    if (writeTempToFile) {

                        FileUtils.writeSrtToFile(temperatureStr, tempFile, false);

                    }

                    if (sendTempToM2X) {

                        M2X.sendValueToStream(temperatureStr, deviceID, tempStream, m2xKey);

                    }

                }

                if (readHum) {

                    humidity = device.getHumidity();
                    String humidityStr = df.format(humidity);
                    //humidityStr += Double.toString(humidity);

                    if (displayHumInConsole) {

                        System.out.println(humidity);

                    }

                    if (writeHumToFile) {

                        FileUtils.writeSrtToFile(humidityStr, humFile, false);

                    }

                    if (sendTempToM2X) {

                        M2X.sendValueToStream(humidityStr, deviceID, humStream, m2xKey);

                    }

                }

                //A heat index value cannot be calculated for temperatures less than 26.7 degrees Celsius
                if (readHum && readTemp && temperature>=26.7) {

                    double heatIndex = Main.calculateHeatIndex(temperature, humidity);
                    String heatIndexStr = df.format(heatIndex);

                    if (displayHTInConsole) {

                        System.out.println(heatIndexStr);

                    }

                    if (writeHumToFile) {

                        FileUtils.writeSrtToFile(heatIndexStr, htFile, false);

                    }

                    if (sendTempToM2X) {

                        M2X.sendValueToStream(heatIndexStr, deviceID, htStream, m2xKey);

                    }

                }

                Thread.sleep(measurementInterval * 1000);

            }

        } catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();
        }

    }

    //according to http://www.srh.noaa.gov/images/epz/wxcalc/heatIndex.pdf
    private static double calculateHeatIndex(double temperature, double rh) {

        double F = 1.8 * temperature + 32;
        double ht;

        ht = -42.379 + 2.04901523 * F + 10.14333127 * rh
                - 0.22475541 * F * rh - 6.83783 * Math.pow(10, -3) * F * F
                - 5.481717 * Math.pow(10, -2) * rh * rh
                + 1.22874 * Math.pow(10, -3) * F * F * rh
                + 8.5282 * Math.pow(10, -4) * F * rh * rh
                - 1.99 * Math.pow(10, -6) * F * F * rh * rh;

        return ht;
    }

}
