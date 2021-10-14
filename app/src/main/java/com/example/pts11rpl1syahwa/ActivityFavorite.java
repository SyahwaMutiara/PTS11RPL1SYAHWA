package com.example.pts11rpl1syahwa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ActivityFavorite extends AppCompatActivity {
    RecyclerView recyclerView;
    Realm realm;
    RealmHelper realmHelper;
    List<ClubModel> teamlist;
    ListClubAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView = findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Setup Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        teamlist = new ArrayList<>();

        teamlist = realmHelper.getAllMahasiswa();
        System.out.println("team name "+teamlist.get(0).getNama());

        show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        teamAdapter.notifyDataSetChanged();
        show();
    }

    public void show(){
        teamAdapter = new ListClubAdapter(teamlist, new ListClubAdapter.Callback() {
            @Override
            public void onClick(int position) {
                ClubModel Operator = teamlist.get(position);
                Intent move = new Intent(getApplicationContext(), DetailClubActivity.class);
                move.putExtra("deskripsi", Operator.getDeskripsi());
                move.putExtra("judul", Operator.getNama());
                move.putExtra("gambar", Operator.getGambar());
                move.putExtra("favorite", Operator.getFavorite());
                move.putExtra("id", Operator.getId());
                startActivity(move);
            }
        });
        recyclerView.setAdapter(teamAdapter);
    }
}