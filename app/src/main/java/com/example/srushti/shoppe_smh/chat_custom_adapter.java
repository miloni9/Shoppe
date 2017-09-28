package com.example.srushti.shoppe_smh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by srushti on 03-11-2015.
 */
public class chat_custom_adapter extends BaseAdapter
{
    ArrayList<chat_class> mArrayList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public chat_custom_adapter(ArrayList<chat_class> mArrayList, Context mContext) {
        this.mArrayList = mArrayList;
        this.mContext = mContext;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        chat_view_holder mViewHolder;
        if (convertView==null)
        {
            convertView=mLayoutInflater.inflate(R.layout.chat_box,null);
            mViewHolder=new chat_view_holder((TextView) convertView.findViewById(R.id.chat_box_text));
            convertView.setTag(mViewHolder);
        }
        else
        {
            mViewHolder= (chat_view_holder) convertView.getTag();
        }
        mViewHolder.tv1.setText(mArrayList.get(position).getMessage());
        return convertView;
    }
}
