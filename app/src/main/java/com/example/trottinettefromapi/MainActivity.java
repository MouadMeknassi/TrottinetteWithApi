package com.example.trottinettefromapi;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    //JSONArray sa;

    JSONArray sa = new JSONArray();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String url="https://run.mocky.io/v3/3ee0d39d-9482-4499-b38b-c3c43a02c9c7";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest joRequest=new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() { //Classe anonyme
                    @Override
                    public void onResponse(JSONArray response) {

                        sa = response;

                        ArrayList<String> listTrottinette = new ArrayList<String>();

                        try {
                            for (int i = 0; i < sa.length(); i++) {


                                String name = sa.getJSONObject(i).getString("name");
                                listTrottinette.add(name);

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        Spinner sp = findViewById(R.id.trottinette);
                        ArrayAdapter<String> AA = new ArrayAdapter<>(getApplicationContext(),
                                android.R.layout.simple_spinner_dropdown_item,listTrottinette);
                        sp.setAdapter(AA);

                        sp.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                try {
                                    String maxMileage = sa.getJSONObject(position).getString("maxMileage");
                                    String price = sa.getJSONObject(position).getString("price");
                                    String maxSpeed = sa.getJSONObject(position).getString("maxSpeed");
                                    boolean isSecondCharge=sa.getJSONObject(position).getBoolean("secondBat");
                                    String url = sa.getJSONObject(position).getString("image");
                                    double prcieDouble=Double.parseDouble(price);// La convertion de string à double .
                                    //int pEnteger=Integer.parseInt(maxSpeed);
                                    TextView textmaxMileage = findViewById(R.id.maxMileage);
                                    textmaxMileage.setText(""+String.valueOf(prcieDouble)+"");//pour convertir la valeur double en text pour le voir dans l'interface

                                    TextView textPrice = findViewById(R.id.price);
                                    textPrice.setText(price+" MAD");// La concaténation

                                    TextView textmaxSpeed = findViewById(R.id.maxSpeed);
                                    textmaxSpeed.setText(maxSpeed);

                                    CheckBox isSecondeBat=findViewById(R.id.secodCharge);
                                    isSecondeBat.setChecked(isSecondCharge);
                                    isSecondeBat.setEnabled(false);

                                    ImageView imageView = findViewById(R.id.image);
                                    Picasso.get().load(url).into(imageView);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        }));



                    }
                    },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                });
        requestQueue.add(joRequest);

        //JSONArray data = ;














    }

}