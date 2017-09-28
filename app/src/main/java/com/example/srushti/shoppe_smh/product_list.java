package com.example.srushti.shoppe_smh;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class product_list extends AppCompatActivity  {
    GridView gridView;
    String cate;

    ArrayList<product_list_class> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        gridView = (GridView) findViewById(R.id.product_list_grid);
        mArrayList = new ArrayList<product_list_class>();


        Intent mIntent = getIntent();
        cate = mIntent.getStringExtra("cat");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        getSupportActionBar().setTitle(cate.toUpperCase());
        JSONObject mJsonObject = new JSONObject();
        StringEntity mStringEntity = null;

        try {
            mJsonObject.put("cat", cate);
            mStringEntity = new StringEntity(mJsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(product_list.this, "Json error", Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(product_list.this, "string error", Toast.LENGTH_LONG).show();
        }
        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.setTimeout(7000);
        mClient.addHeader("Accept", "application/json");
        mClient.post(product_list.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/product_by_cat", mStringEntity, "application/json", new JsonHttpResponseHandler() {
            ProgressDialog mProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray mJsonArray = response.getJSONArray("d");
                    JSONObject mJsonObject = new JSONObject();
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        mArrayList.add(new product_list_class((mJsonObject.getInt("pk_pro_id")), (mJsonObject.getInt("pro_price")), (mJsonObject.getInt("count")), (mJsonObject.getInt("punique")), mJsonObject.getString("pro_category"), mJsonObject.getString("pro_name"), mJsonObject.getString("fk_email_id"), mJsonObject.getString("description"), mJsonObject.getString("pro_condition"), mJsonObject.getString("pro_img1"), mJsonObject.getString("pro_img2")));
                    }
                    gridView.setAdapter(new product_list_custom_adapter(product_list.this, mArrayList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(product_list.this, "Something went wrong", Toast.LENGTH_LONG);
            }

            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(product_list.this, "Loading", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                product_list_class productListClass = (product_list_class) parent.getItemAtPosition(position);
                Intent intent = new Intent(product_list.this, product_detail.class);
                intent.putExtra("id", productListClass.pk_pro_id);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        MenuItem SearchIteam = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(SearchIteam);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent it = new Intent(product_list.this, search_listview.class);
                it.putExtra("query", query);
                startActivity(it);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
