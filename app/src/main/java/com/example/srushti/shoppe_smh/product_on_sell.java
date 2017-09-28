package com.example.srushti.shoppe_smh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link product_on_sell.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link product_on_sell#newInstance} factory method to
 * create an instance of this fragment.
 */
public class
        product_on_sell extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;
    View mView;
    ArrayList<product_list_class> sarrayList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment product_on_sell.
     */
    // TODO: Rename and change types and number of parameters
    public static product_on_sell newInstance(String param1, String param2) {
        product_on_sell fragment = new product_on_sell();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public product_on_sell() {
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
      menu.add("Remove");


    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {

AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos=info.position;
        int id;
        id=sarrayList.get(pos).getPk_pro_id();
        if(item.getTitle()=="Remove")
        {

                    JSONObject mjsonObject = new JSONObject();
                    StringEntity mStringEntity = null;
                    try {
                        mjsonObject.put("pid", id);
                        mStringEntity = new StringEntity(mjsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    final AsyncHttpClient mclient = new AsyncHttpClient();
                    mclient.addHeader("Accept", "application/json");
                    mclient.post(context, "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/delete_product", mStringEntity, "application/json", new JsonHttpResponseHandler() {

                        ProgressDialog nProgressDialog;

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);

                            JSONArray mJsonArray = null;
                            try {
                                int ans = response.getInt("d");
                                if (ans == 1) {
                                    Toast.makeText(context, "item deleted", Toast.LENGTH_LONG).show();
                        //            GotoFactory.getSharedInstance().getGoto().go(4);
                                    Intent mIntent=new Intent(mView.getContext(),profile.class);
                                    startActivity(mIntent);

                                } else {
                                    Toast.makeText(context, "can't deleted", Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Toast.makeText(mView.getContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onStart() {
                            super.onStart();
                            nProgressDialog = ProgressDialog.show(mView.getContext(), "Loading", "Please Wait...");
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            if (nProgressDialog.isShowing()) {
                                nProgressDialog.dismiss();







































                            }
                        }
                    });


        }

return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_product_on_sell, container, false);
        final GridView gridView;

        gridView= (GridView) mView.findViewById(R.id.product_on_sell_list_grid);
        sarrayList=new ArrayList<>();
        registerForContextMenu(mView.findViewById(R.id.product_on_sell_list_grid));

        JSONObject  mjsonObject=new JSONObject();
        StringEntity mStringEntity=null;
        try {
            mjsonObject.put("email",mParam1);
            mStringEntity=new StringEntity( mjsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final AsyncHttpClient mclient=new AsyncHttpClient();
        mclient.addHeader("Accept", "application/json");
        mclient.post(mView.getContext(), "http://www.jinalshah.brainoorja.com/WebService_mhs.asmx/view_profile_sell", mStringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog nProgressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                JSONArray mJsonArray = null;
                try {
                    mJsonArray = response.getJSONArray("d");
                    JSONObject jsonObject = mJsonArray.getJSONObject(0);
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        jsonObject = mJsonArray.getJSONObject(i);

                        sarrayList.add(new product_list_class((jsonObject.getInt("pk_pro_id")), (jsonObject.getInt("pro_price")), (jsonObject.getInt("count")), (jsonObject.getInt("punique")), jsonObject.getString("pro_category"), jsonObject.getString("pro_name"), jsonObject.getString("fk_email_id"), jsonObject.getString("description"), jsonObject.getString("pro_condition"), jsonObject.getString("pro_img1"), jsonObject.getString("pro_img2")));
                    }
                   gridView.setAdapter(new product_list_custom_adapter(mView.getContext(),sarrayList));

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
                nProgressDialog = ProgressDialog.show(mView.getContext(), "Loading", "Please Wait...");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (nProgressDialog.isShowing())
                    nProgressDialog.dismiss();
            }
        });



        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        context = activity.getApplicationContext();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
