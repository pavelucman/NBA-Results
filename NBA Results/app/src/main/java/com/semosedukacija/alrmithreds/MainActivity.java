package com.semosedukacija.alrmithreds;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.semosedukacija.alrmithreds.activity.LoginActivity;
import com.semosedukacija.alrmithreds.adapter.GameAdapter;
import com.semosedukacija.alrmithreds.models.Game;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    List<Game> listGames;

    ProgressBar progressBar;

    ListView listViewGames;


    GameAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCompoments();
        setComponents();

    }

    private void setComponents() {
        String currentDate;

        Date date = new Date();
        date.setHours(-6);
//        date.setDate(-1);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        currentDate = format.format(date);
        new DownloadWebPageTask().execute(
                new String[]{"http://data.nba.net/data/10s/prod/v2/" +currentDate +"/scoreboard.json"}
        );
    }

    private void initCompoments() {

        progressBar = findViewById(R.id.progress);

        listViewGames = findViewById(R.id.listGames);

        listGames = new ArrayList<>();

    }

    public class DownloadWebPageTask extends AsyncTask<String, Integer, String> {
        String stringResponse = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... url) {
            publishProgress(30);
            try {
                for (int i = 0; i < url.length; i++) {

                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url[i]);

                    HttpResponse response = client.execute(httpGet);

                    InputStream content = response.getEntity().getContent();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
                    String htmlContent = "";
                    while ((htmlContent = bufferedReader.readLine())!=null){
                        stringResponse += htmlContent;
                    }

                    JSONObject jsonResponse = new JSONObject(stringResponse);
                    JSONArray jsonGames =  jsonResponse.getJSONArray("games");
                    for (int j = 0; j < jsonGames.length(); j++) {
                        JSONObject jsonObjectGame = jsonGames.getJSONObject(j);
                        Log.w("GAME", "" + jsonObjectGame.optString("startTimeDate",""));
//                        Log.w("GAME", String.valueOf(jsonObjectGame.getBoolean("startTimeUTC")));
                        Log.w("GAME HOME", jsonObjectGame.getJSONObject("hTeam").getString("triCode"));

                        String dateStartUTC = jsonObjectGame.getString("startTimeUTC");
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
//                        Date dateStart = formatter.parse(dateStartUTC);
                        JSONObject teamHome = jsonObjectGame.getJSONObject("hTeam");
                        JSONObject teamAway = jsonObjectGame.getJSONObject("vTeam");

                        // so default / prazen konstruktor
                        Game game = new Game();
                        game.setHomeTeam(teamHome.getString("triCode"));
                        game.setPointH(teamHome.optInt("score", 0));

                        game.setAwayTeam(teamAway.getString("triCode"));
                        game.setPointA(teamAway.optInt("score", 0));

                        game.setDateStart(new Date());
                        game.setCurrentResult(game.getPointH() + " : " + game.getPointA());

                        listGames.add(game);
                    }


                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stringResponse;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);

//            Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
            adapter = new GameAdapter(MainActivity.this, listGames);
            listViewGames.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
