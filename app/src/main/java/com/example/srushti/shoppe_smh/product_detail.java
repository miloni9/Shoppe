package com.example.srushti.shoppe_smh;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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




public class product_detail extends AppCompatActivity implements ActionBar.TabListener, image_1.OnFragmentInteractionListener1,image_2.OnFragmentInteractionListener {


    ActionBar mActionBar;
    TextView textView_pro_description,textView_pro_price,textView_pro_condition,seller;
    ViewPager viewPager;
    SectionPagerAdapter mSectionPagerAdapter;
int liked=0;
    int count=1;
    String param1,param2;
   Button like,unlike;
    int id;
    int flag;
    int pid=0;
    String fav_id;
    String product_author="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        final global_class globalClass= (global_class) getApplicationContext();

        seller= (TextView) findViewById(R.id.product_detail_seller_name);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));

        viewPager = (ViewPager) findViewById(R.id.product_details_viewpager);

        like= (Button) findViewById(R.id.product_detail_like);
      unlike=(Button) findViewById(R.id.product_detail_unlike);

mActionBar=getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Intent mintent=getIntent();
          id=mintent.getIntExtra("id", 0);

        textView_pro_description= (TextView) findViewById(R.id.txt_pro_description);
        textView_pro_price= (TextView) findViewById(R.id.txt_pro_price);
        textView_pro_condition= (TextView) findViewById(R.id.txt_pro_condition);

       unlike.setBackgroundResource(R.mipmap.favrit);
        like.setBackgroundResource(R.mipmap.unfavrit);
if(globalClass.getEmail()!=null) {


    JSONObject JsonObject = new JSONObject();
    StringEntity StringEntity = null;
    try {
        JsonObject.put("email", globalClass.getEmail());
        StringEntity = new StringEntity(JsonObject.toString());
    } catch (JSONException e) {
        e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }

    AsyncHttpClient mclient = new AsyncHttpClient();
    mclient.addHeader("Accept", "application/json");
    mclient.post(product_detail.this, "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/view_profile_fav", StringEntity, "application/json", new JsonHttpResponseHandler() {

        ProgressDialog mProgressDialog;

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);

            JSONArray JsonArray = null;
            try {
                JsonArray = response.getJSONArray("d");


                for (int i = 0; i < JsonArray.length(); i++) {
                    JSONObject jsonObject = JsonArray.getJSONObject(i);
                    if (id == jsonObject.getInt("pk_pro_id")) {
                        flag = 1;
                        unlike.setVisibility(View.VISIBLE);
                        like.setVisibility(View.INVISIBLE);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Toast.makeText(product_detail.this, "Something went wrong.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStart() {
            super.onStart();
            mProgressDialog = ProgressDialog.show(product_detail.this, "Loading", "Please Wait...");
        }

        @Override
        public void onFinish() {
            super.onFinish();
            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();
        }
    });
}
        JSONObject mJsonObject=new JSONObject();
        StringEntity mStringEntity=null;
        try {
            mJsonObject.put("id",id);
            mStringEntity=new StringEntity(mJsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Accept", "application/json");
        client.post(product_detail.this, "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/product_by_id", mStringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog mProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray mJsonArray = null;
                try {
                    mJsonArray = response.getJSONArray("d");
                    JSONObject jsonObject = mJsonArray.getJSONObject(0);
                    textView_pro_description.setText(jsonObject.getString("description"));
                    textView_pro_price.setText(jsonObject.getInt("pro_price") + "");
                    textView_pro_condition.setText(jsonObject.getString("pro_condition"));
                    param1 = jsonObject.getString("pro_img1");
                    param2 = jsonObject.getString("pro_img2");
                    getSupportActionBar().setTitle(jsonObject.getString("pro_name"));
                    seller.setText(jsonObject.getString("fk_email_id"));

                    pid = jsonObject.getInt("pk_pro_id");
                    product_author = jsonObject.getString("fk_email_id");

                    if (param2 == null) {
                        param2 = param1;
                    }
                    //Toast.makeText(product_detail.this,param1,Toast.LENGTH_LONG).show();



                    mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(mSectionPagerAdapter);
                    viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    });

                    for (int i = 0; i < mSectionPagerAdapter.getCount(); i++) {
                        mActionBar.addTab(mActionBar.newTab().setText(mSectionPagerAdapter.getPageTitle(i)).setTabListener(product_detail.this));
                    }
                    viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            mActionBar.setSelectedNavigationItem(position);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(product_detail.this, "Something went wrong.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(product_detail.this, "Loading", "Please Wait...");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });

                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count = count + 1;
                        liked = 1;

                        if (globalClass.getEmail() != null) {
                            if (count % 2 == 0) {
                                JSONObject mJsonObject = new JSONObject();
                                StringEntity mStringEntity = null;
                                try {
                                    mJsonObject.put("email", globalClass.getEmail());
                                    mJsonObject.put("pid", Integer.parseInt(pid + ""));

                                    mStringEntity = new StringEntity(mJsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                AsyncHttpClient mClient = new AsyncHttpClient();
                                mClient.addHeader("Accept", "application/json");
                                mClient.post(product_detail.this, "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/add_to_favourites", mStringEntity, "application/json", new JsonHttpResponseHandler() {
                                    ProgressDialog mProgressDialog;

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        try {
                                            int ans = response.getInt("d");
                                            if (ans == 1) {
                                               // Toast.makeText(product_detail.this, "added to favourites", Toast.LENGTH_LONG).show();
                                                unlike.setVisibility(View.VISIBLE);
                                                like.setVisibility(View.INVISIBLE);

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                        super.onFailure(statusCode, headers, throwable, errorResponse);
                                        Toast.makeText(product_detail.this, "Something went wrong ", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                        mProgressDialog = ProgressDialog.show(product_detail.this, "Loading", "Please wait...");
                                    }

                                    @Override
                                    public void onFinish() {
                                        super.onFinish();

                                        if (mProgressDialog.isShowing())
                                        {
                                            mProgressDialog.dismiss();
                                        }
                                    }

                                });


                                JSONObject pJsonObject = new JSONObject();
                                StringEntity pStringEntity = null;
                                try {
                                    pJsonObject.put("id", id);

                                    pStringEntity = new StringEntity(pJsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                AsyncHttpClient pClient = new AsyncHttpClient();
                                pClient.addHeader("Accept", "application/json");
                                pClient.post(product_detail.this, "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/update_product_in", pStringEntity, "application/json", new JsonHttpResponseHandler() {
                                    ProgressDialog mProgressDialog;

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        try {
                                            int ans = response.getInt("d");
                                            if (ans == 1)
                                            {

                                            }
                                               // Toast.makeText(product_detail.this, "check your wish list", Toast.LENGTH_LONG).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                        super.onFailure(statusCode, headers, throwable, errorResponse);
                                       // Toast.makeText(product_detail.this, "Something went wrong in increament", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                       // mProgressDialog = ProgressDialog.show(product_detail.this, "Loading", "Please wait...");
                                    }

                                    @Override
                                    public void onFinish() {
                                        super.onFinish();

                                    }

                                });
                            }


                        } else {
                            Toast.makeText(product_detail.this, "Please login first", Toast.LENGTH_LONG).show();
                        }
                    }
                });



unlike.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            JSONObject mJsonObject = new JSONObject();
            StringEntity mStringEntity = null;
            try {
                mJsonObject.put("pid", Integer.parseInt(pid + ""));
                mJsonObject.put("userid", globalClass.getEmail());

                mStringEntity = new StringEntity(mJsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            AsyncHttpClient mClient = new AsyncHttpClient();
            mClient.addHeader("Accept", "application/json");
            mClient.post(product_detail.this, "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/unfavourite", mStringEntity, "application/json", new JsonHttpResponseHandler() {
                ProgressDialog mProgressDialog;

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        int ans = response.getInt("d");
                       if (ans == 1)
                       {
                           like.setVisibility(View.VISIBLE);
                           unlike.setVisibility(View.INVISIBLE);
                       }
                           //Toast.makeText(product_detail.this, "removed from favourites", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Toast.makeText(product_detail.this, "Something went wrong ", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStart() {
                    super.onStart();
                    mProgressDialog = ProgressDialog.show(product_detail.this, "Loading", "Please wait...");
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                }

            });


            JSONObject pJsonObject = new JSONObject();
            StringEntity pStringEntity = null;
            try {
                pJsonObject.put("id", id);

                pStringEntity = new StringEntity(pJsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            AsyncHttpClient pClient = new AsyncHttpClient();
            pClient.addHeader("Accept", "application/json");
            pClient.post(product_detail.this, "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/update_product_de", pStringEntity, "application/json", new JsonHttpResponseHandler() {
                ProgressDialog mProgressDialog;

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        int ans = response.getInt("d");
                        if (ans == 1){


                        }
                        //  Toast.makeText(product_detail.this, "Success", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                   // Toast.makeText(product_detail.this, "Something went wrong in increament", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStart() {
                    super.onStart();
                  //  mProgressDialog = ProgressDialog.show(product_detail.this, "Loading", "Please wait...");
                }

                @Override
                public void onFinish() {
                    super.onFinish();

                }

            });

        }
});

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return image_1.newInstance(param1,"");
                case 1:
                    return image_2.newInstance(param2,"");
                default:
                    return image_1.newInstance(param1,"");
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0:
                    return "Image 1";
                case 1:
                    return "Image 2";
                case 2:
                    return "Image 1";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
    public static Bitmap decodeBase64(String path)
    {
        byte[] decodeByte= Base64.decode(path, 0);
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

         if (id==R.id.action_chat)
        {
            final global_class globalClass= (global_class) getApplicationContext();

            if(globalClass.getEmail() != null) {


                Intent mIntent = new Intent(product_detail.this, chat_detail.class);
                mIntent.putExtra("pid", pid);
                mIntent.putExtra("pauthor", product_author);
                startActivity(mIntent);
            }
            else
            {
                Toast.makeText(product_detail.this,"Please login first",Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
