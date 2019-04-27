//DUY: DSACH BÀI ĐĂNG CẦN DUYỆT (ADMIN) DẠNG LƯỚI
package com.example.quang.studenthousing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridViewHouseRequestAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<House> arrItem;

    public GridViewHouseRequestAdapter(Context context, int layout, ArrayList<House> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        TextView tvTitle;
        ImageView imView;
        TextView tvPrice;
        TextView tvAcreage;
        ImageView imMore;
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
                holder.tvTitle = viewRow.findViewById(R.id.tvItemTitleRequest);
            }
            if (viewRow != null) {
                holder.tvPrice = viewRow.findViewById(R.id.tvItemPriceRequest);
            }
            if (viewRow != null) {
                holder.tvAcreage = viewRow.findViewById(R.id.tvItemAcreageRequest);
            }
            if (viewRow != null) {
                holder.imView = viewRow.findViewById(R.id.imItemHouseRequest);
            }
            if (viewRow != null) {
                holder.imMore = viewRow.findViewById(R.id.more_request);
            }

            if (viewRow != null) {
                viewRow.setTag(holder);
            }
        }
        House house = arrItem.get(i);
        ViewHolder holder = null;
        if (viewRow != null) {
            holder = (ViewHolder) viewRow.getTag();
        }
        if (holder != null) {
            holder.tvTitle.setText(house.getTITLE());
        }
        if (holder != null) {
            holder.tvPrice.setText(house.getPRICE()+" " +context.getString(R.string.million));
        }
        if (holder != null) {
            holder.tvAcreage.setText(house.getACREAGE()+" "+context.getString(R.string.meter2));
        }
        if (holder != null) {
            Glide.with(context).load(APIClient.BASE_URL+house.getIMAGE()).into(holder.imView);
        }

        ViewHolder finalHolder = holder;
        holder.imMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, finalHolder.imMore);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_request, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.accept:
                                updateCheckUp(house.getIDHOUSE(),1,i);
                                break;
                            case R.id.denied:
                                updateCheckUp(house.getIDHOUSE(),2,i);
                                break;
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        return viewRow;
    }

    private void updateCheckUp(int id, int result, int index){
        DataClient dataClient = APIClient.getData();
        Call<String> callBack = dataClient.updateCheckUpHouse(id,result);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("success")){
                    arrItem.remove(index);
                    GridViewHouseRequestAdapter.this.notifyDataSetChanged();
                }else if (response.body().equals("fail")){
                    Toast.makeText(context, R.string.fail, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}