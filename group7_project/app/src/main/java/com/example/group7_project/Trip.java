package com.example.group7_project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.Integer.parseInt;

//For API usage, to be implemented
public class Trip {

    public int findStopId(String stopToFind){
        String key = "9caf98cd-80b7-4594-a7b8-950e463047af";
        String stop = stopToFind+"(goteborgkn)";
        String url = "https://api.resrobot.se/v2/location.name?key=" + key +
                "&input=" + stop
                + "&format=json";
        URL obj;
        HttpsURLConnection connection;
        int returnValue = 0;
        {
            try {
                obj = new URL(url);
                connection = (HttpsURLConnection) obj.openConnection();
                connection.setRequestMethod("GET");
                //connection.setRequestProperty();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append((inputLine));
                }
                in.close();

                JSONObject jsonObject = new JSONObject(response.toString());

                JSONArray array = jsonObject.getJSONArray("StopLocation");

                JSONObject object = array.getJSONObject(0);

                String id_string = object.getString("id");

                returnValue = parseInt(id_string);
                System.out.println(returnValue);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        }
        return returnValue;
    }

    public void findTravel(int fromStop ,int toStop) throws IOException, JSONException {
        String key = "9caf98cd-80b7-4594-a7b8-950e463047af";
        String origin = Integer.toString(fromStop);
        String destination = Integer.toString(toStop);
        String url = "https://api.resrobot.se/v2/trip?key=" + key
                + "&originId=" + origin
                + "&destId=" + destination
                + "&format=json";

        URL obj;
        HttpsURLConnection connection;
        {
            obj = new URL(url);
            connection = (HttpsURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            //connection.setRequestProperty();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append((inputLine));
            }
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());

            JSONArray array = jsonObject.getJSONArray("Trip");

            JSONObject object = array.getJSONObject(0);

            String duration = object.getString("duration");

            object = object.getJSONObject("LegList").getJSONArray("Leg").getJSONObject(0);

            String from = object.getJSONObject("Origin").getString("name");
            String to = object.getJSONObject("Destination").getString("name");



            //System.out.println(from);
            //System.out.println(to);
            //System.out.println(duration);

        }

    }
}
