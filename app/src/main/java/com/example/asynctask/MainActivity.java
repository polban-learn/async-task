package com.example.asynctask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asynctask.R;

import java.util.Random;

public class MainActivity extends Activity {
    private Button btn;
    private ProgressBar progressBar;
    private TextView txt, txt2;
    private Integer count =1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txt2 = findViewById(R.id.textView);
        progressBar.setMax(10);
        btn = (Button) findViewById(R.id.button);
        progressBar.setVisibility(View.GONE);
        //btn.setText("Start");
        txt = (TextView) findViewById(R.id.textView1);
        OnClickListener listener = new OnClickListener() {
            public void onClick(View view) {
                count =1;
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                switch (view.getId()) {
                    case R.id.button:
                        new SimpleAsyncTask().execute(10);
                        break;
                }
            }
        };
        btn.setOnClickListener(listener);
    }

    public class SimpleAsyncTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            // Generate a random number between 0 and 10
            Random r = new Random();
            int n = r.nextInt(11);

            // Make the task take long enough that we have
            // time to rotate the phone while it is running
            int s = n * 200;

            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(s);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Awake at last after sleeping for " + s + " milliseconds!";
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            txt2.setVisibility(View.GONE);
            txt.setText(result);
            btn.setText("Restart");
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.GONE);
            txt.setText("Napping...");
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(Integer... values) {
            txt2.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            btn.setText("Stop");
            txt2.setText("Progress..."+ values[0] + "0%");
            progressBar.setProgress(values[0]);
        }
    }
} 