package com.example.easymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MapList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Map> maps;
    String reqURL = "https://ap-southeast-1.aws.webhooks.mongodb-realm.com/" +
            "api/client/v2.0/app/" +
            "easy_map-aakbp/service/" +
            "easy_map_map_api/incoming_webhook/map-get?" +
            "secret=" + "IAmDeveloper" +
            "&command=" + "show_all";
    AdapterMap adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);

        recyclerView = findViewById(R.id.mapList);
        maps = new ArrayList<>();

        extractMap();
    }

    private void extractMap() {
        // Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Object request
        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                reqURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String pattern = "(.*):\"(\\d+)(.*)"; //(pattern for $numberdouble)
                        String patternMap = "(.*):\"(.*)\"(.*)"; //(pattern for $oid)

                        //Log.d("Rest Response", response.toString());
                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject mapObject = response.getJSONObject(i);
                                String text = mapObject.get("floor").toString();
                                Pattern p = Pattern.compile(pattern);
                                Matcher m = p.matcher(text);

                                Map map = new Map();
                                map.setName(mapObject.getString("name").toString());

                                map.setDescription(mapObject.getString("description").toString());

                                if(m.find())
                                {
                                    map.setFloor(Double.parseDouble(m.group(2)));
                                }

                                if (map.getFloor() == 1)
                                {
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

                                    // add map
                                    maps.add(map);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new AdapterMap(maps, getApplicationContext());
                        try {
                            recyclerView.setAdapter(adapter);
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        //Log.d("Test", "Asdf");

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
        requestQueue.add(objectRequest);
    }
}
