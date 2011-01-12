package dao.example.base;

/**
 * @author Demián Gutierrez
 */
public interface BookDTO extends PublicationDTO {

  public static final String BOOK_ATT_1 = "bookAtt1";
  public static final String BOOK_ATT_2 = "bookAtt2";

  // --------------------------------------------------------------------------------

  public String getBookAtt1();

  public void setBookAtt1(String bookAtt1);

  // --------------------------------------------------------------------------------

  public String getBookAtt2();

  public void setBookAtt2(String bookAtt2);
}
