package com.MuffinLabs.kiculator;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public class ButtonTouchEffect {

    private Context mContext;

    ButtonTouchEffect(Context context){
        this.mContext = context;
    }

    public void setTouchEffect(View view, final int actionDownLayout, final int actionUpLayout){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        view.setBackgroundResource(actionDownLayout);
                        break;
                    case MotionEvent.ACTION_UP:
                        view.setBackgroundResource(actionUpLayout);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        view.setBackgroundResource(actionUpLayout);
                        break;
                }
                return false;
            }
        });
    }
}
