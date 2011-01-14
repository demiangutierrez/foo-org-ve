package de.laliluna.tutorial.library.struts.form;

import org.apache.struts.action.ActionForm;

import de.laliluna.tutorial.library.Book;

/**
 * @author laliluna
 */
public class BookEditForm extends ActionForm {

  private Book book = new Book();

  // ----------------------------------------

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  // ----------------------------------------
  // Useful delegators for the book attribute
  // ----------------------------------------

  public long getId() {
    return book.getId();
  }

  public void setId(long id) {
    book.setId(id);
  }

  // ----------------------------------------

  public String getAuthor() {
    return book.getAuthor();
  }

  public void setAuthor(String author) {
    book.setAuthor(author);
  }

  // ----------------------------------------

  public String getTitle() {
    return book.getTitle();
  }

  public void setTitle(String title) {
    book.setTitle(title);
  }

  // ----------------------------------------

  public boolean getAvailable() {
    return book.getAvailable();
  }

  public void setAvailable(boolean available) {
    book.setAvailable(available);
  }

  // ----------------------------------------
  // Object methods
  // ----------------------------------------

  public int hashCode() {
    return book.hashCode();
  }

  public boolean equals(Object obj) {
    return book.equals(obj);
  }

  public String toString() {
    return book.toString();
  }
}
