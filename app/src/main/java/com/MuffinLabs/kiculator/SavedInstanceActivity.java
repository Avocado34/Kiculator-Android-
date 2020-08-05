package com.MuffinLabs.kiculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class SavedInstanceActivity extends AppCompatActivity {

    private Context mContext;

    private RelativeLayout rel_saved_instance_view;
    private ScrollView sv_saved_instance;

    private ImageButton ibtn_back;
    private EditText edt_app_name, edt_base_time, edt_base_fee, edt_fee_per_min;
    private RecyclerView rv_saved_instance;
    private Button btn_save_instance;

    private ArrayList<SavedInstanceCls> instanceList;
    private SavedInstanceAdapter savedInstanceAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SavedInstanceDBHelper savedInstanceDBHelper;

    private ButtonTouchEffect buttonTouchEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_instance);

        setStatusBarIconDark();

        initContext();
        initView();
        initInstance();
        eventListener();

        setDataFromDB();
        setOverScrollMode();

        setSwipeMode();

        setButtonTouchEffect();
    }

    private void setStatusBarIconDark(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void initContext() {
        mContext = SavedInstanceActivity.this;
    }

    private void initView() {
        rel_saved_instance_view = findViewById(R.id.rel_saved_instance_view);
        sv_saved_instance = findViewById(R.id.sv_saved_instance);

        ibtn_back = findViewById(R.id.ibtn_back);

        edt_app_name = findViewById(R.id.edt_app_name);
        edt_base_time = findViewById(R.id.edt_base_time);
        edt_base_fee = findViewById(R.id.edt_base_fee);
        edt_fee_per_min = findViewById(R.id.edt_fee_per_min);

        rv_saved_instance = findViewById(R.id.rv_saved_instance);

        btn_save_instance = findViewById(R.id.btn_save_instance);
    }

    private void initInstance() {
//         set Saved Instance RecyclerView
        instanceList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        savedInstanceAdapter = new SavedInstanceAdapter(mContext, instanceList);
        rv_saved_instance.setLayoutManager(layoutManager);
        rv_saved_instance.setAdapter(savedInstanceAdapter);
        // END

        savedInstanceDBHelper = new SavedInstanceDBHelper(mContext);

        buttonTouchEffect = new ButtonTouchEffect(mContext);
    }

    private void eventListener() {
        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_save_instance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_app_name.getText().toString().trim().equals("") &&
                        !edt_base_time.getText().toString().trim().equals("") &&
                        !edt_base_fee.getText().toString().trim().equals("") &&
                        !edt_fee_per_min.getText().toString().trim().equals("")) {

                    if (savedInstanceDBHelper.isExists(edt_app_name.getText().toString().trim())){
                        Toast.makeText(mContext, "이미 존재하는 항목입니다", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    boolean isInserted = savedInstanceDBHelper.insertData(edt_app_name.getText().toString().trim(), edt_base_fee.getText().toString().trim(),
                            edt_base_time.getText().toString(), edt_fee_per_min.getText().toString());

                    if (isInserted){
                        setDataFromDB();
                        savedInstanceAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(mContext, "다시 시도하여 주시기 바랍니다", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(mContext, "조건을 모두 기입하여 주시기 바랍니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rel_saved_instance_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    InputMethodManager inputMethodManager = (InputMethodManager)mContext.getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setOverScrollMode(){
        OverScrollDecoratorHelper.setUpOverScroll(rv_saved_instance, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        OverScrollDecoratorHelper.setUpOverScroll(sv_saved_instance);
    }

    private void setSwipeMode(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                savedInstanceDBHelper.deleteData(instanceList.get(position).getAppName());
                instanceList.remove(position);
                savedInstanceAdapter.notifyItemRemoved(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rv_saved_instance);
    }

    private void setButtonTouchEffect(){
        buttonTouchEffect.setTouchEffect(btn_save_instance, R.drawable.basic_button_clicked_layout, R.drawable.basic_button_layout);
        buttonTouchEffect.setTouchEffect(ibtn_back, R.drawable.floating_button_clicked_layout, R.drawable.floating_button_layout);
    }

    private void setDataFromDB() {
        instanceList.clear();
        Cursor cursor = savedInstanceDBHelper.getAllData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                instanceList.add(new SavedInstanceCls(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
                savedInstanceAdapter.notifyDataSetChanged();
            }
        }
    }
}