package ru.myapp.zxing3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

public class MainActivity extends AppCompatActivity {

    private static final String RESULT = "result";

    private TextView textViewResult;
    private Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = (TextView) findViewById(R.id.result);
        scanButton = (Button) findViewById(R.id.btn_scan);
        scanButton.setOnClickListener(getOnClickListener());
    }

    @NonNull
    private View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ZxingOrient(MainActivity.this).initiateScan();
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ZxingOrientResult scanResult = ZxingOrient.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            textViewResult.setText(scanResult.getContents());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RESULT, (String) textViewResult.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textViewResult.setText(savedInstanceState.getString(RESULT));
    }
}
