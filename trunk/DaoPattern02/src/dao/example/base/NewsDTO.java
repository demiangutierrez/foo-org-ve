package dao.example.base;

/**
 * @author Demián Gutierrez
 */
public interface NewsDTO extends PublicationDTO {

  public static final String NEWS_ATT_1 = "newsAtt1";
  public static final String NEWS_ATT_2 = "newsAtt2";

  // --------------------------------------------------------------------------------

  public String getNewsAtt1();

  public void setNewsAtt1(String newsAtt1);

  // --------------------------------------------------------------------------------

  public String getNewsAtt2();

  public void setNewsAtt2(String newsAtt2);
}
