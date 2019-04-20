package com.example.quang.studenthousing.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.object.UrlPhoto;
import com.example.quang.studenthousing.services.APIClient;

import java.util.ArrayList;

public class SlidingPhotoAdapter extends PagerAdapter{
    Context context;
    ArrayList<UrlPhoto> images;
    LayoutInflater layoutInflater;


    public SlidingPhotoAdapter(Context context, ArrayList<UrlPhoto> images) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_slide_photo, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.itemPhotoSlide);
        Glide.with(context).load(APIClient.BASE_URL+images.get(position).getURL()).into(imageView);

        container.addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}