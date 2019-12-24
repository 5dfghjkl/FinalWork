package com.footprint.footprint.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.footprint.footprint.R;
import com.footprint.footprint.utils.DensityUtil;
import com.footprint.footprint.utils.ScreenSizeUtils;

public class CommonDialog {

	/** 默认提示弹出框的监听*/
	public static void showDialog(Activity activity, String title , String titleDesc, final OnDialogClickListener onDialogClickListener){
		AlertDialog dialog = new AlertDialog.Builder(activity)
				.setTitle(title)
				.setMessage(titleDesc)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
								onDialogClickListener.onSureClick();

					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						dialog=null;
					}
				})
				.create();
		dialog.show();
	}
	/**
	 *照相，选取照片
	 *
	 * @param ctx
	 */
	public  void showShowPicDialog(final Activity ctx, final String Title, String check1, String check2) {
			final Dialog m_Dialog = new MyDialog(ctx, R.style.dialogdialog);

		Window window = m_Dialog.getWindow();
		window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
//		window.setWindowAnimations(R.style.dialoganimationstyle); // 添加动画
		View view = LayoutInflater.from(ctx).inflate(
				R.layout.dialog_layout_phone_app, null);
		AppCompatTextView callPhone =view
				.findViewById(R.id.actv_call_phone);
		AppCompatTextView sendMessage =  view
				.findViewById(R.id.actv_send_message);
		AppCompatTextView copyNumber =  view
				.findViewById(R.id.actv_copy_phonenumber);
		callPhone.setText(check1);
		copyNumber.setText(check2);
		sendMessage.setVisibility(View.GONE);

		int width = ScreenSizeUtils.getWidth(ctx);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width - DensityUtil.dip2px(ctx,24),
				LinearLayout.LayoutParams.WRAP_CONTENT);
		m_Dialog.setContentView(view, layoutParams);
		m_Dialog.show();
		m_Dialog.setCanceledOnTouchOutside(true);

		callPhone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (m_OnShowPicClickListener!=null){
					m_OnShowPicClickListener.onSureClick1();
				}
				m_Dialog.dismiss();
			}
		});
		copyNumber.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (m_OnShowPicClickListener!=null){
					m_OnShowPicClickListener.onSureClick2();
				}
				m_Dialog.dismiss();
			}
		});
	}


   private static OnDialogClickListener m_OnDialogClickListener;
	public void setOnDialogClickListener(OnDialogClickListener Listener) {
		this.m_OnDialogClickListener = Listener;
	}
	/** 确定按钮监听*/
	public abstract interface  OnDialogClickListener {
		public abstract void onSureClick();
	}


	private static OnShowPicClickListener m_OnShowPicClickListener;
	public void setOnShowPicClickListener(OnShowPicClickListener Listener) {
		this.m_OnShowPicClickListener = Listener;
	}
	/** 图片选择确定按钮监听*/
	public abstract interface  OnShowPicClickListener {
		public abstract void onSureClick1();
		public abstract void onSureClick2();
	}

	private static AlertDialog m_ExitLogInDialog;
	/**
	 *退出登录
	 *
	 * @param ctx
	 */
	public static void showExitLogInDialog(final Context ctx, final String Title, String check1, DialogInterface.OnClickListener onClickListener) {
		if (m_ExitLogInDialog == null){
			synchronized (CommonDialog.class) {
				if (m_ExitLogInDialog == null) {
					m_ExitLogInDialog = new AlertDialog.Builder(ctx)
							.setTitle(Title)
							.setMessage(check1)
							.setPositiveButton("确定", onClickListener)
							.create();
					m_ExitLogInDialog.show();
				}
			}
		}
		if (m_ExitLogInDialog!=null){
			if (m_ExitLogInDialog.isShowing()){
			}else {
				m_ExitLogInDialog.show();
			}
		}
	}
	public static  void disMissDialog(){
		if (m_ExitLogInDialog!=null){
			m_ExitLogInDialog.dismiss();
			m_ExitLogInDialog=null;
		}
	}
}
