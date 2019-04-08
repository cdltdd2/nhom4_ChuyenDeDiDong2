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

        btn_clear.setBounds(0, 0, btn_clear.getIntrinsicWidth(), btn_clear.getIntrinsicHeight());
        final CustomEditTextLocation editText = CustomEditTextLocation.this;

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editText.getCompoundDrawables()[2] == null)
                    return false;

                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;

                if (event.getX() > editText.getWidth() - editText.getPaddingRight() - btn_clear.getIntrinsicWidth()) {
                    onChangeLocation.changeLocation(editText.getText().toString());
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], null, editText.getCompoundDrawables()[3]);
                }
                return false;
            }
        });

        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!"".equals(editText.getText().toString())){
                        editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], btn_clear, editText.getCompoundDrawables()[3]);
                    }
                }else {
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], null, editText.getCompoundDrawables()[3]);
                }
            }
        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getText().toString().equals("")) {
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], null, editText.getCompoundDrawables()[3]);
                }
                else {
                    editText.setCompoundDrawables(editText.getCompoundDrawables()[0], editText.getCompoundDrawables()[1], btn_clear, editText.getCompoundDrawables()[3]);
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
    }

    public void setOnChangedLocationListener(OnChangeLocation listener){
        onChangeLocation = listener;
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