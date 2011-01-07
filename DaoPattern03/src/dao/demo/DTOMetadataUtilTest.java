package dao.demo;

import java.util.List;

import dao.annotation.ManyToOne;
import dao.annotation.OneToMany;
import dao.annotation.PK;
import dao.annotation.SQLType;
import dao.annotation.Transient;
import dao.base.api.FactoryDAO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DepartmentDTO;
import dao.reflection.ColumnDescriptor;
import dao.reflection.DTOMetadataUtil;

/**
 * @author Demián Gutierrez
 */
public class DTOMetadataUtilTest {

  public static void main(String[] args) throws Exception {
    ConnectionBean conn = ConnectionFactory.getConnectionBean();

    FactoryDAO factoryDAO = AbstractFactoryDAO.getFactoryDAO();

    DepartmentDTO d = (DepartmentDTO) //
    factoryDAO.getDTO(DepartmentDTO.class, conn);

    DTOMetadataUtil dtoMetadataUtil = new DTOMetadataUtil(d.getClass());

    List<ColumnDescriptor> list = dtoMetadataUtil.getColumnDescriptorList();

    for (ColumnDescriptor columnDescriptor : list) {
      System.err.println("****************************************");

      System.err.println(columnDescriptor.getName() + ";" + //
          columnDescriptor.getType());

      ManyToOne manyToOne = columnDescriptor.getManyToOne();

      if (manyToOne != null) {
        System.err.println("ManyToOne ----->");
        System.err.println("mappedBy: " + manyToOne.mappedBy());
        System.err.println("dtoClass: " + manyToOne.dtoClass());
      }

      OneToMany oneToMany = columnDescriptor.getOneToMany();

      if (oneToMany != null) {
        System.err.println("OneToMany ----->");
        System.err.println("mappedBy: " + oneToMany.mappedBy());
        System.err.println("dtoClass: " + oneToMany.dtoClass());
      }

      PK pk = columnDescriptor.getPK();

      if (pk != null) {
        System.err.println("PK ------------>");
      }

      Transient tranzient = columnDescriptor.getTransient();

      if (tranzient != null) {
        System.err.println("Transient ----->");
      }

      SQLType sqlType = columnDescriptor.getSQLType();

      if (sqlType != null) {
        System.err.println("SQLType ------->");
        System.err.println("value: " + sqlType.value());
      }

      System.err.println("****************************************");
      System.err.println("table name: " + dtoMetadataUtil.getTableName());
    }
  }
}
