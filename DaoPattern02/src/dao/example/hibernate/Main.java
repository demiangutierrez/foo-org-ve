package dao.example.hibernate;

import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;

public class Main {

  public static void main(String[] args) throws Exception {
    ConnectionBean cb = ConnectionFactory.getConnectionBean();

    cb.getSession().close();
  }
}
