package de.laliluna.tutorial.library.struts.form;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import de.laliluna.tutorial.library.Book;

/**
 * @author laliluna
 */
public class BookListForm extends ActionForm {

  private Collection<Book> books;

  // --------------------------------------------------------------------------------

  public BookListForm() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public Collection<Book> getBooks() {
    return books;
  }

  public void setBooks(Collection<Book> books) {
    this.books = books;
  }

  // --------------------------------------------------------------------------------

  public void reset( //
      ActionMapping actionMapping, HttpServletRequest httpServletRequest) {

    books = new ArrayList<Book>();
  }
}
