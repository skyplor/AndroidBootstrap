package com.skypayjm.androidbootstrap.view;

/**
 * Created by sky on 3/11/15.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.skypayjm.androidbootstrap.R;
import com.skypayjm.androidbootstrap.util.FontManager;
import com.skypayjm.androidbootstrap.util.Utility;

/**
 * TextView subclass which allows the user to define a truetype font file to use as the view's typeface.
 */
public class FontText extends TextView {

    private float spacing = Spacing.NORMAL;
    private CharSequence originalText = "";

    public FontText(Context context) {
        this(context, null);
    }

    public FontText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (isInEditMode())
            return;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontText);
        if (ta != null) {
            String fontAsset = ta.getString(R.styleable.FontText_typefaceAsset);

            if (!Utility.isEmpty(fontAsset)) {
                Typeface tf = FontManager.getInstance().getFont(fontAsset);
                int style = Typeface.NORMAL;
                float size = getTextSize();

                if (getTypeface() != null)
                    style = getTypeface().getStyle();

                if (tf != null)
                    setTypeface(tf, style);
                else
                    Log.d("FontText", String.format("Could not create a font from asset: %s", fontAsset));
            }
            ta.recycle();
        }
    }

    public float getSpacing() {
        return this.spacing;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
        applySpacing();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        super.setText(text, type);
    }

//    public void setText(CharSequence text, BufferType type, boolean applySpacing) {
//        if(!applySpacing) {
//            super.setText(text, type);
//        } else {
//            setText(text, type);
//        }
//    }

    // TODO add a helper method to return index of a search string by appending \u0A00 to each character first then do the search. This is for the case when we want to apply spacing but also want to have part of the string with a different color

    @Override
    public CharSequence getText() {
        return originalText;
    }

    private void applySpacing() {
        if (this == null || this.originalText == null) return;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < originalText.length(); i++) {
            builder.append(originalText.charAt(i));
            if (i + 1 < originalText.length()) {
                builder.append("\u00A0");
            }
        }
        SpannableString finalText = new SpannableString(builder.toString());
        if (builder.toString().length() > 1) {
            for (int i = 1; i < builder.toString().length(); i += 2) {
                finalText.setSpan(new ScaleXSpan((spacing + 1) / 10), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        super.setText(finalText, BufferType.SPANNABLE);
    }

    public class Spacing {
        public final static float NORMAL = 0;
    }

    public void setFont(String typeface){
        setTypeface(FontManager.getInstance().getFont(typeface));
    }
}
