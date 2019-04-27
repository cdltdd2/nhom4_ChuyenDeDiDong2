//DUY: DSACH NHÀ DẠNG GRID (DẠNG LƯỚI)
package com.example.quang.studenthousing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.services.APIClient;

import java.util.ArrayList;

public class GridViewHouseAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<House> arrItem;

    public GridViewHouseAdapter(Context context, int layout, ArrayList<House> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        TextView tvTitle;
        ImageView imView;
        TextView tvPrice;
        TextView tvAcreage;
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
                holder.tvTitle = viewRow.findViewById(R.id.tvItemTitle);
            }
            if (viewRow != null) {
                holder.tvPrice = viewRow.findViewById(R.id.tvItemPrice);
            }
            if (viewRow != null) {
                holder.tvAcreage = viewRow.findViewById(R.id.tvItemAcreage);
            }
            if (viewRow != null) {
                holder.imView = viewRow.findViewById(R.id.imItemHouse);
            }

            if (viewRow != null) {
                viewRow.setTag(holder);
            }
        }
        House item = arrItem.get(i);
        ViewHolder holder = null;
        if (viewRow != null) {
            holder = (ViewHolder) viewRow.getTag();
        }
        if (holder != null) {
            holder.tvTitle.setText(item.getTITLE());
        }
        if (holder != null) {
            holder.tvPrice.setText(item.getPRICE()+" " +context.getString(R.string.million));
        }
        if (holder != null) {
            holder.tvAcreage.setText(item.getACREAGE()+" "+context.getString(R.string.meter2));
        }
        if (holder != null) {
            Glide.with(context).load(APIClient.BASE_URL+item.getIMAGE()).into(holder.imView);
        }

        return viewRow;
    }
}