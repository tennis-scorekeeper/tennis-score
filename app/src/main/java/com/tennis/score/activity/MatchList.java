package com.tennis.score.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tennis.score.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Ali on 9/19/2018.
 */

public class MatchList extends AppCompatActivity {

    private final int blackColor = 0xff000000;
    private final int redColor = 0xffcc0000;
    private final int greenColor = 0xff99cc00;

    String signedInEmail;
    int tournamentId;
    String tournamentName;
    JSONObject tournamentObject;

    private View.OnClickListener viewMatch;

    LinearLayout matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_list);

        matchList = (LinearLayout)findViewById(R.id.matchList);

        Intent intent = getIntent();
        signedInEmail = intent.getStringExtra("signedInEmail");
        tournamentId = intent.getIntExtra("tournamentId", -1);

        setTournamentObject();
        setTournamentName();
        hideOptions();
        createViewMatchOnClickListener();
        updateMatchList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        updateMatchList();
    }

    public void newMatch(View view) {
        Intent intent = new Intent(view.getContext(), NewMatch.class);
        intent.putExtra("signedInEmail", signedInEmail);
        intent.putExtra("tournamentId", tournamentId);
        startActivityForResult(intent, 0);
    }

    private void createViewMatchOnClickListener() {
        viewMatch = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int matchId = (int)view.getTag();
                Intent intent = new Intent(view.getContext(), PreMatchSetup.class);
                intent.putExtra("signedInEmail", signedInEmail);
                intent.putExtra("tournamentId", tournamentId);
                intent.putExtra("matchId", matchId);
                intent.putExtra("tournamentName", tournamentName);
                startActivity(intent);
            }
        };
    }

    public void deleteMatches() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete all your matches?").setTitle("Test");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.show();
    }

    public void addUserToTournament() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add chair umpire");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final TextView resultMessage = new TextView(this);
        final TextView inputLabel = new TextView(this);
        final EditText input = new EditText(this);

        resultMessage.setText("");
        resultMessage.setGravity(1);
        resultMessage.setPadding(0,25,0,25);

        inputLabel.setText("Enter email of chair umpire");
        inputLabel.setTextColor(blackColor);

        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        Button addButton = new Button(this);
        addButton.setText("Add");

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String emailToAdd = input.getText().toString();

                if (emailToAdd.length() <= 0) {
                    resultMessage.setText("Please enter an email.");
                    resultMessage.setTextColor(redColor);
                    return;
                }

                try {
                    String response = new RetrieveFeedTask(
                            "https://www.mikenguyenmd.com/match_live/addUserTournamentMapping",
                            new String[]{"email", emailToAdd, "tournamentId", String.valueOf(tournamentId)}).execute().get();
                    if (response.equals("0")) {
                        resultMessage.setText(emailToAdd + " added to this tournament");
                        resultMessage.setTextColor(greenColor);
                        return;
                    }
                    if (response.equals("1")) {
                        resultMessage.setText("User with email " + emailToAdd + " does not exist");
                        resultMessage.setTextColor(redColor);
                        return;
                    }
                    else if (response.equals("2")) {
                        resultMessage.setText(emailToAdd + " is already added to this tournament");
                        resultMessage.setTextColor(redColor);
                        return;
                    }
                    else {
                        resultMessage.setText("An error occured while adding user.");
                        resultMessage.setTextColor(redColor);
                    }


                }
                catch (Exception e) {
                    e.printStackTrace();
                    resultMessage.setText("An error occured while adding user.");
                    resultMessage.setTextColor(redColor);
                }
            }
        });

        layout.addView(resultMessage);
        layout.addView(inputLabel);
        layout.addView(input);
        layout.addView(addButton);
        builder.setView(layout);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.show();
    }

    private void updateMatchList() {
        String[] params = new String[]{
                "tournamentId", String.valueOf(tournamentId)};

        try {
            String response = new RetrieveFeedTask(
                    "https://www.mikenguyenmd.com/match_live/getMatchList", params).execute().get();

            System.out.println(response);
            matchList.removeAllViews();
            if (!response.equals("null")) {
                JSONArray matchArray = new JSONArray(response.toString());
                for (int i = 0; i < matchArray.length(); i++) {
                    TextView currentTournament = new TextView(this);

                    currentTournament.setText(getMatchDisplayString(matchArray.getJSONObject(i)));
                    currentTournament.setTag(matchArray.getJSONObject(i).getInt("matchId"));
                    currentTournament.setClickable(true);
                    currentTournament.setOnClickListener(viewMatch);
                    currentTournament.setTextSize(20);
                    currentTournament.setPadding(5, 5, 5, 5);

                    matchList.addView(currentTournament, 0);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMatchDisplayString(JSONObject match) {
        try {
            return match.getString("playerOneName") + " vs. " + match.getString("playerTwoName");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setTournamentObject() {
        String[] params = new String[]{
                "tournamentId", String.valueOf(tournamentId)};

        try {
            String response = new RetrieveFeedTask(
                    "https://www.mikenguyenmd.com/match_live/getTournament", params).execute().get();

            if (!response.equals("null")) {
                tournamentObject = new JSONObject(response.toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideOptions() {
        try {
            if (!signedInEmail.equals(tournamentObject.getString("creatorEmail"))) {
                (findViewById(R.id.fab)).setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTournamentName() {
        try{
            tournamentName = tournamentObject.getString("tournamentName");
            ((TextView)findViewById(R.id.matchListTournamentNameDisplay)).setText(tournamentName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        try {
            if (signedInEmail.equals(tournamentObject.getString("creatorEmail"))) {
                getMenuInflater().inflate(R.menu.menu_tennis_score, menu);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

        if (id == R.id.deleteMatches) {
            System.out.println("calling dialog");
            deleteMatches();
        }

        if (id == R.id.addUserToTournament) {
            addUserToTournament();
        }

        return super.onOptionsItemSelected(item);
    }
}
