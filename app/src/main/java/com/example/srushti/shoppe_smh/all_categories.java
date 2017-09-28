package com.example.srushti.shoppe_smh;

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
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class all_categories extends AppCompatActivity {

    ImageView accessory,men,women,kids,other,creativity,mobile,foot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        getSupportActionBar().setTitle("Categories");
        accessory= (ImageView) findViewById(R.id.all_categories_img_acc);
        men= (ImageView) findViewById(R.id.all_categories_img_men);
        women= (ImageView) findViewById(R.id.all_categories_img_women);
        mobile= (ImageView) findViewById(R.id.all_categories_img_mob);
        other= (ImageView) findViewById(R.id.all_categories_img_everything_else);
        creativity= (ImageView) findViewById(R.id.all_categories_img_creativity);
        kids= (ImageView) findViewById(R.id.all_categories_item_kids);
        foot= (ImageView) findViewById(R.id.all_categories_img_foot_wear);
        men.setImageResource(R.drawable.mens);
        accessory.setImageResource(R.drawable.jw);
        other.setImageResource(R.drawable.ee);
        women.setImageResource(R.drawable.womens);
        mobile.setImageResource(R.drawable.mobile);
        kids.setImageResource(R.drawable.kids);
        creativity.setImageResource(R.drawable.creativity);
        foot.setImageResource(R.drawable.foot_wears);
        accessory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(all_categories.this,product_list.class);
                mIntent.putExtra("cat","accessories and jewellery");
                startActivity(mIntent);
            }
        });
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(all_categories.this,product_list.class);
                mIntent.putExtra("cat","mens fashion");
                startActivity(mIntent);
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(all_categories.this,product_list.class);
                mIntent.putExtra("cat","womens fashion");
                startActivity(mIntent);
            }
        });
        kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(all_categories.this,product_list.class);
                mIntent.putExtra("cat","Kids");
                startActivity(mIntent);
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(all_categories.this,product_list.class);
                mIntent.putExtra("cat","mobile accessories");
                startActivity(mIntent);
            }
        });
        creativity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(all_categories.this,product_list.class);
                mIntent.putExtra("cat","creativity");
                startActivity(mIntent);
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(all_categories.this,product_list.class);
                mIntent.putExtra("cat","everything else");
                startActivity(mIntent);
            }
        });
        foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(all_categories.this, product_list.class);
                mIntent.putExtra("cat", "foot wear");
                startActivity(mIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_categories, menu);
        MenuItem SearchIteam=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(SearchIteam);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent it = new Intent(all_categories.this, search_listview.class);
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
