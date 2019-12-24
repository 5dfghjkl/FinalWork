package com.footprint.footprint.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.footprint.footprint.R;


/**
 * 功能:程序的自定义title
 */
public class TitleWidget extends RelativeLayout implements View.OnClickListener {

    public static final String Tag = TitleWidget.class.getSimpleName();

    private RelativeLayout rlyt_left_back;//左边返回键
    private ImageView mivBack;//左边返回键的按钮
    private RelativeLayout rlyt_left_tv_back;//左边文字显示的布局
    private TextView tv_back;;//显示的文字的布局
    public TextView tv_title;//title文字的显示
    private RelativeLayout rlyt_miv_right1; //右边第一个按钮
    private ImageView mivRight1;//右边第一个图标的显示
    private RelativeLayout rlyt_miv_right2;//右边第二个按钮
    private ImageView mivRight2;//右边第二个图标的显示
    private RelativeLayout rlyt_tv_right3;//右边第一个文字的显示
    private TextView tvExplain;//右边第一个文字的显示
    private RelativeLayout rlyt_tv_right4;//右边第二个文字的显示
    private TextView tvExplain2;//右边第二个文字的显示
    private LinearLayout rl_title;//整个title的布局
//    private View  view_stau;//电池栏的颜色
    private Context m_Context;

    private onReturnListener m_OnReturnListener;
    private onSubmitListener m_OnSubmitListener;
    private onLeftTitleListener m_onLeftTitleListener;
    private onRightMiv1Listener onRightMiv1Listener;
    private onRightMiv2Listener onRightMiv2Listener;



    private int title_miv_back;//左边返回键
    private String title_tv_back;//左边文字的显示
    private String title_text;//title文字的显示
    private int title_miv_right1;//右边第一个按钮
    private int title_miv_right2;//右边第二个按钮
    private String title_tv_right3;//右边第一个文字显示
    private String title_tv_right4;//右边第一个文字显示
    private int title_bg_color;//Title背景颜色
    private int title_stu_color;//电池缆的颜色

    public TitleWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        inintAttribut(context,attrs);
        initData(context);
    }
    /** 初始化自定义布局*/
    private void initData(Context context) {
        this.m_Context = context;
        LayoutInflater.from(m_Context).inflate(R.layout.common_widget_title,this, true);
        this.rlyt_left_back = (RelativeLayout) findViewById(R.id.rlyt_left_back);
        this.mivBack= (ImageView) findViewById(R.id.iv_back);
        this.rlyt_left_tv_back = (RelativeLayout) findViewById(R.id.rlyt_left_tv_back);
        this.tv_back = (TextView) findViewById(R.id.tv_back);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.rlyt_miv_right1 = (RelativeLayout) findViewById(R.id.rlyt_miv_right1);
        this.mivRight1= (ImageView) findViewById(R.id.iv_right1);
        this.rlyt_miv_right2 = (RelativeLayout) findViewById(R.id.rlyt_miv_right2);
        this.mivRight2= (ImageView) findViewById(R.id.iv_right2);
        this.rlyt_tv_right3 = (RelativeLayout) findViewById(R.id.rlyt_tv_right3);
        this.tvExplain = (TextView) findViewById(R.id.tv_explain);
        this.rlyt_tv_right4 = (RelativeLayout) findViewById(R.id.rlyt_tv_right4);
        this.tvExplain2 = (TextView) findViewById(R.id.tv_explain2);
        this.rlyt_left_back.setOnClickListener(this);
        this.rl_title=(LinearLayout) findViewById(R.id. rl_title);
       /* this.view_stau=(View) findViewById(R.id. view_stau);
        if (-1!=title_stu_color){
            view_stau.setBackgroundResource(title_stu_color);
        }
        if (-1!=title_bg_color){
            rl_title.setBackgroundResource(title_bg_color);
        }*/

        setTitle(title_text);
        setBackMiv(title_miv_back);
        setBackText(title_tv_back);
        setRightMiv1(title_miv_right1);
        setRightMiv2(title_miv_right2);
        setRighttv3(title_tv_right3);
        setRighttv4(title_tv_right4);
    }

    public TitleWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inintAttribut(context,attrs);
        // TODO Auto-generated constructor stub
        initData(context);
    }
/** 初始化自定义的属性*/
    private void inintAttribut(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.title_widget_layout);
        title_miv_back=a.getResourceId(R.styleable.title_widget_layout_title_miv_back,-1);
        title_tv_back=a.getString(R.styleable.title_widget_layout_title_tv_back);
        title_text=a.getString(R.styleable.title_widget_layout_title_text);
        title_miv_right1=a.getResourceId(R.styleable.title_widget_layout_title_miv_right1,-1);
        title_miv_right2=a.getResourceId(R.styleable.title_widget_layout_title_miv_right2,-1);
        title_tv_right3=a.getString(R.styleable.title_widget_layout_title_tv_right3);
        title_tv_right4=a.getString(R.styleable.title_widget_layout_title_tv_right4);
        title_bg_color= a.getResourceId(R.styleable.title_widget_layout_title_bg_color,-1);
        title_stu_color= a.getResourceId(R.styleable.title_widget_layout_title_stu_color,-1);
        a.recycle();
    }
    /**
     * 设置返回键的按钮
     *
     * @param str 必须为大写
     */
    public void setBackMiv(int str) {
        if (str!=-1) {
            this.mivBack.setBackgroundResource(str);
        }

    }
    /**
     * 设置左边文字的显示
     */
    public void setBackText(String str) {
        if (!TextUtils.isEmpty(str)){
            this.rlyt_left_tv_back.setVisibility(View.VISIBLE);
            this.rlyt_left_tv_back.setOnClickListener(this);
            this.tv_back.setText(str);
        }else{
            this.rlyt_left_tv_back.setVisibility(View.GONE);
        }
    }
    /**
     * 设置title标题
     *
     * @param str
     */
    public void setTitle(String str) {
        // TODO Auto-generated method stub
        this.tv_title.setText(str);
    }
    /**
     * 设置返回键的按钮
     *
     * @param str 必须为资源文件
     */
    public void setRightMiv1(int str) {
        if (str!=-1){
            this.rlyt_miv_right1.setVisibility(View.VISIBLE);
            this.rlyt_miv_right1.setOnClickListener(this);
            this.mivRight1.setBackgroundResource(str);
        }else{
            this.rlyt_miv_right1.setVisibility(View.GONE);
        }
    }
    /**
     * 设置返回键的按钮
     *
     * @param str 必须为资源文件
     */
    public void setRightMiv2(int str) {
        if (str!=-1){
            this.rlyt_miv_right2.setVisibility(View.VISIBLE);
            this.rlyt_miv_right2.setOnClickListener(this);
            this.mivRight1.setBackgroundResource(str);
        }else{
            this.rlyt_miv_right2.setVisibility(View.GONE);
        }
    }
    /**
     * 设置右边第一个文字
     *
     * @param str 必须为资源文件
     */
    public void setRighttv3(String str) {
        if (!TextUtils.isEmpty(str)){
            this.rlyt_tv_right3.setVisibility(View.VISIBLE);
            this.rlyt_tv_right3.setOnClickListener(this);
            this.tvExplain.setText(str);
        }else{
            this.rlyt_tv_right3.setVisibility(View.GONE);
        }
    }
    /**
     * 设置右边第二个文字
     *
     * @param str 必须为资源文件
     */
    public void setRighttv4(String str) {
        if (!TextUtils.isEmpty(str)){
            this.rlyt_tv_right4.setVisibility(View.VISIBLE);
            this.rlyt_tv_right4.setOnClickListener(this);
            this.tvExplain2.setText(str);
        }else{
            this.rlyt_tv_right4.setVisibility(View.GONE);
        }
    }
    /**
     * 是否显示左侧返回键，默认显示
     *
     * @param visibility
     */
    public void setBackVisibility(int visibility) {
        rlyt_left_back.setVisibility(visibility);
    }
    /**
     * 设置title标题栏的背景颜色
     *
     * @param color
     */
    public void setTitleBackground(int color) {
        // TODO Auto-generated method stub
        this.rl_title.setBackgroundColor(color);
    }

    public void setReturnListener(onReturnListener paramonReturnListener) {
        this.m_OnReturnListener = paramonReturnListener;
    }

    public void setSubmitListener(onSubmitListener paramonSubmitListener) {
        this.m_OnSubmitListener = paramonSubmitListener;
    }

    public void setLeftTextListener(onLeftTitleListener paramonReturnListener) {
        this.m_onLeftTitleListener = paramonReturnListener;
    }

    public void setRightMiv1Listener(onRightMiv1Listener paramonSubmitListener) {
        this.onRightMiv1Listener = paramonSubmitListener;
    }
    public void setRightMiv2Listener(onRightMiv2Listener Listener) {
        this.onRightMiv2Listener = Listener;
    }
/** 返回按钮*/
    public abstract interface onReturnListener {
        public abstract void onReturn(View paramView);
    }
    /** 最左边提示文字的监听*/
    public abstract interface onLeftTitleListener {
        public abstract void LeftTitle(View paramView);
    }
    /** 最右边第一个按钮的监听*/
    public abstract interface onRightMiv1Listener {
        public abstract void onRightMiv1(View paramView);
    }
    /** 最右边第二个按钮的监听*/
    public abstract interface onRightMiv2Listener {
        public abstract void onRightMiv2(View paramView);
    }
    /** 最右边文字的显示监听*/
    public abstract interface onSubmitListener {
        public abstract void onSubmit(View paramView);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.rlyt_left_back://返回键
                if (this.m_OnReturnListener != null) {
                    this.m_OnReturnListener.onReturn(v);
                    return;
                }
                ((Activity) this.m_Context).finish();
                return;
            case R.id.rlyt_left_tv_back://左边文字的显示
                if (this.m_onLeftTitleListener != null) {
                    this.m_onLeftTitleListener.LeftTitle(v);
                }
                return;
            case R.id.rlyt_miv_right1://右边第一个按钮
                if (this.onRightMiv1Listener != null) {
                    this.onRightMiv1Listener.onRightMiv1(v);
                }
                return;
            case R.id.rlyt_miv_right2://右边第二个按钮
                if (this.onRightMiv2Listener != null) {
                    this.onRightMiv2Listener.onRightMiv2(v);
                }
                return;
            case R.id.rlyt_tv_right3://右边最外边文字的显示
                if (this.m_OnSubmitListener != null) {
                    this.m_OnSubmitListener.onSubmit(v);
                }
            default:
                break;
        }
    }
}
