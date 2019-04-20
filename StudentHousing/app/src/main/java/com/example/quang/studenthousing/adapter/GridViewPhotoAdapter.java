package com.example.quang.studenthousing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.object.UrlPhoto;
import com.example.quang.studenthousing.services.APIClient;

import java.util.ArrayList;

public class GridViewPhotoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<UrlPhoto> arrItem;

    //khoi tao
    public GridViewPhotoAdapter(Context context, int layout, ArrayList<UrlPhoto> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        ImageView imView;
    }

    @Override
    public int getCount() {
        return arrItem.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //trang thai: chua ket noi api
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewRow = view;
        if(viewRow == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                viewRow = inflater.inflate(layout,viewGroup,false);
            }

            ViewHolder holder = new ViewHolder();
            if (viewRow != null) {
                holder.imView = viewRow.findViewById(R.id.imViewItemPhoto);
            }
            if (viewRow != null) {
                viewRow.setTag(holder);
            }
        }
        String item = arrItem.get(i).getURL();
        ViewHolder holder = null;
        if (viewRow != null) {
            holder = (ViewHolder) viewRow.getTag();
        }
        if (holder != null) {
            Glide.with(context).load(APIClient.BASE_URL+item).into(holder.imView);
        }
        return viewRow;
    }
}