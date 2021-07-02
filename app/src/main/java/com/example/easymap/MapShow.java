package com.example.easymap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.easymap.views.CustomView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapShow extends AppCompatActivity {

    String reqMap;
    String reqObject;
    String reqItem;

    private ArrayList<Map> maps;
    private ArrayList<MapObject>[] objects;
    private ArrayList<MapItem>[] items;
    private CustomView customView;
    private EditText searchInput;
    private Button searchBtn;
    private Spinner floorList;
    private Button calibrateButton;


    RequestQueue requestQueue;

    //____________________________________________________________________________
    //on create
    //____________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_show);
        String map_id = getIntent().getStringExtra("MAP_ID"); // Will delete later
        String map_name = getIntent().getStringExtra("MAP_NAME");

        searchInput = (EditText) findViewById(R.id.searchInput);
        searchBtn = (Button) findViewById(R.id.objectSearch);
        floorList = (Spinner) findViewById(R.id.floorList);
        calibrateButton = (Button) findViewById(R.id.calibrateMode);

        customView = (CustomView) findViewById(R.id.customView);
        maps = new ArrayList<>();

        this.requestQueue = Volley.newRequestQueue(this);
        extractMap(map_name);
    }

    //____________________________________________________________________________
    //map name extract
    //____________________________________________________________________________
    private void extractMap(String map_name) {
        this.reqMap = "https://ap-southeast-1.aws.webhooks.mongodb-realm.com/" +
                "api/client/v2.0/app/easy_map-aakbp/service/easy_map_map_api/" +
                "incoming_webhook/map-get?" +
                "secret=IAmDeveloper" +
                "&" +
                "command=show_same_map" +
                "&" +
                "map_name=" +
                map_name;

        JsonArrayRequest mapRequest = new JsonArrayRequest(
                Request.Method.GET,
                reqMap,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String patternFloor = "(.*):\"(\\d+)(.*)"; //(pattern for $numberdouble)
                        String patternMap = "(.*):\"(.*)\"(.*)"; //(pattern for $oid)

                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject mapObject = response.getJSONObject(i);
                                String text = mapObject.get("floor").toString();
                                Pattern p = Pattern.compile(patternFloor);
                                Matcher m = p.matcher(text);

                                Map map = new Map();
                                map.setName(mapObject.getString("name").toString());
                                map.setDescription(mapObject.getString("description").toString());

                                if(m.find())
                                {
                                    map.setFloor(Double.parseDouble(m.group(2)));
                                }

                                //_id
                                text = mapObject.get("_id").toString();
                                p = Pattern.compile(patternMap);
                                m = p.matcher(text);

                                if(m.find())
                                {
                                    map.set_id(m.group(2));
                                }

                                //creator_id
                                text = mapObject.get("creator_id").toString();
                                p = Pattern.compile(patternMap);
                                m = p.matcher(text);

                                if(m.find())
                                {
                                    map.setCreator_id(m.group(2));
                                }

                                maps.add(map);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        customView.setUpCanvas(maps);

                       ArrayList<String> floorArray = new ArrayList<>();

                        objects = (ArrayList<MapObject>[]) new ArrayList[maps.size()];
                        items = (ArrayList<MapItem>[]) new ArrayList[maps.size()];

                        for(int i = 0; i < maps.size(); i++) {
                            floorArray.add(String.valueOf(maps.get(i).getFloor().intValue()));
                            objects[maps.get(i).getFloor().intValue() - 1] = new ArrayList<>();
                            extractObject(maps.get(i).get_id(), maps.get(i).getFloor().intValue());
                            items[maps.get(i).getFloor().intValue() - 1] = new ArrayList<>();
                            extractItem(maps.get(i).get_id(), maps.get(i).getFloor().intValue());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_dropdown_item_1line,
                                floorArray);
                        floorList.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );

        //Add req to queue
        requestQueue.add(mapRequest);
    }

    //____________________________________________________________________________
    //Object extract
    //____________________________________________________________________________
    private void extractObject(String map_id, int floor) {
        this.reqObject = "https://ap-southeast-1.aws.webhooks.mongodb-realm.com/" +
                "api/client/v2.0/app/easy_map-aakbp/service/easy_map_mapobject_api/" +
                "incoming_webhook/mapobject-get?" +
                "secret=IAmDeveloper" +
                "&" +
                "command=show_all_in_map" +
                "&" +
                "in_map=" +
                map_id;

        Log.d("Obj ID", map_id);

        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                reqObject,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String patternDouble = "(.*):\"(\\d+)(.*)"; //(pattern for $numberdouble)
                        String patternId = "(.*):\"(.*)\"(.*)"; //(pattern for $oid)

                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                MapObject mapObject = new MapObject();

                                JSONObject objectObject = response.getJSONObject(i);

                                //location_x
                                String text = objectObject.get("location_x").toString();
                                Pattern p = Pattern.compile(patternDouble);
                                Matcher m = p.matcher(text);

                                if(m.find())
                                {
                                    mapObject.setLocation_x(Double.parseDouble(m.group(2)));
                                }

                                //Location_y
                                text = objectObject.get("location_y").toString();
                                p = Pattern.compile(patternDouble);
                                m = p.matcher(text);

                                if(m.find())
                                {
                                    mapObject.setLocation_y(Double.parseDouble(m.group(2)));
                                }

                                //Width_x
                                text = objectObject.get("width_x").toString();
                                p = Pattern.compile(patternDouble);
                                m = p.matcher(text);

                                if(m.find())
                                {
                                    mapObject.setWidth_x(Double.parseDouble(m.group(2)));
                                }

                                //Width_y
                                text = objectObject.get("width_y").toString();
                                p = Pattern.compile(patternDouble);
                                m = p.matcher(text);

                                if(m.find())
                                {
                                    mapObject.setWidth_y(Double.parseDouble(m.group(2)));
                                }

                                //_id
                                text = objectObject.get("_id").toString();
                                p = Pattern.compile(patternId);
                                m = p.matcher(text);

                                if(m.find())
                                {
                                    mapObject.set_id(m.group(2));
                                }

                                //map_id
                                text = objectObject.get("map_id").toString();
                                p = Pattern.compile(patternId);
                                m = p.matcher(text);

                                if(m.find())
                                {
                                    mapObject.setMap_id(m.group(2));
                                }

                                mapObject.setName(objectObject.getString("name").toString());
                                mapObject.setDetail(objectObject.getString("detail").toString());
                                mapObject.setHighligt(false);

                                objects[floor-1].add(mapObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //Generate as bitmap
                        Log.d("res obj", "succeed:" + String.valueOf(objects[floor-1].size()));

                        customView.setUpMapObject(objects[floor-1], floor-1);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response Object", error.toString());
                    }
                }
        );

        //Add req to queue
        requestQueue.add(objectRequest);
    }


    //____________________________________________________________________________
    //Item extract
    //____________________________________________________________________________
    private void extractItem(String map_id, int floor) {
        this.reqItem = "https://ap-southeast-1.aws.webhooks.mongodb-realm.com/" +
                "api/client/v2.0/app/easy_map-aakbp/service/easy_map_mapitem_api/" +
                "incoming_webhook/mapitem-get?" +
                "secret=IAmDeveloper" +
                "&" +
                "command=show_all_in_map" +
                "&" +
                "map_id=" +
                map_id;

        Log.d("Item ID", map_id);

        JsonArrayRequest itemRequest = new JsonArrayRequest(
                Request.Method.GET,
                reqItem,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String patternId = "(.*):\"(.*)\"(.*)"; //(pattern for $oid)

                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                MapItem mapItem = new MapItem();

                                JSONObject itemObject = response.getJSONObject(i);

                                //_id
                                String text = itemObject.get("_id").toString();
                                Pattern p = Pattern.compile(patternId);
                                Matcher m = p.matcher(text);

                                if(m.find())
                                {
                                    mapItem.set_id(m.group(2));
                                }

                                //object_id
                                text = itemObject.get("object_id").toString();
                                p = Pattern.compile(patternId);
                                m = p.matcher(text);

                                if(m.find())
                                {
                                    mapItem.setObject_id(m.group(2));
                                }

                                mapItem.setName(itemObject.getString("name").toString());
                                mapItem.setDescription(itemObject.getString("description").toString());
                                JSONArray categoryJ = itemObject.getJSONArray("category");
                                ArrayList<String> category = new ArrayList<>();

                                for(int j = 0; j < categoryJ.length(); j++){
                                    category.add(categoryJ.getString(j));
                                }

                                mapItem.setCategory(category);

                                items[floor-1].add(mapItem);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("res item", "succeed"+ String.valueOf(items[floor-1].size()));
                        customView.setUpMapItem(items[floor-1], floor-1);
                        objectSearchSetUp();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response Item", error.toString());
                    }
                }
        );

        //Add req to queue
        requestQueue.add(itemRequest);
    }


    private void objectSearchSetUp(){
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = searchInput.getText().toString();
                customView.searchContainString(searchString);
            }
        });
    }
}