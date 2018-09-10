package com.tennis.score.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tennis.score.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TennisScore extends AppCompatActivity {

    private File file;
    private String fileName = "matchData";

    private View.OnClickListener viewMatch;

    LinearLayout matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tennis_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        matchList = (LinearLayout)findViewById(R.id.matchList);

        createMatchDataFile();
        createViewMatchOnClickListener();
        updateMatchList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        updateMatchList();
    }

    public void newMatch(View view) {
        Intent intent = new Intent(view.getContext(), NewMatch.class);
        startActivityForResult(intent, 0);
    }

    private void createMatchDataFile() {
        file = new File(getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void createViewMatchOnClickListener() {
        viewMatch = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lineToGet = (int)view.getTag();
                try {
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader chapterReader = new InputStreamReader(fis);
                    BufferedReader buffReader = new BufferedReader(chapterReader);

                    String line = buffReader.readLine();
                    int counter = 0;
                    while (line != null) {
                        if (counter == lineToGet) {
                            String[] lineSplit = line.split(",");

                            Intent intent = new Intent(view.getContext(), PreMatchSetup.class);
                            intent.putExtra("tournamentName", lineSplit[0]);
                            intent.putExtra("date", lineSplit[1]);
                            intent.putExtra("playerOneName", lineSplit[2]);
                            intent.putExtra("playerOneFrom", lineSplit[3]);
                            intent.putExtra("playerTwoName", lineSplit[4]);
                            intent.putExtra("playerTwoFrom", lineSplit[5]);
                            intent.putExtra("round", lineSplit[6]);
                            intent.putExtra("division", lineSplit[7]);
                            intent.putExtra("matchFormat", lineSplit[8]);
                            intent.putExtra("adRule", lineSplit[9]);
                            intent.putExtra("referee", lineSplit[10]);
                            startActivity(intent);

                            break;
                        }

                        line = buffReader.readLine();
                        counter++;
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void updateMatchList() {
        file = new File(getFilesDir(), fileName);

        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader chapterReader = new InputStreamReader(fis);
            BufferedReader buffReader = new BufferedReader(chapterReader);

            String line = buffReader.readLine();
            int counter = 0;
            matchList.removeAllViews();
            while (line != null) {
                TextView currentMatch = new TextView(this);

                currentMatch.setText(getMatchDisplayString(line));
                currentMatch.setTag(counter);
                currentMatch.setClickable(true);
                currentMatch.setOnClickListener(viewMatch);
                currentMatch.setTextSize(30);
                currentMatch.setPadding(5, 5, 5, 5);

                matchList.addView(currentMatch, 0);

                line = buffReader.readLine();
                counter++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getMatchDisplayString(String line) {
        String[] lineValues = line.split(",");
        String tournamentName = lineValues[0];
        String playerOne = lineValues[2];
        String playerTwo = lineValues[4];

        return tournamentName + ": " + playerOne + " Vs. " + playerTwo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tennis_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
