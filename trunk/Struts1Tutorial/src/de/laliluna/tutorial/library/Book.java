package de.laliluna.tutorial.library;

/**
 * @author laliluna
 */
public class Book {

  private long id;

  private String title;
  private String author;

  private boolean available;

  // --------------------------------------------------------------------------------

  public Book() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public Book(long id, String author, String title, boolean available) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.available = available;
  }

  // --------------------------------------------------------------------------------

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  // --------------------------------------------------------------------------------

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  // --------------------------------------------------------------------------------

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  // --------------------------------------------------------------------------------

  public boolean getAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }
}
