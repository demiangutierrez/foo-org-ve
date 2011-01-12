package dao.example.mysql;

import dao.example.base.NewsDTO;

/**
 * @author Demián Gutierrez
 */
public class NewsDTOImpl extends PublicationDTOImpl implements NewsDTO {

  private String newsAtt1;
  private String newsAtt2;

  // --------------------------------------------------------------------------------

  public NewsDTOImpl() {
    // TODO:
    // super(BookDAO.class);
  }

  // --------------------------------------------------------------------------------

  public String getNewsAtt1() {
    return newsAtt1;
  }

  public void setNewsAtt1(String newsAtt1) {
    this.newsAtt1 = newsAtt1;
  }

  // --------------------------------------------------------------------------------

  public String getNewsAtt2() {
    return newsAtt2;
  }

  public void setNewsAtt2(String newsAtt2) {
    this.newsAtt2 = newsAtt2;
  }
}
