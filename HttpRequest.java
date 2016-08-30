package teamproject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpRequest {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static String request(String requestMethod, String url, String params) {
        URL obj;
        if (requestMethod.equalsIgnoreCase("get")) {
            try {
                obj = new URL(url + "?" + params);
            } catch (MalformedURLException e) {
                return "Illegal URL";
            }
        } else if (requestMethod.equalsIgnoreCase("post")) {
            try {
                obj = new URL(url);
            } catch (MalformedURLException e) {
                return "Illegal URL";
            }
        } else return "Unknown request method";

        HttpURLConnection con;
        try {
            con = (HttpURLConnection) obj.openConnection();
        } catch (IOException e) {
            return e + "";
        }

        if (requestMethod.equalsIgnoreCase("get")) {
            try {
                con.setRequestMethod("GET");
            } catch (ProtocolException e) {
                return "Illegal Protocol";
            }
        } else if (requestMethod.equalsIgnoreCase("post")) {
            try {
                con.setRequestMethod("POST");
            } catch (ProtocolException e) {
                return "Illegal Protocol";
            }
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        }

        // Add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        if (requestMethod.equalsIgnoreCase("post")) {
            con.setDoOutput(true);
            try {
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(params);
                wr.flush();
                wr.close();
            } catch (Exception e) {
                return e + "";
            }
        }

        int responseCode;
        try {
            responseCode = con.getResponseCode();
        } catch (Exception e) {
            return e + "";
        }

        // Return Error Code
        if (responseCode != 200) return "Error Code: " + Integer.toString(responseCode);

        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } catch (Exception e) {
            return e + "";
        }
        String inputLine;
        StringBuffer response = new StringBuffer();

        try {
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (Exception e) {
            return e + "";
        }

        try {
            // Close input stream
            in.close();
        } catch (Exception e) {
            return e + "";
        }

        // Return response
        return response.toString();
    }
}