//LINH: ADAPTER CHỨA DỮ LIỆU COMMENT
package com.example.quang.studenthousing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.object.Comment;

import java.util.ArrayList;

public class ListViewCommentAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Comment> arrItem;

    public ListViewCommentAdapter(Context context, int layout, ArrayList<Comment> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        TextView textView;
        TextView tvTime;
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
                holder.textView = viewRow.findViewById(R.id.tvComment);
            }
            if (viewRow != null) {
                holder.tvTime = viewRow.findViewById(R.id.tvTimeComment);
            }
            if (viewRow != null) {
                viewRow.setTag(holder);
            }
        }
        String item = arrItem.get(i).getTEXT();
        ViewHolder holder = null;
        if (viewRow != null) {
            holder = (ViewHolder) viewRow.getTag();
        }
        if (holder != null) {
            holder.textView.setText(item);
        }
        if (holder != null) {
            holder.tvTime.setText(arrItem.get(i).getCREATEDAT());
        }
        return viewRow;
    }
}