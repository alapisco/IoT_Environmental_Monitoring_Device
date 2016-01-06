
import analog.MCP3008;
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
        String splStream = reader.getPropertyValue(reader.SPL_STREAM);
        String m2xKey = reader.getPropertyValue(reader.M2X_KEY);

        boolean displaySpl = reader.getPropertyValue(reader.MCP3008_DISPLAY_SPL_IN_CONSOLE).equals("true");
        String measurementInterval = reader.getPropertyValue(reader.MCP3008_MEASUREMENT_INTERVAL_SECONDS);
        boolean readSpl = reader.getPropertyValue(reader.MCP3008_READ_SPL).equals("true");
        boolean sendSplToM2X = reader.getPropertyValue(reader.MCP3008_SEND_SPL_TO_M2X).equals("true");
        boolean writeSplToFile = reader.getPropertyValue(reader.MCP3008_WRITE_SPL_TO_FILE).equals("true");
        String splFile = reader.getPropertyValue(reader.MCP3008_SPL_FILE);

        int measurementIntevarlMs = Integer.parseInt(measurementInterval);

        try {
            MCP3008 device = new MCP3008();
            device.initialize();

            while (true) {
                double spl = device.readSPL(0);
                DecimalFormat df = new DecimalFormat(".##");

                if (readSpl) {

                    String splStr = df.format(spl);

                    if (displaySpl) {

                        System.out.println(splStr);

                    }

                    if (writeSplToFile) {

                        FileUtils.writeSrtToFile(splStr, splFile, false);

                    }

                    if (sendSplToM2X) {

                        M2X.sendValueToStream(splStr, deviceID, splStream, m2xKey);

                    }

                }

                Thread.sleep(measurementIntevarlMs * 1000);
            }

        } catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();
        }
    }

}
