package com.example.dclassicbook.application;

import com.example.dclassicbook.application.handler.GetBookDetailHandler;
import com.example.dclassicbook.application.handler.GetBookListHandler;
import com.example.dclassicbook.application.handler.models.Book;

import java.util.List;

public class Mediator {

    public static List<Book> getBookList(String category) {
        return GetBookListHandler.getBooksByCategory(category);
    }

    public static Book getBookDetail(String title) {
        return GetBookDetailHandler.getBookByTitle(title);
    }
}
