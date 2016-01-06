
import i2c.MPL3115A2;
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
        String presStream = reader.getPropertyValue(reader.PRESSURE_STREAM);
        String tempStream = reader.getPropertyValue(reader.TEMPERATURE_STREAM);
        String m2xKey = reader.getPropertyValue(reader.M2X_KEY);

        boolean displayPres = reader.getPropertyValue(reader.MPL3115A2_DISPLAY_PRES_IN_CONSOLE).equals("true");
        boolean displayTemp = reader.getPropertyValue(reader.MPL3115A2_DISPLAY_TEMP_IN_CONSOLE).equals("true");
        String measurementIntervalStr = reader.getPropertyValue(reader.MPL3115A2_MEASUREMENT_INTERVAL_SECONDS);
        int measurementInterval = Integer.parseInt(measurementIntervalStr);
        String presFile = reader.getPropertyValue(reader.MPL3115A2_PRES_FILE);
        boolean readPres = reader.getPropertyValue(reader.MPL3115A2_READ_PRES).equals("true");
        boolean readTemp = reader.getPropertyValue(reader.MPL3115A2_READ_TEMP).equals("true");
        boolean sendPresToM2X = reader.getPropertyValue(reader.MPL3115A2_SEND_PRES_TO_M2X).equals("true");
        boolean sendTempToM2X = reader.getPropertyValue(reader.MPL3115A2_SEND_TEMP_TO_M2X).equals("true");
        String tempFile = reader.getPropertyValue(reader.MPL3115A2_TEMP_FILE);
        boolean writePresToFile = reader.getPropertyValue(reader.MPL3115A2_WRITE_PRES_TO_FILE).equals("true");
        boolean writeTempToFile = reader.getPropertyValue(reader.MPL3115A2_WRITE_TEMP_TO_FILE).equals("true");

        
        try {

            MPL3115A2 device = new MPL3115A2();
            device.initialize();
            DecimalFormat df = new DecimalFormat(".##");
            
            while(true){

            if (readTemp) {

                double temp = device.getTemperature();
                String tempStr = df.format(temp);

                if (displayTemp) {

                    System.out.println(tempStr);

                }
                
                if(writeTempToFile){
                
                    FileUtils.writeSrtToFile(tempStr, tempFile, false);
                    
                }

                if (sendTempToM2X) {

                    M2X.sendValueToStream(tempStr, deviceID, tempStream, m2xKey);

                }

            }
            
            //
            
            if (readPres) {

                double pres = device.getPressure();
                String presStr = df.format(pres);

                if (displayPres) {

                    System.out.println(presStr);

                }
                
                if(writePresToFile){
                
                    FileUtils.writeSrtToFile(presStr, presFile, false);
                    
                }

                if (sendPresToM2X) {

                    M2X.sendValueToStream(presStr, deviceID, presStream, m2xKey);

                }

            }

            Thread.sleep(measurementInterval*1000);
            }
        } catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();
        }

    }

}
