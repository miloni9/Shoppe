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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class login extends AppCompatActivity {
    EditText userid,password;
    Button submit;
    TextView frgt;
    ArrayList<login_class> mArrayList;
    String id,pass,mblnum,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        getSupportActionBar().setTitle("Login");
        userid= (EditText) findViewById(R.id.login_userid);
        password= (EditText) findViewById(R.id.login_password);
        submit= (Button) findViewById(R.id.login_submit);
frgt= (TextView) findViewById(R.id.login_forgotpassword);
        mArrayList=new ArrayList<>();

        frgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(login.this,forget_password.class);
                startActivity(mIntent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            id=userid.getText()+"";
            pass=password.getText()+"";
        JSONObject mJsonObject=new JSONObject();
        StringEntity mStringEntity=null;

        try {
            mJsonObject.put("id",id);
            mJsonObject.put("pass",pass);
            mStringEntity=new StringEntity(mJsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(login.this, "Json error", Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(login.this,"string error",Toast.LENGTH_LONG).show();
        }
        AsyncHttpClient mClient=new AsyncHttpClient();
        mClient.addHeader("Accept", "application/json");
        mClient.post(login.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/login", mStringEntity, "application/json", new JsonHttpResponseHandler() {
            ProgressDialog mProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray mJsonArray = response.getJSONArray("d");
                    JSONObject mJsonObject = new JSONObject();
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        mblnum = mJsonObject.getString("mobile_no") + "";
                        name = mJsonObject.getString("name") + "";

                    }

                    global_class globalClass = (global_class) getApplicationContext();
                    globalClass.setEmail(userid.getText() + "");

                    if (mblnum != null) {
                        Intent mIntent = new Intent(login.this, home.class);
                        startActivity(mIntent);
                    } else {
                        Toast.makeText(login.this, "Wrong id/password", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(login.this, "Something went wrong", Toast.LENGTH_LONG);
            }

            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(login.this, "Loading", "Please wait");
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
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id==R.id.action_skip){
            Intent mIntent=new Intent(login.this,home.class);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
