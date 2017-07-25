package com.example.eze.restapi;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.myTextView);
        output.setMovementMethod(new ScrollingMovementMethod());

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== R.id.setting_id){
            if (isOnLine()){
                requestData("http://services.hanselandpetal.com/feeds/flowers.xml");
            }else{
                Toast.makeText(this, "Network is'nt Available", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    public void updateDisplay(String message){
        output.append(message + "\n");
    }

    //checking if internet is on
    public boolean isOnLine(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    private  class MyTask extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            updateDisplay("Starting Task");

            if(tasks.size() == 0){
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpUrlManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            updateDisplay(s);
            tasks.remove(this);
            if(tasks.size() == 0){
                pb.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            updateDisplay(values[0]);
        }
    }
}
