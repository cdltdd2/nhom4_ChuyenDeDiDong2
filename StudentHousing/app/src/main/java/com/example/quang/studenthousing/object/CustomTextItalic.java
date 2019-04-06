package com.example.quang.studenthousing.object;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class CustomTextItalic extends TextView {
    public CustomTextItalic(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomTextItalic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomTextItalic(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-HeavyItalic.ttf");
        setTypeface(custom_font);
    }
}
