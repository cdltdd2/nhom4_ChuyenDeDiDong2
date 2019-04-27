//TOÀN: ADAPTER CHỨA DỮ LIỆU Ở ADMIN
package com.example.quang.studenthousing.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.fragment.ManagerPostFragment;
import com.example.quang.studenthousing.fragment.ManagerUserFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    private Context context;

    public FragmentAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ManagerPostFragment();
            case 1:
                return new ManagerUserFragment();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        //CHO BIẾT ĐANG Ở VỊ TRÍ CỦA TAB NÀO
        if (position == 0){
            return context.getString(R.string.post);
        }else if (position == 1){
            return context.getString(R.string.user);
        }
        return null;
    }

}