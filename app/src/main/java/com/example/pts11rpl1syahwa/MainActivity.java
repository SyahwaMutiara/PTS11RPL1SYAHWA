 package com.example.pts11rpl1syahwa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.content.ContentValues.TAG;

 public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ClubModel> list;
    ListClubAdapter adapter;
    String gambar, deskripsi, nama;
     Realm realm;
     RealmHelper realmHelper;
     private final int REQUEST_CODE = 101;
     private int listIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);

        recyclerView = findViewById(R.id.rv_club);
        recyclerView.setHasFixedSize(true);
        showRecylerList();
    }

    private void showRecylerList() {
        list = new ArrayList<>();
        AndroidNetworking.get("https://www.thesportsdb.com/api/v1/json/1/search_all_leagues.php?c=England")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + response.toString());
                        {
                            try {
                                JSONArray results = response.getJSONArray("countrys");
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject object = results.getJSONObject(i);
                                    gambar =  object.getString("strBadge");
                                    nama  =  object.getString("strLeague");
                                    deskripsi =  object.getString("strDescriptionEN");
                                    list.add(new ClubModel(nama , gambar , deskripsi, false));
                                    ClubModel favoriteClub = realm.where(ClubModel.class).equalTo("deskripsi", deskripsi).findFirst();
                                    if (favoriteClub != null) {
                                        list.get(i).setFavorite(favoriteClub.getFavorite());
                                        list.get(i).setId(favoriteClub.getId());
                                    } else {
                                        list.get(i).setId(0);
                                        list.get(i).setFavorite(false);
                                    }
                                }

                                setAdapter();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }@Override
                    public void onError(ANError error) {
                        Log.d(TAG, "onError: " + error);
                    }

                });

    }

     private void setAdapter() {
         ListClubAdapter adapter = new ListClubAdapter(list, new ListClubAdapter.Callback() {
             @Override
             public void onClick(int position) {
                 listIndex = position;
                 ClubModel Operator = list.get(position);
                 Intent move = new Intent(getApplicationContext(), DetailClubActivity.class);
                 move.putExtra("deskripsi", Operator.getDeskripsi());
                 move.putExtra("judul", Operator.getNama());
                 move.putExtra("gambar", Operator.getGambar());
                 move.putExtra("favorite", Operator.getFavorite());
                 move.putExtra("id", Operator.getId());
                 startActivity(move);
             }
         });
         recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
         recyclerView.setHasFixedSize(true);
         recyclerView.setAdapter(adapter);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
             list.get(listIndex).setId(data.getIntExtra("id", 0));
             list.get(listIndex).setFavorite(data.getBooleanExtra("isFavorite", false));
             setAdapter();
             listIndex = 0;
         }
     }
 }