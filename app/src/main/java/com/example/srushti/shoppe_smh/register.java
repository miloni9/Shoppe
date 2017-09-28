package com.example.srushti.shoppe_smh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;








public class register extends AppCompatActivity {

    EditText name,username,mob,password,password2;
    Button submit1,cancel;
    Spinner city;

    String names,usernames,mobiles,passwords,password2s,citys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String str[]=new String[10];
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        getSupportActionBar().setTitle("Register");
        name= (EditText) findViewById(R.id.register_name);
        username= (EditText) findViewById(R.id.register_username);
        mob= (EditText) findViewById(R.id.register_phone_number);
        password= (EditText) findViewById(R.id.register_password);
        password2= (EditText) findViewById(R.id.register_reenter_password);
        submit1= (Button) findViewById(R.id.register_submit);
        cancel= (Button) findViewById(R.id.register_cancel);
        city= (Spinner) findViewById(R.id.register_city);
        str[0]="Jaipur";
        str[1]="Ahmedabad";
        str[2]="Surat";
        str[3]="Mumbai";
        str[4]="Delhi";
        str[5]="Kolkata";
        str[6]="Banglore";
        str[7]="pune";
        str[8]="vadodara";
        str[9]="Hyderabad";

    passwords=password.getText()+"";
        password2s=password2.getText()+"";


       ArrayAdapter<String> mArrayAdapter=new ArrayAdapter<String>(register.this,R.layout.support_simple_spinner_dropdown_item,str);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(mArrayAdapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                citys = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (passwords.equals(password2s)) {
                    usernames = username.getText() + "";

                    names = name.getText() + "";
                    mobiles = mob.getText() + "";

                    JSONObject mJsonObject = new JSONObject();
                    StringEntity mStringEntity = null;

                    try {
                        mJsonObject.put("id", usernames);
                        mJsonObject.put("pass", passwords);
                        mJsonObject.put("num", mobiles);
                        mJsonObject.put("nm", names);
                        mJsonObject.put("city", citys);
                        mStringEntity = new StringEntity(mJsonObject.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(register.this, "Json error", Toast.LENGTH_LONG).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Toast.makeText(register.this, "string error", Toast.LENGTH_LONG).show();
                    }
                    AsyncHttpClient mClient = new AsyncHttpClient();
                    mClient.addHeader("Accept", "application/json");
                    mClient.post(register.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/register", mStringEntity, "application/json", new JsonHttpResponseHandler() {
                        ProgressDialog mProgressDialog;

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {

                                int ans = response.getInt("d");
                                if (ans == 1) {
                                    Toast.makeText(register.this, "Successfuly registered", Toast.LENGTH_LONG).show();
                                    Intent mIntent = new Intent(register.this, home.class);
                                    startActivity(mIntent);
                                }
                                else {
                                    Toast.makeText(register.this, "not registered", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Toast.makeText(register.this, "Something went wrong", Toast.LENGTH_LONG);
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            mProgressDialog = ProgressDialog.show(register.this, "Loading", "Please wait");
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            if (mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                        }

                    });
                } else {
                    Toast.makeText(register.this, "Password not matching", Toast.LENGTH_LONG).show();

                }
            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(register.this,home.class);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
