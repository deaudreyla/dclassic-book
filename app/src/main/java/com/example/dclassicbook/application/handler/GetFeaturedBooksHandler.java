package com.example.dclassicbook.application.handler;

import com.example.dclassicbook.application.handler.data.BookData;
import com.example.dclassicbook.application.handler.models.Book;

import java.util.ArrayList;
import java.util.List;

public class GetFeaturedBooksHandler {

    public static List<Book> getFeaturedBooks() {
        List<Book> allBooks = new ArrayList<>(BookData.getBooks());
        allBooks.sort((a, b) -> {
            int ratingCompare = Double.compare(b.getRating(), a.getRating());
            if (ratingCompare != 0) return ratingCompare;
            return Long.compare(b.getUserCount(), a.getUserCount());
        });

        List<Book> featured = new ArrayList<>();
        for (Book book : allBooks) {
            if (featured.size() < 4) {
                featured.add(book);
            }
        }
        return featured;
    }
}
