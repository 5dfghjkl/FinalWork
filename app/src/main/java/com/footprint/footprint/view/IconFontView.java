package com.footprint.footprint.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/4/13.
 */

public class IconFontView extends android.support.v7.widget.AppCompatTextView {
    public static final String FONT_PATH = "iconfont.ttf";
    public static Typeface mTypeface;
    public IconFontView(Context context) {
        this(context, null);
    }

    public IconFontView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconFontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        if(mTypeface == null){
            mTypeface = Typeface.createFromAsset(getContext().getAssets(), FONT_PATH);
        }
        setTypeface(mTypeface);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        StringBuffer sb=new StringBuffer();
        if (text.length()==4){

            sb.append("\\u");
            sb.append(text.toString());
        }else{
            sb.append(text.toString());
        }
        super.setText(decode2(sb.toString()),  type);
    }

    public static String decode2(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '\\' && chars[i + 1] == 'u') {
                char cc = 0;
                for (int j = 0; j < 4; j++) {
                    char ch = Character.toLowerCase(chars[i + 2 + j]);
                    if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
                        cc |= (Character.digit(ch, 16) << (3 - j) * 4);
                    } else {
                        cc = 0;
                        break;
                    }
                }
                if (cc > 0) {
                    i += 5;
                    sb.append(cc);
                    continue;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
