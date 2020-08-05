package com.MuffinLabs.kiculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    public static final int REQUEST_CODE = 1001;

    private RelativeLayout rel_main_view;
    private ScrollView sv_main;
    private ImageButton ibtn_saved_instance, ibtn_menu;
    private TextView tv_app_name;
    private EditText edt_base_fee, edt_fee_per_min, edt_ride_time, edt_base_time;
    private TextView tv_final_fee;
    private Button btn_start_calculate;

    private ButtonTouchEffect buttonTouchEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setStatusBarIconDark();

        initContext();
        initView();
        initInstance();
        eventListener();

        setButtonTouchEffect();
        setOverScrollMode();

        hideSoftwareKeyboard(500);
    }

    private void setStatusBarIconDark(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void initContext(){
        mContext = MainActivity.this;
    }

    private void initView() {
        rel_main_view = findViewById(R.id.rel_main_view);
        sv_main = findViewById(R.id.sv_main);

        ibtn_saved_instance = findViewById(R.id.ibtn_saved_instance);
        tv_app_name = findViewById(R.id.tv_app_name);
        ibtn_menu = findViewById(R.id.ibtn_menu);

        edt_base_fee = findViewById(R.id.edt_base_fee);
        edt_fee_per_min = findViewById(R.id.edt_fee_per_min);
        edt_ride_time = findViewById(R.id.edt_ride_time);
        edt_base_time = findViewById(R.id.edt_base_time);
        tv_final_fee = findViewById(R.id.tv_final_fee);
        btn_start_calculate = findViewById(R.id.btn_start_calculate);
    }

    private void initInstance() {
        buttonTouchEffect = new ButtonTouchEffect(mContext);
    }

    private void eventListener() {
        ibtn_saved_instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SavedInstanceActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        ibtn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CreditActivity.class);
                startActivity(intent);
            }
        });

        btn_start_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (!edt_base_fee.getText().toString().trim().equals("") &&
                            !edt_fee_per_min.getText().toString().trim().equals("") &&
                            !edt_ride_time.getText().toString().trim().equals("") &&
                            !edt_base_time.getText().toString().trim().equals("")) {
                        int baseFee = Integer.parseInt(edt_base_fee.getText().toString().trim());
                        int feePerMin = Integer.parseInt(edt_fee_per_min.getText().toString().trim());
                        int rideTime = Integer.parseInt(edt_ride_time.getText().toString().trim());
                        int baseTime = Integer.parseInt(edt_base_time.getText().toString().trim());

                        if (baseTime < rideTime){
                            int finalFee = (rideTime - baseTime) * feePerMin + baseFee;
                            tv_final_fee.setText(finalFee +"");
                        }else{
                            tv_final_fee.setText(baseFee + "");
                        }

                        hideSoftwareKeyboard(0);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sv_main.fullScroll(View.FOCUS_DOWN);
                            }
                        }, 140);
                    }else{
                        Toast.makeText(mContext, "조건을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "다시 시도하여 주시기 바랍니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rel_main_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftwareKeyboard(0);
            }
        });

        editTextChangeListener(edt_base_fee);
        editTextChangeListener(edt_base_time);
        editTextChangeListener(edt_fee_per_min);
    }

    private void editTextChangeListener(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_app_name.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setButtonTouchEffect(){
        try{
            buttonTouchEffect.setTouchEffect(btn_start_calculate, R.drawable.basic_button_clicked_layout, R.drawable.basic_button_layout);
            buttonTouchEffect.setTouchEffect(ibtn_saved_instance, R.drawable.floating_button_clicked_layout, R.drawable.floating_button_layout);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setOverScrollMode(){
        OverScrollDecoratorHelper.setUpOverScroll(sv_main);
    }


    private void hideSoftwareKeyboard(long millyTime){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    InputMethodManager inputManager = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, millyTime);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SavedInstance Result
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            edt_base_fee.setText(data.getExtras().getString("baseFee"));
            edt_base_time.setText(data.getExtras().getString("baseTime"));
            edt_fee_per_min.setText(data.getExtras().getString("feePerMin"));
            tv_app_name.setText(data.getExtras().getString("appName"));
            tv_app_name.setVisibility(View.VISIBLE);
        }
    }
}