package com.tennis.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tennis.score.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Ali on 9/19/2018.
 */

public class TournamentList extends AppCompatActivity {

    String signedInEmail;
    LinearLayout tournamentList;

    private View.OnClickListener viewTournament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournament_list);

        tournamentList = (LinearLayout)findViewById(R.id.tournamentList);

        Intent intent = getIntent();
        signedInEmail = intent.getStringExtra("signedInEmail");

        createViewTournamentOnClickListener();
        updateTournamentList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        updateTournamentList();
    }

    public void newTournament(View view) {
        Intent intent = new Intent(view.getContext(), NewTournament.class);
        intent.putExtra("signedInEmail", signedInEmail);
        startActivityForResult(intent, 0);
    }

    private void updateTournamentList() {
        String[] params = new String[]{
                "email", signedInEmail};

        try {
            String response = new RetrieveFeedTask(
                    "https://www.mikenguyenmd.com/match_live/getTournamentList", params).execute().get();

            System.out.println(response);
            tournamentList.removeAllViews();
            if (!response.equals("null")) {
                JSONArray tournamentArray = new JSONArray(response.toString());
                for (int i = 0; i < tournamentArray.length(); i++) {
                    TextView currentTournament = new TextView(this);

                    currentTournament.setText(getTournamentDisplayString(tournamentArray.getJSONObject(i)));
                    currentTournament.setTag(tournamentArray.getJSONObject(i).getInt("tournamentId"));
                    currentTournament.setClickable(true);
                    currentTournament.setOnClickListener(viewTournament);
                    currentTournament.setTextSize(20);
                    currentTournament.setPadding(5, 5, 5, 5);

                    tournamentList.addView(currentTournament, 0);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTournamentDisplayString(JSONObject tournament) {
        try {
            return tournament.getString("tournamentName") + " " + tournament.getString("startDate") + " By: " + tournament.getString("creatorEmail");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createViewTournamentOnClickListener() {
        viewTournament = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tournamentId = (int)view.getTag();

                Intent intent = new Intent(view.getContext(), MatchList.class);
                intent.putExtra("signedInEmail", signedInEmail);
                intent.putExtra("tournamentId", tournamentId);
                startActivity(intent);
            }
        };
    }
}
