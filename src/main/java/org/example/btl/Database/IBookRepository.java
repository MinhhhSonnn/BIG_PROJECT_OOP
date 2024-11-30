package org.example.btl.Database;

import java.util.List;
import org.example.btl.model.Book;

public interface IBookRepository {
  boolean addBook(Book book);
  boolean deleteBook(Book book);
  boolean updateBook(Book book);
  Book findBookById(int id);
  Book findBookByTitle(String title);
  List<Book> findAllBooks();

}
