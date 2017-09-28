package com.example.srushti.shoppe_smh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class chat_detail extends AppCompatActivity {

    ListView lst;
    EditText editText1;
    Button button1;
    global_class globalClass;
    static int pid=0;
    static String recvr="";
    ArrayList<chat_class> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        lst= (ListView) findViewById(R.id.chat_detail_lst);
        editText1= (EditText) findViewById(R.id.chat_detail_txt_msg);
        button1= (Button) findViewById(R.id.chat_detail_btn_msg_send);
        arrayList=new ArrayList<>();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        getSupportActionBar().setTitle("Chat");
        final Intent mIntent=getIntent();
        pid=mIntent.getIntExtra("pid", 0);
        recvr=mIntent.getStringExtra("pauthor");
        globalClass= (global_class) getApplicationContext();

        JSONObject mJsonObject=new JSONObject();
        StringEntity mStringEntity=null;
        try {
            mJsonObject.put("sender",globalClass.getEmail());
            mJsonObject.put("recvr",recvr);
            mJsonObject.put("pid",pid);
            mStringEntity=new StringEntity(mJsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        display_msg();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject mJsonObject=new JSONObject();
                StringEntity mStringEntity=null;
                try {
                    mJsonObject.put("sender",globalClass.getEmail());
                    mJsonObject.put("recvr",recvr);
                    mJsonObject.put("pid",pid);
                    mJsonObject.put("msg",editText1.getText().toString());
                    mStringEntity=new StringEntity(mJsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                AsyncHttpClient mClient=new AsyncHttpClient();
                mClient.addHeader("Accept","application/json");
                mClient.post(chat_detail.this,"http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/send_message",mStringEntity,"application/json",new JsonHttpResponseHandler()
                {
                    ProgressDialog mProgressDialog;

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            int ans = response.getInt("d");
                            if (ans == 1)
                            {
                                //editText1.setText("");

                                Toast.makeText(chat_detail.this,"Message Sent",Toast.LENGTH_LONG).show();
                                arrayList.clear();
                                display_msg();
                                editText1.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
            }
        });
    }

    private void display_msg()
    {
        final global_class globalClass= (global_class) getApplicationContext();
        JSONObject mJsonObject=new JSONObject();
        StringEntity mStringEntity=null;
        try {
            mJsonObject.put("sender",globalClass.getEmail());
            mJsonObject.put("recvr",recvr);
            mJsonObject.put("pid",pid);
            mStringEntity=new StringEntity(mJsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient mClient=new AsyncHttpClient();
        mClient.addHeader("Accept","application/json");
        mClient.post(chat_detail.this,"http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/show_message",mStringEntity,"application/json",new JsonHttpResponseHandler()
        {
            ProgressDialog mProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray mJsonArray = response.getJSONArray("d");
                    JSONObject mJsonObject = new JSONObject();
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        arrayList.add(new chat_class(mJsonObject.getString("sender"),mJsonObject.getString("reciever"),mJsonObject.getString("message")));
                    }
                    lst.setAdapter(new chat_custom_adapter(arrayList,chat_detail.this));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_chat) {
            final AlertDialog.Builder mBuilder=new AlertDialog.Builder(chat_detail.this);
            mBuilder.setTitle("Delete");
            mBuilder.setMessage("Do you want to delete all messages?");
            mBuilder.setCancelable(false);

            mBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    JSONObject mjsonObject = new JSONObject();
                    StringEntity mStringEntity = null;
                    try {

                        mjsonObject.put("sender", globalClass.getEmail());
                        mjsonObject.put("recvr", recvr);
                        mjsonObject.put("pid", pid);
                        mStringEntity = new StringEntity(mjsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    final AsyncHttpClient mclient = new AsyncHttpClient();
                    mclient.addHeader("Accept", "application/json");
                    mclient.post(chat_detail.this, "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/clear_chat", mStringEntity, "application/json", new JsonHttpResponseHandler() {

                        ProgressDialog nProgressDialog;

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            try {
                                int ans = response.getInt("d");
                                if (ans == 1) {
                                    Toast.makeText(chat_detail.this, "Chat deleted", Toast.LENGTH_LONG).show();
                                    arrayList.clear();
                                    lst.setAdapter(new chat_custom_adapter(arrayList,chat_detail.this));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Toast.makeText(chat_detail.this, "Something went wrong.", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            nProgressDialog = ProgressDialog.show(chat_detail.this, "Loading", "Please Wait...");
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            if (nProgressDialog.isShowing())
                                nProgressDialog.dismiss();
                        }
                    });

                }
            });
            mBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog mAlertDialog=mBuilder.create();
            mAlertDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
