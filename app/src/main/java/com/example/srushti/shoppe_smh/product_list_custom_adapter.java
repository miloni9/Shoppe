package com.example.srushti.shoppe_smh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by srushti on 24-10-2015.
 */
public class product_list_custom_adapter extends BaseAdapter {
    Context mContext;
    ArrayList<product_list_class> mArraylist;
    LayoutInflater mLayoutInflater;

    public product_list_custom_adapter(Context mContext, ArrayList<product_list_class> mArraylist) {
        this.mContext = mContext;
        this.mArraylist = mArraylist;
        this.mLayoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mArraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mArraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        product_list_view_holder mProduct_list_view_holder;
        if(convertView==null)
        {
            convertView=mLayoutInflater.inflate(R.layout.product_list_item,null);
            mProduct_list_view_holder=new product_list_view_holder((ImageView)convertView.findViewById(R.id.product_list_item_img),(TextView)convertView.findViewById(R.id.product_list_item_txt),(TextView)convertView.findViewById(R.id.product_list_item_price));
            convertView.setTag(mProduct_list_view_holder);

        }
        else
        {
            mProduct_list_view_holder= (product_list_view_holder) convertView.getTag();
        }

        mProduct_list_view_holder.getTxtname().setText(mArraylist.get(position).getPro_name()+"");
        mProduct_list_view_holder.getTxtprice().setText(mArraylist.get(position).getPro_price()+"");
       // mProduct_list_view_holder.txtname.setText(mArrayList.get(position).getPro_name());
        if(mArraylist.get(position).getPro_img1()!=null) {
            mProduct_list_view_holder.getImg1().setImageBitmap(decodeBase64(mArraylist.get(position).getPro_img1()));
        }

        return convertView;
    }
    public static Bitmap decodeBase64(String path)
    {
        byte[] decodeByte= Base64.decode(path, 0);
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }
}
