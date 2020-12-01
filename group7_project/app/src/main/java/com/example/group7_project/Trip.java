package com.example.group7_project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.Integer.parseInt;

//For API usage, to be implemented
public class Trip {

    private ArrayList<Travel> travels;

    public Trip(String from, String to) {
        travels = new ArrayList<>();
        setTravels(from, to);
    }


    public ArrayList<Travel> getTravels() {
        return travels;
    }

    public void setTravels(String from, String to) {
        if (travels.size() != 0) travels.clear();
        try {
            findTravel(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int findStopId(String stopToFind) {
        String key = "9caf98cd-80b7-4594-a7b8-950e463047af";
        String stop = stopToFind + "(goteborgkn)";
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

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        }
        return returnValue;
    }

    public void findTravel(String fromStop, String toStop) throws IOException, JSONException {
        int fromStopId = findStopId(fromStop);
        int toStopId = findStopId(toStop);
        String key = "9caf98cd-80b7-4594-a7b8-950e463047af";
        String origin = Integer.toString(fromStopId);
        String destination = Integer.toString(toStopId);
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

            for (int i = 0; i < array.length(); i++) {
                int id = 0;
                boolean change;
                int line_1 = 0;
                int line_2 = 0;
                int duration_1 = 0;
                int duration_2 = 0;
                int duration_wait = 0;
                int score = 0;
                int departure;
                int arrival = 0;
                String from = fromStop;
                String to = toStop;
                boolean best = false;

                JSONObject object = array.getJSONObject(i);
                JSONArray legs = object.getJSONObject("LegList").getJSONArray("Leg");
                JSONObject leg = legs.getJSONObject(0);
                change = legs.length() > 1;

                boolean walk = leg.getJSONObject("Destination").getString("type").equals("WALK");

                from = leg.getJSONObject("Origin").getString("name");
                String str =(leg.getString("time"));
                departure = timeToInt(str);


                switch (legs.length()) {
                    case 0:
                        break;
                    case 1:
                        to = leg.getJSONObject("Destination").getString("name");
                        arrival = timeToInt(leg.getJSONObject("Destination").getString("time"));
                        line_1 = Integer.parseInt(leg.getJSONObject("Product").getString("num"));
                        duration_1 = arrival-departure;
                        duration_2 = 0;
                        duration_wait = 0;
                        break;
                    case 2:
                        int leg_1_start = timeToInt(leg.getJSONObject("Origin").getString("time"));
                        int leg_1_end = timeToInt(leg.getJSONObject("Destination").getString("time"));
                        duration_1 = leg_1_end - leg_1_start;
                        line_1 = Integer.parseInt(leg.getJSONObject("Product").getString("num"));

                        leg = legs.getJSONObject(1);
                        walk = leg.getJSONObject("Destination").getString("type").equals("WALK");
                        int leg_2_start = timeToInt(leg.getJSONObject("Origin").getString("time"));
                        int leg_2_end = timeToInt(leg.getJSONObject("Destination").getString("time"));
                        duration_2 = leg_2_end - leg_2_start;
                        duration_wait = leg_2_start - leg_1_end;
                        if(!walk){
                            line_2 = Integer.parseInt(leg.getJSONObject("Product").getString("num"));
                        }else{
                            line_2 = 0;
                        }

                        to = leg.getJSONObject("Destination").getString("name");
                        break;
                    default:
                        break;
                }
            
                Travel travel = new Travel(id,change,line_1,line_2,
                        duration_1,duration_2,duration_wait,
                        score,departure,arrival,from,to);


            }

        }

    }
    public int timeToInt(String time){
        StringBuilder sb = new StringBuilder(time);
        return Integer.parseInt(sb.substring(0,2))*60 + Integer.parseInt(sb.substring(3,5));
    }

}

