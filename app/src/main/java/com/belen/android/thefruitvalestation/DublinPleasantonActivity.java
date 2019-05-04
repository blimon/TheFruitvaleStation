package com.belen.android.thefruitvalestation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DublinPleasantonActivity extends AppCompatActivity {


    TextView firstTrainTextView;
    TextView secondTrainTextView;
    TextView thirdTrainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dublin_pleasanton);
        firstTrainTextView = findViewById(R.id.first_train);
        secondTrainTextView = findViewById(R.id.second_train);
        thirdTrainTextView = findViewById(R.id.third_train);
    }
    public void checkTimes(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.bart.gov/api/etd.aspx?cmd=etd&orig=ftvl&json=y&key=MW9S-E7SL-26DU-VV8V";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJson = new JSONObject(response);
                            JSONObject root = responseJson.getJSONObject("root");
                            JSONArray station = root.getJSONArray("station");
                            JSONObject fruitvale = station.getJSONObject(0);
                            JSONArray etd = fruitvale.getJSONArray("etd");
                            JSONObject dublinPleasanton = etd.getJSONObject(1);
                            JSONArray estmimate = dublinPleasanton.getJSONArray("estimate");
                            JSONObject firstTrain = estmimate.getJSONObject(0);
                            String firstTrainMinutes = firstTrain.getString("minutes");
                            JSONObject secondTrain = estmimate.getJSONObject(1);
                            String secondTrainMinutes = secondTrain.getString("minutes");
                            JSONObject thirdTrain = estmimate.getJSONObject(2);
                            String thirdTrainMinutes = thirdTrain.getString("minutes");



                            firstTrainTextView.setText(firstTrainMinutes);
                            secondTrainTextView.setText(secondTrainMinutes);
                            thirdTrainTextView.setText(thirdTrainMinutes);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                firstTrainTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

