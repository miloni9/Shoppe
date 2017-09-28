package com.example.srushti.shoppe_smh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class search_listview extends AppCompatActivity {
    ArrayList<product_list_class> mArrayList;
    GridView alistview;
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_listview);
        alistview= (GridView) findViewById(R.id.product_search_listview);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        getSupportActionBar().setTitle("Search ");
        mArrayList=new ArrayList<>();
        Intent it=getIntent();
        s=it.getStringExtra("query");
        JSONObject mJsonObject=new JSONObject();
        StringEntity mStringEntity=null;
        try {
            mJsonObject.put("name",s);
            mStringEntity=new StringEntity(mJsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(search_listview.this, "Json error", Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(search_listview.this,"string error",Toast.LENGTH_LONG).show();
        }
        AsyncHttpClient mClient=new AsyncHttpClient();
        mClient.addHeader("Accept","application/json");
        mClient.post(search_listview.this, "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/search_product_by_name", mStringEntity, "application/json", new JsonHttpResponseHandler() {
            ProgressDialog mProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray mJsonArray = response.getJSONArray("d");
                    JSONObject mJsonObject = new JSONObject();
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        mArrayList.add(new product_list_class(mJsonObject.getInt("pk_pro_id"), mJsonObject.getInt("pro_price"), mJsonObject.getString("pro_name"), mJsonObject.getString("pro_img1")));
                    }
                    alistview.setAdapter(new product_list_custom_adapter(search_listview.this, mArrayList));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(search_listview.this, "Something went wrong", Toast.LENGTH_LONG);

            }

            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(search_listview.this, "Searching", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
       alistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               product_list_class mclass=(product_list_class)parent.getItemAtPosition(position);
               Intent it=new Intent(search_listview.this,product_detail.class);
               it.putExtra("id",mclass.getPk_pro_id());

               startActivity(it);
           }
       });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_listview, menu);

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
