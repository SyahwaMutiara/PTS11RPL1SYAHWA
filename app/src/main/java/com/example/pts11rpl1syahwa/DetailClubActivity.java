package com.example.pts11rpl1syahwa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailClubActivity extends AppCompatActivity {
    public static final String ITEM_EXTRA = "item_extra";
    private Bundle bundle;
    private String name, desc, image;
    private boolean isFavorite;
    Button btn_fav;
    Realm realm;
    RealmHelper realmHelper;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_club);

        ImageView imgClub = findViewById(R.id.imgclub);
        TextView tvClubName = findViewById(R.id.tvClubName);
        TextView tvClubDetail = findViewById(R.id.tvClubDetail);
        btn_fav = findViewById(R.id.btn_fav);

        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        bundle = getIntent().getExtras();
        if (bundle != null){
            name = bundle.getString("judul");
            desc = bundle.getString("deskripsi");
            image = bundle.getString("gambar");
            isFavorite = bundle.getBoolean("favorite");
            id = bundle.getInt("id");
            Glide.with(this)
                    .load(image)
                    .into(imgClub);
            tvClubName.setText(name);
            tvClubDetail.setText(desc);
        }
        if (getSupportActionBar() !=null){
               getSupportActionBar().setTitle("Detail Club");
               getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (isFavorite) {
            btn_fav.setText("Remove from favorites");
        } else {
            btn_fav.setText("Add to favorites");
        }

        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    realmHelper.delete(id);
                    btn_fav.setText("Add to favorites");
                    isFavorite = false;
                } else {
                    ClubModel club = new ClubModel(name, image, desc, true);
                    id = realmHelper.save(club);
                    btn_fav.setText("Remove from favorites");
                    isFavorite = true;
                }
                Toast.makeText(DetailClubActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("isFavorite", isFavorite);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}