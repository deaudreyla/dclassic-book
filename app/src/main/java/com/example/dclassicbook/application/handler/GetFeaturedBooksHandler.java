package com.example.dclassicbook.application.handler;

import com.example.dclassicbook.application.handler.data.BookData;
import com.example.dclassicbook.application.handler.models.Book;

import java.util.ArrayList;
import java.util.List;

public class GetFeaturedBooksHandler {

    public static List<Book> getFeaturedBooks() {
        List<Book> books = new ArrayList<>(BookData.getBooks());
        books.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        return books;
    }
}
