package com.example.quang.studenthousing.object;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.quang.studenthousing.R;

@SuppressLint("AppCompatCustomView")
public class CustomEditTextLocation extends EditText {

    private Drawable btn_clear = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_done_location, null);
    private OnChangeLocation onChangeLocation;

    public CustomEditTextLocation(Context context) {
        super(context);
        init(context);
    }

    public CustomEditTextLocation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CustomEditTextLocation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    void init(Context context) {

    }


//    //intercept Typeface change and set it with our custom font
//    /*public void setTypeface(Typeface tf, int style) {
//        if (style == Typeface.BOLD) {
//            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Vegur-B 0.602.otf"));
//        } else {
//            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Vegur-R 0.602.otf"));
//        }
//    }*/
}