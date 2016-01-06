
import i2c.TSL2561;
import java.text.DecimalFormat;
import utils.FileUtils;
import utils.M2X;
import utils.PropertyFileReader;

public class Main {

    public static void main(String[] args) {

        // Read property file
        PropertyFileReader reader = new PropertyFileReader("sensors.properties");
        // m2x settings
        String deviceID = reader.getPropertyValue(reader.DEVICE_ID);
        String luminosityStream = reader.getPropertyValue(reader.LUMINOSITY_STREAM);
        String m2xKey = reader.getPropertyValue(reader.M2X_KEY);

        boolean displayLum = reader.getPropertyValue(reader.TSL2561_DISPLAY_LUM_IN_CONSOLE).equals("true");
        String measurementInterval = reader.getPropertyValue(reader.TSL2561_MEASUREMENT_INTERVAL_SECONDS);
        boolean readLum = reader.getPropertyValue(reader.TSL2561_READ_LUM).equals("true");
        boolean sendLumToM2X = reader.getPropertyValue(reader.TSL2561_SEND_LUM_TO_M2X).equals("true");
        boolean writeLumToTfile = reader.getPropertyValue(reader.TSL2561_WRITE_LUM_TO_FILE).equals("true");
        String lumFile = reader.getPropertyValue(reader.TSL2561_LUM_FILE);

        int measurementIntervalSec = Integer.parseInt(measurementInterval);

        try {
            while (true) {
                TSL2561 device = new TSL2561();
                device.initialize();
                DecimalFormat df = new DecimalFormat(".##");

                if (readLum) {

                    double lux = device.getLux();
                    String luxStr = df.format(lux);

                    if (displayLum) {
                        System.out.println(lux);
                    }
                    if (writeLumToTfile) {

                        FileUtils.writeSrtToFile(luxStr, lumFile, false);

                    }
                    if(sendLumToM2X){
                        M2X.sendValueToStream(luxStr, deviceID, luminosityStream, m2xKey);
                    
                    }

                }

                Thread.sleep(measurementIntervalSec * 1000);
            }
        } catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();

        }

    }

}
