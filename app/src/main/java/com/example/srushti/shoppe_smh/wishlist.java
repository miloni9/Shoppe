package com.example.srushti.shoppe_smh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class wishlist extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<product_list_class> mArrayList;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener1 mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment wishlist.
     */
    // TODO: Rename and change types and number of parameters
    public static wishlist newInstance(String param1, String param2) {
        wishlist fragment = new wishlist();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public wishlist() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View mView= inflater.inflate(R.layout.fragment_wishlist, container, false);
        final GridView gridView;
        gridView = (GridView) mView.findViewById(R.id.wishlist_grid);

        mArrayList=new ArrayList<product_list_class>();
       // gridView= (GridView) mView.findViewById(R.id.wishlist_grid);
        final JSONObject mjsonObject=new JSONObject();
        StringEntity mStringEntity=null;
        try {
            mjsonObject.put("email",mParam1);
            mStringEntity=new StringEntity( mjsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client=new AsyncHttpClient();
        client.addHeader("Accept", "application/json");
        client.post(mView.getContext(), "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/view_profile_fav", mStringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog mProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray mJsonArray = null;
                try {
                    mJsonArray = response.getJSONArray("d");
                    JSONObject jsonObject;
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        jsonObject = mJsonArray.getJSONObject(i);
                        mArrayList.add(new product_list_class((jsonObject.getInt("pk_pro_id")), (jsonObject.getInt("pro_price")), (jsonObject.getInt("count")), (jsonObject.getInt("punique")), jsonObject.getString("pro_category"), jsonObject.getString("pro_name"), jsonObject.getString("fk_fav_email_id"), jsonObject.getString("description"), jsonObject.getString("pro_condition"), jsonObject.getString("pro_img1"), jsonObject.getString("pro_img2")));

                    }


                  gridView.setAdapter(new product_list_custom_adapter(mView.getContext(),mArrayList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        product_list_class productListClass = (product_list_class) parent.getItemAtPosition(position);
                        Intent intent = new Intent(mView.getContext(), product_detail.class);
                        intent.putExtra("id", productListClass.pk_pro_id);
                        startActivity(intent);
                    }
                });
            }



            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(mView.getContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                mProgressDialog = ProgressDialog.show(mView.getContext(), "Loading", "Please Wait...");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });




        return mView;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener1 {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
