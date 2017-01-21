package com.example.lenovo.okhttpdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.okhttpdemo.DataModel.DataModel;
import com.example.lenovo.okhttpdemo.Parser.JSONParser;
import com.example.lenovo.okhttpdemo.Utils.InternetConnection;
import com.example.lenovo.okhttpdemo.adapter.MyArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_CONTACTS = "contacts";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_PROFILE_PIC = "profile_pic";
    public static final String KEY_EMAIL = "email";

    private ListView listView;
    private ArrayList<DataModel> list;
    private MyArrayAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();

        //Binding that List to Adapter

        adapter = new MyArrayAdapter(this, list);

         // Getting List and Setting List Adapter

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Snackbar.make(findViewById(R.id.parentLayout), list.get(position).getName() + " => " + list.get(position).getPhone(), Snackbar.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("email",list.get(position).getEmail());
                intent.putExtra("image",list.get(position).getImage());
                intent.putExtra("gender",list.get(position).getGender());
                intent.putExtra("phone",list.get(position).getPhone());
                startActivity(intent);
            }
        });


        //  Checking Internet Connection

        if (InternetConnection.checkConnection(getApplicationContext())) {
            // Execute Async Task
            new GetDataTask().execute();
        } else {
            Toast.makeText(this, "Internet Connection Not Available..", Toast.LENGTH_LONG).show();
        }
       

    }

    // Create Async Task for get data from API
    class GetDataTask extends AsyncTask<Void ,Void ,Void>
    {
        ProgressDialog dialog;
        String Data="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // dialog loading
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Fetching Details..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jsonObject = JSONParser.getDataFromAPI();

            try {

                if (jsonObject != null) {

                    if(jsonObject.length() > 0) {

                         //Getting Array named "contacts" From MAIN Json Object

                        JSONArray array = jsonObject.getJSONArray(KEY_CONTACTS);

                        int lenArray = array.length();

                        if(lenArray > 0) {
                            for(int jIndex = 0; jIndex < lenArray; jIndex++) {

                               // Creating object and add to the list
                                DataModel model = new DataModel();

                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String name = innerObject.getString(KEY_NAME);
                                String email = innerObject.getString(KEY_EMAIL);
                                String image = innerObject.getString(KEY_PROFILE_PIC);
                                String gender = innerObject.getString("gender");
                                // Get value from "phone"
                                JSONObject phoneObject = innerObject.getJSONObject(KEY_PHONE);
                                String phone = phoneObject.getString(KEY_MOBILE);

                                model.setName(name);
                                model.setEmail(email);
                                model.setPhone(phone);
                                model.setImage(image);
                                model.setGender(gender);

                                //Adding name and phone concatenation in List...

                                list.add(model);
                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
            }
         /*   try
            {
                if(jsonObject != null)
                {
                    JSONArray array = jsonObject.getJSONArray(KEY_CONTACTS);
                    Data = array.toString();
                }
            }
            catch(JSONException je)
            {}*/
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();
            }
        }
    }
}
