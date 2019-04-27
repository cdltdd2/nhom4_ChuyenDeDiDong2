//DUY: ADAPTER CHỨA DỮ LIỆU LỊCH SỬ ĐẶT PHÒNG
package com.example.quang.studenthousing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.object.PersonBooking;

import java.util.ArrayList;

public class ListViewBookingAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<PersonBooking> arrItem;

    public ListViewBookingAdapter(Context context, int layout, ArrayList<PersonBooking> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        TextView tvName;
        TextView tvTitle;
        TextView tvPhone;
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
                holder.tvName = viewRow.findViewById(R.id.tvNamePersonHouseBooking);
            }
            if (viewRow != null) {
                holder.tvPhone = viewRow.findViewById(R.id.tvPhonePersonHouseBooking);
            }
            if (viewRow != null) {
                holder.tvTitle = viewRow.findViewById(R.id.tvTitleHouseBooking);
            }

            if (viewRow != null) {
                viewRow.setTag(holder);
            }
        }
        PersonBooking personBooking = arrItem.get(i);
        ViewHolder holder = null;
        if (viewRow != null) {
            holder = (ViewHolder) viewRow.getTag();
        }
        if (holder != null) {
            holder.tvName.setText(personBooking.getNAME());
        }
        if (holder != null) {
            holder.tvTitle.setText(personBooking.getTITLE());
        }
        if (holder != null) {
            String phone = personBooking.getPHONE();
            if (phone.equals("null")){
                holder.tvPhone.setText(context.getString(R.string.no_phone));
            }else {
                holder.tvPhone.setText(phone);
            }
        }


        return viewRow;
    }

}