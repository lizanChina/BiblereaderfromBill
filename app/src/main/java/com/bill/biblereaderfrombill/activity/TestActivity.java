package com.bill.biblereaderfrombill.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.bill.biblereaderfrombill.R;
import com.bill.common.Logger;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Logger.d("TestActivity onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.txt_testactivity_layout);
        Button button = (Button) findViewById(R.id.txt_testactivity_loadbook);
        final EditText editText = (EditText) findViewById(R.id.txt_testactivity_edittext);
        String path = "/data/biblereader/txt" + "/test2.txt";
        editText.setText(path);

        Logger.d("load path is: " + path);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("bookname", "testbookname");
                intent.putExtra("bookpath", editText.getText().toString());
                intent.setClass(TestActivity.this, BookPlayActivity.class);
                TestActivity.this.startActivity(intent);

            }
        });
    }

}
