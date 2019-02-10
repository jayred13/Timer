package edu.utep.cs.cs4330.timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TimerModel timerModel;
    private TextView timerDisplay;
    private Button startButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerDisplay = findViewById(R.id.timeDisplay);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        timerModel = new TimerModel();
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("ID", "1234");
        super.onSaveInstanceState(outState);
    }

    // called when activity is re-recreated, following onCreate().
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String id = savedInstanceState.getString("ID");
    }

    private void displayTime(){
        long time = timerModel.elapsedTime();
        int sec = (int)time % 60;
        int min = (int)time / 60;
        min = min % 60;
        int hour = min / 60;
        timerDisplay.setText(String.format("%d:%02d:%02d", hour, min, sec));
    }

    public void startClicked(View view){
        timerModel.start();
        new Thread(() -> {
            while(timerModel.isRunning()){
                this.runOnUiThread(this::displayTime);
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e ){}
            }
        }).start();
        Toast.makeText(this, "Start Tapped!", Toast.LENGTH_SHORT).show();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void stopClicked(View view){
        timerModel.stop();
        Toast.makeText(this, "Stop Tapped!", Toast.LENGTH_SHORT).show();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }
}