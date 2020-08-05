package com.MuffinLabs.kiculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class CreditActivity extends AppCompatActivity {

    private Context mContext;

    private ImageButton ibtn_back;

    private ButtonTouchEffect buttonTouchEffect;

    private ScrollView sv_credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        setContext();
        initView();
        initInstance();
        eventListener();
        setOverScrollMode();
    }

    private void setContext() {
        mContext = CreditActivity.this;
    }

    private void initView() {
        ibtn_back = findViewById(R.id.ibtn_back);
        sv_credit = findViewById(R.id.sv_credit);
    }

    private void initInstance() {
        buttonTouchEffect = new ButtonTouchEffect(mContext);
    }

    private void eventListener() {
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonTouchEffect.setTouchEffect(ibtn_back, R.drawable.floating_button_clicked_layout, R.drawable.floating_button_layout);
    }

    private void setOverScrollMode() {
        OverScrollDecoratorHelper.setUpOverScroll(sv_credit);
    }

}