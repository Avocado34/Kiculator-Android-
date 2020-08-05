package com.MuffinLabs.kiculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedInstanceAdapter extends RecyclerView.Adapter<SavedInstanceAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SavedInstanceCls> instanceList;

    SavedInstanceAdapter(Context context, ArrayList<SavedInstanceCls> instanceList) {
        this.mContext = context;
        this.instanceList = instanceList;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout saved_instance_item_view;
        private TextView tv_app_name, tv_base_fee, tv_fee_per_min;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            saved_instance_item_view = itemView.findViewById(R.id.saved_instance_item_view);
            tv_app_name = itemView.findViewById(R.id.tv_app_name);
            tv_base_fee = itemView.findViewById(R.id.tv_base_fee);
            tv_fee_per_min = itemView.findViewById(R.id.tv_fee_per_min);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.saved_instance_item, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_app_name.setText(instanceList.get(position).getAppName());
        holder.tv_base_fee.setText(instanceList.get(position).getBaseFee());
        holder.tv_fee_per_min.setText(instanceList.get(position).getFeePerMin());

        holder.saved_instance_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("appName", instanceList.get(position).getAppName());
                resultIntent.putExtra("baseFee", instanceList.get(position).getBaseFee());
                resultIntent.putExtra("baseTime", instanceList.get(position).getBaseTime());
                resultIntent.putExtra("feePerMin", instanceList.get(position).getFeePerMin());

                ((SavedInstanceActivity)mContext).setResult(Activity.RESULT_OK, resultIntent);
                ((SavedInstanceActivity)mContext).finish();
            }
        });

        holder.saved_instance_item_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        view.setBackgroundColor(Color.parseColor("#D1D1D1"));
                        break;
                    case MotionEvent.ACTION_UP:
                        view.setBackgroundColor(Color.parseColor("#FAFAFA"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        view.setBackgroundColor(Color.parseColor("#FAFAFA"));
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (instanceList != null) {
            return instanceList.size();
        } else {
            return 0;
        }
    }

}
