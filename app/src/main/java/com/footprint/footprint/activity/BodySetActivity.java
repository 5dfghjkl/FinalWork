package com.footprint.footprint.activity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.footprint.footprint.R;
import com.footprint.footprint.base.BaseActivity;

public class BodySetActivity extends BaseActivity {
    private AppCompatEditText  acet1,acet2,acet5;
    private AppCompatTextView acet3,acet4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);
        inintView();
    }

    private void inintView() {
        acet1= (AppCompatEditText) findViewById(R.id.acet1);
        acet2= (AppCompatEditText) findViewById(R.id.acet2);
        acet3= (AppCompatTextView) findViewById(R.id.acet3);
        acet5= (AppCompatEditText) findViewById(R.id.acet5);

        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=acet1.getText().toString().trim();
                String s2=acet2.getText().toString().trim();
                String s3=acet5.getText().toString().trim();

                if (TextUtils.isEmpty(s1)||TextUtils.isEmpty(s2)){
                    Toast.makeText(BodySetActivity.this, "Please fill in the complete information", Toast.LENGTH_SHORT).show();
                }else{
                    acet3.setText((Double.parseDouble(s1)*Double.parseDouble(s2)/60*30/Double.parseDouble(s3))+"(cal)");
                }


            }
        });
    }
}
