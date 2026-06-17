package com.example.dclassicbook.application.handler;

import com.example.dclassicbook.application.handler.data.BookData;
import com.example.dclassicbook.application.handler.models.Book;

public class GetBookDetailHandler {

    public static Book getBookByTitle(String title) {
        for (Book book : BookData.getBooks()) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }
}
