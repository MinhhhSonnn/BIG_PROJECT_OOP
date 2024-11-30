package org.example.btl.service;

import org.example.btl.Database.IBookRepository;
import org.example.btl.model.Book;

public class BookService {

  private final IBookRepository bookRepository;

  public BookService(IBookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

}
