package com.example.dclassicbook.application.handler.data;

import com.example.dclassicbook.R;
import com.example.dclassicbook.application.handler.models.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreData {

    public static List<Store> getStores() {
        List<Store> stores = new ArrayList<>();

        stores.add(new Store(
                R.drawable.img_store_central,
                "Central D'Classic",
                "Jl. M.H. Thamrin No. 1, Menteng, Jakarta Pusat"
        ));

        stores.add(new Store(
                R.drawable.img_store_poris,
                "D'Classic Poris Store",
                "Cipondoh, Kota Tangerang, Provinsi Banten"
        ));

        stores.add(new Store(
                R.drawable.img_store_sentul,
                "D'Classic Sentul",
                "Sentul City, Babakan Madang, Bogor, Jawa Barat"
        ));

        stores.add(new Store(
                R.drawable.img_store_boulevard,
                "D'Classic Boulevard",
                "Jl. Ganesha Boulevard, Kec. Cikarang Pusat"
        ));

        stores.add(new Store(
                R.drawable.img_store_yogya,
                "Yogyakarta D'Classic Store",
                "Jl. Malioboro, Sosromenduran, Gedong Tengen"
        ));

        return stores;
    }
}
