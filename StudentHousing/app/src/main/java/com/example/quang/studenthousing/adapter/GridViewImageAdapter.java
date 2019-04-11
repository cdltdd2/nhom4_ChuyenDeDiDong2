package com.example.quang.studenthousing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.quang.studenthousing.R;
import java.util.ArrayList;


public class GridViewImageAdapter extends BaseAdapter
{
    private Context context;
    private int layout;
    private ArrayList<String> arrItem;

    public GridViewImageAdapter(Context context, int layout, ArrayList<String> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        ImageView imView;
        ImageView imRemove;
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
                holder.imView = viewRow.findViewById(R.id.imViewItemImage);
            }
            if (viewRow != null) {
                holder.imRemove = viewRow.findViewById(R.id.removeImage);
            }

            if (viewRow != null) {
                viewRow.setTag(holder);
            }
        }
        String item = arrItem.get(i);
        ViewHolder holder = null;
        if (viewRow != null) {
            holder = (ViewHolder) viewRow.getTag();
        }

        return viewRow;
    }
}