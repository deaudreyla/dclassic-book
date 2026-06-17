package com.example.dclassicbook.application.handler;

import com.example.dclassicbook.application.handler.data.StoreData;
import com.example.dclassicbook.application.handler.models.Store;

import java.util.List;

public class GetStoreListHandler {

    public static List<Store> getStores() {
        return StoreData.getStores();
    }
}
