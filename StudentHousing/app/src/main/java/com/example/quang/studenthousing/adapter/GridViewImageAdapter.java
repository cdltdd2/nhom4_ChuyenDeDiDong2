//LỊNH: DSACH HINH ẢNH BÀI ĐĂNG DẠNG LƯỚI
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
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        if (holder != null) {
            Glide.with(context).load(APIClient.BASE_URL+item).into(holder.imView);
        }

        holder.imRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataClient dataClient = APIClient.getData();
                Call<String> callback = dataClient.removeImage(arrItem.get(i));
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("success")){

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        //Toast.makeText(CreatePostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                arrItem.remove(i);
                GridViewImageAdapter.this.notifyDataSetChanged();
            }
        });

        return viewRow;
    }
}