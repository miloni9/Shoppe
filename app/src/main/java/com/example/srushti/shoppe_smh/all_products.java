package com.example.srushti.shoppe_smh;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class all_products extends AppCompatActivity {

    GridView gridView;
    int i;
    Button filter, bsort;
    RadioButton relevance , lowtohigh , hightolow, newestfirst;
    ArrayList<product_list_class> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        gridView = (GridView) findViewById(R.id.all_product_grid);
        mArrayList = new ArrayList<product_list_class>();


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        getSupportActionBar().setTitle("Our Products");
        filter = (Button) findViewById(R.id.all_product_filter);
        bsort = (Button) findViewById(R.id.all_product_sort);

        AsyncHttpClient mClient = new AsyncHttpClient();
        mClient.setTimeout(7000);
        mClient.addHeader("Accept", "application/json");
        mClient.addHeader("Content-type", "application/json");
        mClient.get(all_products.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/show_all_products", new JsonHttpResponseHandler() {
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

                    gridView.setAdapter(new product_list_custom_adapter(all_products.this, mArrayList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(all_products.this, "Something went wrong", Toast.LENGTH_LONG);
            }

            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(all_products.this, "Loading", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });




        bsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(all_products.this);
                dialog.setContentView(R.layout.sort);
                dialog.setTitle("Sort products");

                dialog.setCancelable(true);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
                Button btn = (Button) dialog.findViewById(R.id.sort_ok);
                RadioGroup rbtn = (RadioGroup) findViewById(R.id.sort_rbtn_grp);
                relevance = (RadioButton) dialog.findViewById(R.id.sort_relevance);
                lowtohigh = (RadioButton) dialog.findViewById(R.id.sort_low_to_high);
                hightolow = (RadioButton) dialog.findViewById(R.id.sort_high_to_low);
                newestfirst = (RadioButton) dialog.findViewById(R.id.sort_newest_first);
                dialog.show();

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (relevance.isChecked()) {

                            AsyncHttpClient mClient = new AsyncHttpClient();
                            mClient.setTimeout(7000);
                            mClient.addHeader("Accept", "application/json");
                            mClient.addHeader("Content-type", "application/json");
                            mClient.get(all_products.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/show_all_products", new JsonHttpResponseHandler() {
                                ProgressDialog mProgressDialog;

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    try {
                                        JSONArray mJsonArray = response.getJSONArray("d");
                                        JSONObject mJsonObject = new JSONObject();
                                        mArrayList.clear();
                                        for (int i = 0; i < mJsonArray.length(); i++) {
                                            mJsonObject = mJsonArray.getJSONObject(i);

                                            mArrayList.add(new product_list_class((mJsonObject.getInt("pk_pro_id")), (mJsonObject.getInt("pro_price")), (mJsonObject.getInt("count")), (mJsonObject.getInt("punique")), mJsonObject.getString("pro_category"), mJsonObject.getString("pro_name"), mJsonObject.getString("fk_email_id"), mJsonObject.getString("description"), mJsonObject.getString("pro_condition"), mJsonObject.getString("pro_img1"), mJsonObject.getString("pro_img2")));
                                        }

                                        gridView.setAdapter(new product_list_custom_adapter(all_products.this, mArrayList));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    super.onFailure(statusCode, headers, throwable, errorResponse);
                                    Toast.makeText(all_products.this, "Something went wrong", Toast.LENGTH_LONG);
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(all_products.this, "Loading", "Please wait");
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

                    if(lowtohigh.isChecked())

                    {

                        AsyncHttpClient mClient = new AsyncHttpClient();
                        mClient.setTimeout(7000);
                        mClient.addHeader("Accept", "application/json");
                        mClient.addHeader("Content-type", "application/json");
                        mClient.get(all_products.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/product_ascending", new JsonHttpResponseHandler() {
                            ProgressDialog mProgressDialog;

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                try {
                                    JSONArray mJsonArray = response.getJSONArray("d");
                                    JSONObject mJsonObject = new JSONObject();
                                    mArrayList.clear();
                                    for (int i = 0; i < mJsonArray.length(); i++) {
                                        mJsonObject = mJsonArray.getJSONObject(i);

                                        mArrayList.add(new product_list_class((mJsonObject.getInt("pk_pro_id")), (mJsonObject.getInt("pro_price")), (mJsonObject.getInt("count")), (mJsonObject.getInt("punique")), mJsonObject.getString("pro_category"), mJsonObject.getString("pro_name"), mJsonObject.getString("fk_email_id"), mJsonObject.getString("description"), mJsonObject.getString("pro_condition"), mJsonObject.getString("pro_img1"), mJsonObject.getString("pro_img2")));
                                    }

                                    gridView.setAdapter(new product_list_custom_adapter(all_products.this, mArrayList));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                Toast.makeText(all_products.this, "Something went wrong", Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                mProgressDialog = ProgressDialog.show(all_products.this, "Loading", "Please wait");
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

                    if(hightolow.isChecked())

                    {

                        AsyncHttpClient mClient = new AsyncHttpClient();
                        mClient.setTimeout(7000);
                        mClient.addHeader("Accept", "application/json");
                        mClient.addHeader("Content-type", "application/json");
                        mClient.get(all_products.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/product_descending", new JsonHttpResponseHandler() {
                            ProgressDialog mProgressDialog;

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                try {
                                    JSONArray mJsonArray = response.getJSONArray("d");
                                    JSONObject mJsonObject = new JSONObject();
                                    mArrayList.clear();
                                    for (int i = 0; i < mJsonArray.length(); i++) {
                                        mJsonObject = mJsonArray.getJSONObject(i);

                                        mArrayList.add(new product_list_class((mJsonObject.getInt("pk_pro_id")), (mJsonObject.getInt("pro_price")), (mJsonObject.getInt("count")), (mJsonObject.getInt("punique")), mJsonObject.getString("pro_category"), mJsonObject.getString("pro_name"), mJsonObject.getString("fk_email_id"), mJsonObject.getString("description"), mJsonObject.getString("pro_condition"), mJsonObject.getString("pro_img1"), mJsonObject.getString("pro_img2")));
                                    }

                                    gridView.setAdapter(new product_list_custom_adapter(all_products.this, mArrayList));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                Toast.makeText(all_products.this, "Something went wrong", Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                mProgressDialog = ProgressDialog.show(all_products.this, "Loading", "Please wait");
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

                    if(newestfirst.isChecked())

                    {

                        AsyncHttpClient mClient = new AsyncHttpClient();
                        mClient.setTimeout(7000);
                        mClient.addHeader("Accept", "application/json");
                        mClient.addHeader("Content-type", "application/json");
                        mClient.get(all_products.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/product_newest", new JsonHttpResponseHandler() {
                            ProgressDialog mProgressDialog;

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                try {
                                    JSONArray mJsonArray = response.getJSONArray("d");
                                    JSONObject mJsonObject = new JSONObject();
                                    mArrayList.clear();
                                    for (int i = 0; i < mJsonArray.length(); i++) {
                                        mJsonObject = mJsonArray.getJSONObject(i);

                                        mArrayList.add(new product_list_class((mJsonObject.getInt("pk_pro_id")), (mJsonObject.getInt("pro_price")), (mJsonObject.getInt("count")), (mJsonObject.getInt("punique")), mJsonObject.getString("pro_category"), mJsonObject.getString("pro_name"), mJsonObject.getString("fk_email_id"), mJsonObject.getString("description"), mJsonObject.getString("pro_condition"), mJsonObject.getString("pro_img1"), mJsonObject.getString("pro_img2")));
                                    }

                                    gridView.setAdapter(new product_list_custom_adapter(all_products.this, mArrayList));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                Toast.makeText(all_products.this, "Something went wrong", Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                mProgressDialog = ProgressDialog.show(all_products.this, "Loading", "Please wait");
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

                    dialog.dismiss();
                }
            }

            );
        }

    });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(all_products.this);
                dialog.setContentView(R.layout.filter);
                dialog.setTitle("Filter products");
                dialog.show();

                dialog.setCancelable(true);
                Button result= (Button) dialog.findViewById(R.id.filter_btn_result);
                final RadioButton upto500,upto1000,upto5000,upto10000;
                upto500= (RadioButton) dialog.findViewById(R.id.filter_rbtn_500);
                upto5000= (RadioButton) dialog.findViewById(R.id.filter_rbtn_5000);
                upto1000= (RadioButton) dialog.findViewById(R.id.filter_rbtn_1000);
                upto10000= (RadioButton) dialog.findViewById(R.id.filter_rbtn_10000);

                result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (upto1000.isChecked()) {
                            i = 1000;
                        } else if (upto10000.isChecked()) {
                            i = 10000;
                        } else if (upto500.isChecked()) {
                            i = 500;
                        } else if (upto5000.isChecked()) {
                            i = 5000;
                        }
                        JSONObject mJsonObject = new JSONObject();
                        StringEntity mStringEntity = null;

                        try {
                            mJsonObject.put("maxprice", i);
                            mStringEntity = new StringEntity(mJsonObject.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(all_products.this, "Json error", Toast.LENGTH_LONG).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Toast.makeText(all_products.this, "string error", Toast.LENGTH_LONG).show();
                        }
                        AsyncHttpClient mClient = new AsyncHttpClient();
                        mClient.setTimeout(7000);
                        mClient.addHeader("Accept", "application/json");
                        mClient.post(all_products.this, "http://www.jinalshah.brainoorja.com/Webservice_mhs.asmx/product_by_price", mStringEntity, "application/json", new JsonHttpResponseHandler() {
                            ProgressDialog mProgressDialog;

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                try {
                                    JSONArray mJsonArray = response.getJSONArray("d");
                                    JSONObject mJsonObject = new JSONObject();
                                    mArrayList.clear();
                                    for (int i = 0; i < mJsonArray.length(); i++) {
                                        mJsonObject = mJsonArray.getJSONObject(i);

                                        mArrayList.add(new product_list_class((mJsonObject.getInt("pk_pro_id")), (mJsonObject.getInt("pro_price")), (mJsonObject.getInt("count")), (mJsonObject.getInt("punique")), mJsonObject.getString("pro_category"), mJsonObject.getString("pro_name"), mJsonObject.getString("fk_email_id"), mJsonObject.getString("description"), mJsonObject.getString("pro_condition"), mJsonObject.getString("pro_img1"), mJsonObject.getString("pro_img2")));
                                    }

                                    gridView.setAdapter(new product_list_custom_adapter(all_products.this, mArrayList));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                super.onFailure(statusCode, headers, throwable, errorResponse);
                                Toast.makeText(all_products.this, "Something went wrong", Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                mProgressDialog = ProgressDialog.show(all_products.this, "Loading", "Please wait");
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                if (mProgressDialog.isShowing()) {
                                    mProgressDialog.dismiss();
                                }
                            }
                        });
                        dialog.dismiss();
                    }

                });


            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                product_list_class productListClass = (product_list_class) parent.getItemAtPosition(position);
                Intent intent = new Intent(all_products.this, product_detail.class);
                intent.putExtra("id", productListClass.pk_pro_id);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_products, menu);
        MenuItem SearchIteam = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(SearchIteam);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent it = new Intent(all_products.this, search_listview.class);
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

        return super.onOptionsItemSelected(item);
    }
}
