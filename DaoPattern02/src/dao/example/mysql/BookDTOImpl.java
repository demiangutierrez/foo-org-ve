package dao.example.mysql;

import dao.example.base.BookDTO;

/**
 * @author Demián Gutierrez
 */
public class BookDTOImpl extends PublicationDTOImpl implements BookDTO {

  private String bookAtt1;
  private String bookAtt2;

  // --------------------------------------------------------------------------------

  public BookDTOImpl() {
    // TODO:
    // super(BookDAO.class);
  }

  // --------------------------------------------------------------------------------

  public String getBookAtt1() {
    return bookAtt1;
  }

  public void setBookAtt1(String bookAtt1) {
    this.bookAtt1 = bookAtt1;
  }

  // --------------------------------------------------------------------------------

  public String getBookAtt2() {
    return bookAtt2;
  }

  public void setBookAtt2(String bookAtt2) {
    this.bookAtt2 = bookAtt2;
  }
}
