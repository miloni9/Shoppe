package com.example.srushti.shoppe_smh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class sell extends AppCompatActivity {
    Button publish;
    Button takepicture,picturefromgallery;
    ImageView take,pick;
    EditText title,description,sellingprice;
    Spinner category;
    ToggleButton tbtn;
    RadioButton conditionnew,conditionused;
    CheckBox terms;
ImageView image;
    static int LOAD_IMAGE_RESULTS = 2;
    static final int REQUEST_CAPTURE=1;
    Bitmap bitmap,bitmap1;

    int uniq;

    String str[]=new String[8],op,rbtn;
    global_class globalClass;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CAPTURE && resultCode==RESULT_OK)
        {
            Bundle bundle=data.getExtras();
            bitmap= (Bitmap) bundle.get("data");
            if(bitmap!=null)
            {
                take.setImageBitmap(bitmap);
                takepicture.setVisibility(View.INVISIBLE);

            }
        }
      else  if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {

                // Let's read picked image data - its URI
                Uri pickedImage = data.getData();
                // Let's read picked image path using content resolver
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                // Now we need to set the GUI ImageView data with data read from the picked file.
                bitmap1=(BitmapFactory.decodeFile(imagePath));
            if (bitmap1!=null)
            {
                pick.setImageBitmap(bitmap1);
                picturefromgallery.setVisibility(View.INVISIBLE);
            }
                // At the end remember to close the cursor or you will end with the RuntimeException!
                cursor.close();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        publish= (Button) findViewById(R.id.sell_publish);

        take= (ImageView) findViewById(R.id.sell_take_picture_i);
        pick= (ImageView) findViewById(R.id.sell_pick_from_gallery_i);
        picturefromgallery= (Button) findViewById(R.id.sell_pick_from_gallery);
        title= (EditText) findViewById(R.id.sell_product_name);
        description= (EditText) findViewById(R.id.sell_product_description);
        sellingprice= (EditText) findViewById(R.id.sell_selling_price);
        conditionnew= (RadioButton) findViewById(R.id.sell_condition_new);
        conditionused= (RadioButton) findViewById(R.id.sell_condition_used);
        terms= (CheckBox) findViewById(R.id.sell_terms);
        tbtn= (ToggleButton) findViewById(R.id.sell_unique);
        category= (Spinner) findViewById(R.id.sell_category);
        globalClass= (global_class) getApplicationContext();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));

        picturefromgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, LOAD_IMAGE_RESULTS);
               // lv.clearTextFilter();
            }
        });
        str[0]="mens fashion";
        str[1]="womens fashion";
        str[2]="kids stuff";
        str[3]="accessories and jewellery";
        str[4]="mobile accessories";
        str[5]="creativity";
        str[6]="everything else";
        str[7]="foot wear";
        ArrayAdapter<String> mArrayAdapter=new ArrayAdapter<String>(sell.this,R.layout.support_simple_spinner_dropdown_item,str);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(mArrayAdapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                op = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        takepicture= (Button) findViewById(R.id.sell_take_picture);
        takepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, REQUEST_CAPTURE);
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if (terms.isChecked() && title.getText() != null && sellingprice.getText() != null ) {

                            if (conditionused.isChecked()) {
                                rbtn = "used";
                            } else {
                                rbtn = "new";
                            }

                            if (tbtn.isChecked()) {
                                uniq = 1;
                            } else {
                                uniq = 0;
                            }

                            JSONObject mJsonObject = new JSONObject();
                            StringEntity mStringEntity = null;
                            try {
                                mJsonObject.put("name", title.getText() + "");
                                mJsonObject.put("price", Integer.parseInt(sellingprice.getText() + ""));
                                mJsonObject.put("cat", op);
                                mJsonObject.put("email", globalClass.getEmail());
                                mJsonObject.put("desc", description.getText() + "");
                                mJsonObject.put("condition", rbtn);
                                mJsonObject.put("punique", uniq);
                                if (bitmap!=null)
                                mJsonObject.put("path1",encodeToBase64(bitmap) );

                                else
                                    mJsonObject.put("path1","" );
                                if(bitmap1!=null)
                                mJsonObject.put("path2", encodeToBase64(bitmap1));
                                else
                                    mJsonObject.put("path2","" );
                                mStringEntity = new StringEntity(mJsonObject.toString());
                                //
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            AsyncHttpClient mClient = new AsyncHttpClient();
                            mClient.addHeader("Accept", "application/json");
                            mClient.post(sell.this, "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/add_new_product", mStringEntity, "application/json", new JsonHttpResponseHandler() {
                                ProgressDialog mProgressDialog;

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    try {
                                        int ans = response.getInt("d");
                                        if (ans == 1) {
                                            Toast.makeText(sell.this, "Success", Toast.LENGTH_LONG).show();
                                            Intent mIntent=new Intent(sell.this,home.class);
                                            startActivity(mIntent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    super.onFailure(statusCode, headers, throwable, errorResponse);
                                    Toast.makeText(sell.this, "Something went wrong.", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                    mProgressDialog = ProgressDialog.show(sell.this, "Loading", "Please wait...");
                                }

                                @Override
                                public void onFinish() {
                                    super.onFinish();
                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                }

                            });
                        }
                        else
                        {
                            Toast.makeText(sell.this,"something is missing",Toast.LENGTH_LONG).show();
                        }

                    }

        });
    }

    public static String encodeToBase64(Bitmap bitmap_image)
    {
        Bitmap bitmapx=bitmap_image;
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmapx.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b=baos.toByteArray();
        String imageEncoded= Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sell, menu);
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
