
package com.example.pts11rpl1syahwa;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;
    private int id;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    // untuk menyimpan data
    public int save(final ClubModel club) {
        id = 0;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(ClubModel.class).max("id");
                    int nextId;
                    if (currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    id = nextId;
                    club.setId(nextId);
                    ClubModel model = realm.copyToRealm(club);
                } else {
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
        return id;
    }

    // untuk memanggil semua data
    public List<ClubModel> getAllMahasiswa() {
        RealmResults<ClubModel> results = realm.where(ClubModel.class).findAll();
        System.out.println("results name: "+results.get(0).getNama());
        System.out.println("results desc: "+results.get(0).getDeskripsi());
        return results;
    }

    // untuk menghapus data
    public void delete(Integer id) {
        final RealmResults<ClubModel> model = realm.where(ClubModel.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        });
    }
}