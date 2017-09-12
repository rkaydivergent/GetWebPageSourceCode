package com.example.pc.getwebpagesourcecode;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner mySpinner;
    static String option;
    EditText inputTxt;
    TextView outputSc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySpinner = (Spinner) findViewById(R.id.mySpinner);

        ArrayAdapter adapter= ArrayAdapter.createFromResource(this, R.array.type, R.layout.myspinner_layout);
        adapter.setDropDownViewResource(R.layout.myspinner_layout);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(this);

        inputTxt= (EditText) findViewById(R.id.inputText);
        outputSc= (TextView)findViewById(R.id.source_code);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        option = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getSourceCode(View view) {
        String queryString = option+ inputTxt.getText().toString() ;

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
           new GetSourceCodeClass(this, outputSc, inputTxt).execute(queryString);
        }

        else {
            if (queryString.length() == 0) {
                outputSc.setText(R.string.no_text);
            } else {
                outputSc.setText(R.string.no_net);

            }
        }
    }
    }

