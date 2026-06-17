package com.example.dclassicbook.application;

import com.example.dclassicbook.application.handler.GetBookDetailHandler;
import com.example.dclassicbook.application.handler.GetBookListHandler;
import com.example.dclassicbook.application.handler.GetFeaturedBooksHandler;
import com.example.dclassicbook.application.handler.GetStoreListHandler;
import com.example.dclassicbook.application.handler.models.Book;
import com.example.dclassicbook.application.handler.models.Store;

import java.util.List;

public class Mediator {

    public static List<Book> getBookList(String category) {
        return GetBookListHandler.getBooksByCategory(category);
    }

    public static Book getBookDetail(String title) {
        return GetBookDetailHandler.getBookByTitle(title);
    }

    public static List<Store> getStoreList() {
        return GetStoreListHandler.getStores();
    }

    public static List<Book> getFeaturedBooks() {
        return GetFeaturedBooksHandler.getFeaturedBooks();
    }
}
