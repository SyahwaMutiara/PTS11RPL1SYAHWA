package com.example.pts11rpl1syahwa;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ClubModel extends RealmObject {
    @PrimaryKey
    private Integer id;
    private String nama;
    private String gambar;
    private String deskripsi;
    private boolean isFavorite;

    public ClubModel(){}

    public ClubModel(String nama, String gambar, String deskripsi, boolean isFavorite) {
        this.nama = nama;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.isFavorite = isFavorite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
