package com.footprint.footprint.dialog;

import android.app.Dialog;
import android.content.Context;

public class MyDialog extends Dialog {

	public MyDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	protected MyDialog(Context context, boolean cancelable,
                       OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public MyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
}
