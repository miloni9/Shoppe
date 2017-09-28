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
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class most_unique extends AppCompatActivity {
    GridView gridView;
    ArrayList<product_list_class> aarraylist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_unique);
        gridView= (GridView) findViewById(R.id.most_unique_gridview);
        aarraylist=new ArrayList<>();
        AsyncHttpClient cclient=new AsyncHttpClient();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        getSupportActionBar().setTitle("Unique Products");
        cclient.addHeader("Accept", "application/json");
        cclient.addHeader("Content-Type", "application/json");
        cclient.get(most_unique.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/show_unique_product", new JsonHttpResponseHandler() {
            ProgressDialog cprogressdialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray cjasonarray = response.getJSONArray("d");
                    JSONObject cjasonobject;
                    for (int i = 0; i < cjasonarray.length(); i++) {
                        cjasonobject = cjasonarray.getJSONObject(i);
                        aarraylist.add(new product_list_class(cjasonobject.getInt("pk_pro_id"), cjasonobject.getInt("pro_price"), cjasonobject.getString("pro_name"), cjasonobject.getString("pro_img1")));

                    }
                    gridView.setAdapter(new product_list_custom_adapter(most_unique.this, aarraylist));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(most_unique.this, "something went wrong", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                cprogressdialog = ProgressDialog.show(most_unique.this, "Loading", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (cprogressdialog.isShowing()) {
                    cprogressdialog.dismiss();
                }
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                product_list_class mclass = (product_list_class) parent.getItemAtPosition(position);
                Intent it = new Intent(most_unique.this, product_detail.class);
                it.putExtra("id", mclass.getPk_pro_id());

                startActivity(it);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_most_unique, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
