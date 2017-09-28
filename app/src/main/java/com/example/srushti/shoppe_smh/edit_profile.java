package com.example.srushti.shoppe_smh;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class edit_profile extends AppCompatActivity {
    Button edit,cancel;
    String oldp,newp,rep;
    EditText name,city,username,phone,pass;
    String sname,scity,susername,sphone,spass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edit= (Button) findViewById(R.id.edit_profile_edit);
        cancel= (Button) findViewById(R.id.edit_profile_cancel);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        getSupportActionBar().setTitle("Edit Profile");
        name= (EditText) findViewById(R.id.edit_profile_name);
        city= (EditText) findViewById(R.id.edit_profile_city);
        username= (EditText) findViewById(R.id.edit_profile_username);
        phone= (EditText) findViewById(R.id.edit_profile_phone_number);




        final global_class globalClass= (global_class) getApplicationContext();

        JSONObject mJsonObject = new JSONObject();
        StringEntity mStringEntity = null;

        try {
            mJsonObject.put("id", globalClass.getEmail());

            mStringEntity = new StringEntity(mJsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(edit_profile.this, "Json error", Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(edit_profile.this, "string error", Toast.LENGTH_LONG).show();
        }
        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.addHeader("Accept", "application/json");
        mClient.post(edit_profile.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/get_user_detail_by_id", mStringEntity, "application/json", new JsonHttpResponseHandler() {
            ProgressDialog mProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    JSONArray mJsonArray = response.getJSONArray("d");
                    JSONObject mJsonObject = new JSONObject();
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        susername = mJsonObject.getString("pk_email_id") + "";
                      //  spass = mJsonObject.getString("password") + "";
                        sphone = mJsonObject.getString("mobile_no") + "";
                        sname = mJsonObject.getString("name") + "";
                        scity = mJsonObject.getString("city") + "";
                        Toast.makeText(edit_profile.this, scity, Toast.LENGTH_LONG).show();
                        name.setText(sname + "");
                        city.setText(scity + "");
                        username.setText(susername + "");
                        phone.setText(sphone + "");
                      //  pass.setText(spass + "");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(edit_profile.this, "Something went wrong", Toast.LENGTH_LONG);
            }

            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(edit_profile.this, "Loading", "Pleas wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject mJsonObject = new JSONObject();
                StringEntity mStringEntity = null;
                final global_class globalClass= (global_class) getApplicationContext();

                try {
                    mJsonObject.put("id", globalClass.getEmail());
                    mJsonObject.put("num", phone.getText() + "");
                    mJsonObject.put("nm", name.getText() + "");
                    mJsonObject.put("city", city.getText() + "");

                    mStringEntity = new StringEntity(mJsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(edit_profile.this, "Json error", Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(edit_profile.this, "string error", Toast.LENGTH_LONG).show();
                }
                AsyncHttpClient mClient = new AsyncHttpClient();
                mClient.addHeader("Accept", "application/json");
                mClient.post(edit_profile.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/edit_profile", mStringEntity, "application/json", new JsonHttpResponseHandler() {
                    ProgressDialog mProgressDialog;

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {

                            int ans = response.getInt("d");
                            if (ans == 1)
                                Toast.makeText(edit_profile.this, "Successfuly updated", Toast.LENGTH_LONG).show();




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(edit_profile.this, "Something went wrong", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        mProgressDialog = ProgressDialog.show(edit_profile.this, "Registering", "Pleas wait");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                    }

                });


            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
