package de.laliluna.tutorial.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpSession;

/**
 * @author laliluna
 */
public class SimulateDB {

  private Collection<Book> books;

  // --------------------------------------------------------------------------------

  /**
   * Init data to db;
   */
  private void init(HttpSession session) {
    Random random = new Random();

    books = new ArrayList<Book>();

    books.add(new Book(random.nextLong(), "David Roos",/*  */"Struts book",/*                  */true));
    books.add(new Book(random.nextLong(), "Micheal Jackson", "Java book",/*                    */true));
    books.add(new Book(random.nextLong(), "Bruce Lee",/*   */"Java2 book",/*                   */false));
    books.add(new Book(random.nextLong(), "Tom Jones",/*   */"EJB book",/*                     */true));
    books.add(new Book(random.nextLong(), "Mc Donald",/*   */"JBoss for beginners",/*          */false));
    books.add(new Book(random.nextLong(), "Lars Mars",/*   */"Using MyEclipse for cooking",/*  */true));
    books.add(new Book(random.nextLong(), "Mary Jane",/*   */"EJB or spending your weekends",/**/true));

    session.setAttribute("bookDB", books);
  }

  // --------------------------------------------------------------------------------

  /**
   * Load data from db;
   */
  @SuppressWarnings("unchecked")
  private void loadData(HttpSession session) {
    books = (Collection<Book>) session.getAttribute("bookDB");

    if (books == null) {
      init(session);
    }
  }

  // --------------------------------------------------------------------------------

  /**
   * Save data to DB
   */
  private void saveData(HttpSession session) {
    session.setAttribute("bookDB", books);
  }

  // --------------------------------------------------------------------------------

  public long saveToDB(Book book, HttpSession session) {
    loadData(session);

    boolean bookExist = false;

    ArrayList<Book> booksNew = (ArrayList<Book>) books;

    int index = 0;

    for (Book element : books) {
      // If book is found do an update
      if (element.getId() == book.getId()) {
        booksNew.set(index, book);
        bookExist = true;
        break;
      }
      index++;
    }

    books = booksNew;

    if (bookExist == false) {
      Random random = new Random();
      book.setId(random.nextLong());
      books.add(book);
    }

    saveData(session);

    return book.getId();
  }

  // --------------------------------------------------------------------------------

  public Book loadBookById(long id, HttpSession session) {
    // laliluna 04.10.2004 load books from db
    loadData(session);
    // laliluna 04.10.2004 loop over all books and return book if found
    for (Iterator<Book> iter = books.iterator(); iter.hasNext();) {
      Book element = (Book) iter.next();
      if (element.getId() == id)
        return (Book) element;
    }
    return null;
  }

  public void deleteBookById(long id, HttpSession session) {
    // laliluna 04.10.2004 load books from db
    loadData(session);
    Collection<Book> booksNew = new ArrayList<Book>();
    // laliluna 04.10.2004 loop over all books and delete book if found
    for (Iterator<Book> iter = books.iterator(); iter.hasNext();) {
      Book element = (Book) iter.next();
      if (element.getId() != id) {
        booksNew.add(element);
      }
    }

    // laliluna 04.10.2004 set the new books
    books = booksNew;

    // laliluna 04.10.2004 save to DB ;-)
    saveData(session);
  }

  public Collection<Book> getAllBooks(HttpSession session) {
    // laliluna 04.10.2004 load books from db
    loadData(session);
    return books;

  }
}
