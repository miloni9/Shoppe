package com.example.srushti.shoppe_smh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class home extends AppCompatActivity {
    ImageView allcat,mosttalked,mostunique,spcloffers;
    Button sell;
    ImageView camara;
    String id;
    ArrayList<product_list_class> mArrayList;
    global_class globalClass;
    String m=null;


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder mBuilder=new AlertDialog.Builder(home.this);
        mBuilder.setTitle("ALert");
        mBuilder.setMessage("Do you want to exit?");
        mBuilder.setCancelable(false);

        mBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mArrayList=new ArrayList<>();
       // camara= (ImageView) findViewById(R.id.home_img_camara);
        allcat= (ImageView) findViewById(R.id.home_all_categories);
        mosttalked= (ImageView) findViewById(R.id.home_most_talked_about);
        mostunique= (ImageView) findViewById(R.id.home_most_unique);
        spcloffers= (ImageView) findViewById(R.id.home_all_products);
        sell= (Button) findViewById(R.id.home_start_selling);
        allcat.setImageResource(R.drawable.all_cat);
        mosttalked.setImageResource(R.drawable.most_talked);
        mostunique.setImageResource(R.drawable.unique);
        spcloffers.setImageResource(R.drawable.ap);
        globalClass= (global_class) getApplicationContext();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        getSupportActionBar().setTitle("Shoppe");

        Intent idIntent=getIntent();
        id=idIntent.getStringExtra("id");

        allcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(home.this, all_categories.class);
                startActivity(mIntent);
            }
        });
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (globalClass.getEmail() != null) {
                        Intent mIntent = new Intent(home.this, sell.class);
                    startActivity(mIntent);

                } else {
                    Toast.makeText(home.this, "Please Login first", Toast.LENGTH_LONG).show();


                }

            }
        });

        mosttalked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(home.this, activity_most_talked_about.class);
                startActivity(mIntent);

            }
        });
        mostunique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(home.this, most_unique.class);
                startActivity(mIntent);
            }
        });
        spcloffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(home.this, all_products.class);
                startActivity(mIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem SearchIteam=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(SearchIteam);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent it = new Intent(home.this, search_listview.class);
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
        if (id==R.id.action_edit_profile){

            if(globalClass.getEmail()!=null) {
                Intent mIntent=new Intent(home.this,edit_profile.class);
                startActivity(mIntent);
            }
            else
            {
                Toast.makeText(home.this,"Please login first",Toast.LENGTH_LONG).show();
            }

        }

        else  if (id==R.id.action_register){
            if (globalClass.getEmail()==null) {
                Intent mIntent = new Intent(home.this, register.class);
                startActivity(mIntent);
            }
            else
            {
                Toast.makeText(home.this,"You are alredy a user",Toast.LENGTH_LONG).show();
            }
        }
        else  if (id==R.id.action_view_profile){
         if(globalClass.getEmail() !=null) {
             Intent mIntent = new Intent(home.this, profile.class);
             startActivity(mIntent);
            }
            else
            {
                Toast.makeText(home.this,"Please login first",Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(home.this, login.class);
                startActivity(mIntent);

            }

        }
        else  if (id==R.id.action_feedback){
            if(globalClass.getEmail() !=null) {
                Intent mIntent = new Intent(home.this, feedback.class);
                startActivity(mIntent);
            }
            else
            {
                Toast.makeText(home.this,"Please login first",Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(home.this, login.class);
                startActivity(mIntent);

            }

        }


        return super.onOptionsItemSelected(item);
    }
}
