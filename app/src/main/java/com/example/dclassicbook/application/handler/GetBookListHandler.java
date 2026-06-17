package com.example.dclassicbook.application.handler;

import com.example.dclassicbook.application.handler.data.BookData;
import com.example.dclassicbook.application.handler.models.Book;

import java.util.ArrayList;
import java.util.List;

public class GetBookListHandler {

    public static List<Book> getBooksByCategory(String category) {
        List<Book> result = new ArrayList<>();
        for (Book book : BookData.getBooks()) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                result.add(book);
            }
        }
        return result;
    }
}
