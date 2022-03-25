package com.mizael.jeudupendu.tools;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;

import com.mizael.jeudupendu.R;

public class CustomPopup extends Dialog {
    private TextView title, content;
    private Button positive, negative;

    public CustomPopup(Activity activity){
        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.custom_popup);

        this.title = findViewById(R.id.textview_title);
        this.content = findViewById(R.id.textview_content);
        this.positive = findViewById(R.id.button_positive);
        this.negative = findViewById(R.id.button_negative);



    }


    public TextView getTitle() { return this.title; }

    public TextView getContent(){ return this.content; }

    public Button getPositive(){ return this.positive; }

    public Button getNegative(){ return this.negative; }

    public void build(){
        show();
    }
}
