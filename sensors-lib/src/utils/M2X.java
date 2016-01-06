package utils;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class M2X {

    public static boolean sendValueToStream(String value, String deviceID, String stream, String m2xKey) {

        try {
            String urlStr = " http://api-m2x.att.com/v2/devices/" + deviceID + "/streams/" + stream + "/value";
            String jsonContent = "{ \"value\": \"" + value + "\" }";

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-M2X-KEY", m2xKey);

            connection.setRequestProperty("Accept", "application/json");
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(jsonContent);
            osw.flush();
            osw.close();
            int response = connection.getResponseCode();

            if (response != 202) {
                throw new Exception("Could not send data to M2X. \n Error code: "+response +"\n"+urlStr+"\n"+jsonContent);
            }

        } catch (Exception e) {

            System.out.println(e);
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
