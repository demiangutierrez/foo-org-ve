package dao.test;

import java.util.Iterator;

import junit.framework.TestCase;
import dao.base.api.FactoryDAO;
import dao.connection.ConnectionBean;
import dao.connection.ConnectionFactory;
import dao.example.base.AbstractFactoryDAO;
import dao.example.base.DeptDAO;
import dao.example.base.DeptDTO;
import dao.example.base.ProfDAO;
import dao.example.base.ProfDTO;

/**
 * @author Demián Gutierrez
 */
public class TestDeptProf extends TestCase {

  private boolean compareDTODeep(DeptDTO deptDTO1, DeptDTO deptDTO2) {
    boolean ret = true;

    ret = ret && deptDTO1.getId() == deptDTO2.getId();

    ret = ret && deptDTO1.getDeptAtt1().equals(deptDTO2.getDeptAtt1());
    ret = ret && deptDTO1.getDeptAtt2().equals(deptDTO2.getDeptAtt2());

    if (deptDTO1.getProfDTOList().size() != //
    deptDTO2.getProfDTOList().size()) {
      return false;
    }

    Iterator<ProfDTO> itt1 = deptDTO1.getProfDTOList().iterator();
    Iterator<ProfDTO> itt2 = deptDTO2.getProfDTOList().iterator();

    while (itt1.hasNext() && itt2.hasNext()) {
      ProfDTO profDTO1 = (ProfDTO) itt1.next();
      ProfDTO profDTO2 = (ProfDTO) itt2.next();

      compareDTOShallow(profDTO1, profDTO2);
    }

    return ret;
  }

  // --------------------------------------------------------------------------------

  private boolean compareDTODeep(ProfDTO profDTO1, ProfDTO profDTO2) {
    boolean ret = true;

    ret = ret && profDTO1.getId() == profDTO2.getId();

    ret = ret && profDTO1.getProfAtt1().equals(profDTO2.getProfAtt1());
    ret = ret && profDTO1.getProfAtt2().equals(profDTO2.getProfAtt2());

    DeptDTO ref1 = profDTO1.getDeptDTORef();
    DeptDTO ref2 = profDTO2.getDeptDTORef();

    ret = ret && ref1.getId() == ref2.getId();

    compareDTOShallow(ref1, ref2);

    return ret;
  }

  // --------------------------------------------------------------------------------

  private boolean compareDTOShallow(DeptDTO deptDTO1, DeptDTO deptDTO2) {
    boolean ret = true;

    ret = ret && deptDTO1.getId() == deptDTO2.getId();

    ret = ret && deptDTO1.getDeptAtt1().equals(deptDTO2.getDeptAtt1());
    ret = ret && deptDTO1.getDeptAtt2().equals(deptDTO2.getDeptAtt2());

    return ret;
  }

  // --------------------------------------------------------------------------------

  private boolean compareDTOShallow(ProfDTO profDTO1, ProfDTO profDTO2) {
    boolean ret = true;

    ret = ret && profDTO1.getId() == profDTO2.getId();

    ret = ret && profDTO1.getProfAtt1().equals(profDTO2.getProfAtt1());
    ret = ret && profDTO1.getProfAtt2().equals(profDTO2.getProfAtt2());

    return ret;
  }

  // --------------------------------------------------------------------------------

  public void testRefOnInsert() throws Exception {
    ConnectionBean connectionBean;

    DeptDAO deptDAO;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    // ----------------------------------------
    // INSERT Depts
    // ----------------------------------------

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("DeptAtt1_111");
    deptDTO1.setDeptAtt2("DeptAtt2_111");
    deptDAO.insert(deptDTO1);
    assertEquals(deptDTO1.getId(), 1);

    DeptDTO deptDTO2 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO2.setDeptAtt1("DeptAtt1_222");
    deptDTO2.setDeptAtt2("DeptAtt2_222");
    deptDAO.insert(deptDTO2);
    assertEquals(deptDTO2.getId(), 2);

    // ----------------------------------------
    // INSERT Profs
    // ----------------------------------------

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("ProfAtt1_111");
    profDTO1.setProfAtt2("ProfAtt2_111");
    //profDTO1.setDeptRef(deptDTO1); // ----------> LINK
    profDTO1.setDeptDTORef(deptDTO1); // ----------> LINK
    deptDTO1.getProfDTOList().add(profDTO1); // -----> LINK
    profDAO.insert(profDTO1);
    assertEquals(profDTO1.getId(), 1);

    ProfDTO profDTO2 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO2.setProfAtt1("ProfAtt1_222");
    profDTO2.setProfAtt2("ProfAtt2_222");
    //profDTO2.setDeptRef(deptDTO1); // ----------> LINK
    profDTO2.setDeptDTORef(deptDTO1); // ----------> LINK
    deptDTO1.getProfDTOList().add(profDTO2); // -----> LINK
    profDAO.insert(profDTO2);
    assertEquals(profDTO2.getId(), 2);

    ProfDTO profDTO3 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO3.setProfAtt1("ProfAtt1_333");
    profDTO3.setProfAtt2("ProfAtt2_333");
    //profDTO3.setDeptRef(deptDTO2); // ----------> LINK
    profDTO3.setDeptDTORef(deptDTO2); // ----------> LINK
    deptDTO2.getProfDTOList().add(profDTO3); // -----> LINK
    profDAO.insert(profDTO3);
    assertEquals(profDTO3.getId(), 3);

    ProfDTO profDTO4 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO4.setProfAtt1("ProfAtt1_444");
    profDTO4.setProfAtt2("ProfAtt2_444");
    //profDTO4.setDeptRef(deptDTO2); // ----------> LINK
    profDTO4.setDeptDTORef(deptDTO2); // ----------> LINK
    deptDTO2.getProfDTOList().add(profDTO4); // -----> LINK
    profDAO.insert(profDTO4);
    assertEquals(profDTO4.getId(), 4);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / CHECK Depts
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOX = (DeptDTO) deptDAO.loadById(1);

    // XXX: Not needed anymore, it loads lazy on request
    //deptDAO.loadProfList(deptDTOX);
    assertTrue(compareDTODeep(deptDTO1, deptDTOX));

    DeptDTO deptDTOY = (DeptDTO) deptDAO.loadById(2);

    // XXX: Not needed anymore, it loads lazy on request
    //deptDAO.loadProfList(deptDTOY);
    assertTrue(compareDTODeep(deptDTO2, deptDTOY));

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / CHECK Profs
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOX = (ProfDTO) profDAO.loadById(1);

    // XXX: Not needed anymore, it loads lazy on request
    //profDAO.loadDeptRef(profDTOX);
    assertTrue(compareDTODeep(profDTO1, profDTOX));

    ProfDTO profDTOY = (ProfDTO) profDAO.loadById(2);

    // XXX: Not needed anymore, it loads lazy on request
    //profDAO.loadDeptRef(profDTOY);
    assertTrue(compareDTODeep(profDTO2, profDTOY));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testRefOnUpdate() throws Exception {
    ConnectionBean connectionBean;

    DeptDAO deptDAO;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    // ----------------------------------------
    // INSERT Depts
    // ----------------------------------------

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO1 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO1.setDeptAtt1("DeptAtt1_111");
    deptDTO1.setDeptAtt2("DeptAtt2_111");
    deptDAO.insert(deptDTO1);
    assertEquals(deptDTO1.getId(), 1);

    DeptDTO deptDTO2 = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO2.setDeptAtt1("DeptAtt1_222");
    deptDTO2.setDeptAtt2("DeptAtt2_222");
    deptDAO.insert(deptDTO2);
    assertEquals(deptDTO2.getId(), 2);

    // ----------------------------------------
    // INSERT Profs
    // ----------------------------------------

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    // d1 -> e1
    ProfDTO profDTO1 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO1.setProfAtt1("ProfAtt1_111");
    profDTO1.setProfAtt2("ProfAtt2_111");
    //profDTO1.setDeptRef(deptDTO1); // ----------> LINK
    profDTO1.setDeptDTORef(deptDTO1); // ----------> LINK
    deptDTO1.getProfDTOList().add(profDTO1); // -----> LINK
    profDAO.insert(profDTO1);
    assertEquals(profDTO1.getId(), 1);

    // d2 -> e2
    ProfDTO profDTO2 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO2.setProfAtt1("ProfAtt1_222");
    profDTO2.setProfAtt2("ProfAtt2_222");
    //profDTO2.setDeptRef(deptDTO2); // ----------> LINK
    profDTO2.setDeptDTORef(deptDTO2); // ----------> LINK
    deptDTO2.getProfDTOList().add(profDTO2); // -----> LINK
    profDAO.insert(profDTO2);
    assertEquals(profDTO2.getId(), 2);

    // e3 -> (No dep)
    ProfDTO profDTO3 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO3.setProfAtt1("ProfAtt1_333");
    profDTO3.setProfAtt2("ProfAtt2_333");
    //profDTO3.setDeptRef(deptDTO1); // ----------> LINK
    //deptDTO2.getProfList().add(profDTO3); // -----> LINK
    profDAO.insert(profDTO3);
    assertEquals(profDTO3.getId(), 3);

    // e4 -> (No dep)
    ProfDTO profDTO4 = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO4.setProfAtt1("ProfAtt1_444");
    profDTO4.setProfAtt2("ProfAtt2_444");
    //profDTO4.setDeptRef(deptDTO2); // ----------> LINK
    //deptDTO2.getProfList().add(profDTO4); // -----> LINK
    profDAO.insert(profDTO4);
    assertEquals(profDTO4.getId(), 4);

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / UPDATE Depts
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    // d1->(e1)
    DeptDTO deptDTOa = (DeptDTO) deptDAO.loadById(1);

    // XXX: Not needed anymore, it loads lazy on request
    //deptDAO.loadProfList(deptDTOa);
    assertTrue(compareDTODeep(deptDTO1, deptDTOa));

    // d2->(e2)
    DeptDTO deptDTOb = (DeptDTO) deptDAO.loadById(2);
    // XXX: Not needed anymore, it loads lazy on request
    //deptDAO.loadProfList(deptDTOb);
    assertTrue(compareDTODeep(deptDTO2, deptDTOb));

    // Load 1 & 2 just to compare in the next part
    ProfDTO profDTOa = (ProfDTO) profDAO.loadById(1);
    // XXX: To avoid a lazy load like problem below when comparing
    profDTOa.getDeptDTORef();
    // XXX: Not needed anymore, it loads lazy on request
    //profDAO.loadDeptRef(profDTOa);
    ProfDTO profDTOb = (ProfDTO) profDAO.loadById(2);
    // XXX: To avoid a lazy load like problem below when comparing
    profDTOb.getDeptDTORef();
    // XXX: Not needed anymore, it loads lazy on request
    //profDAO.loadDeptRef(profDTOb);

    // d1->(e1, e3)
    ProfDTO profDTOc = (ProfDTO) profDAO.loadById(3);
    //profDTOc.setDeptRef(deptDTOa); // ----------> LINK
    profDTOc.setDeptDTORef(deptDTOa); // ----------> LINK
    // If I don't add I can't compare below
    deptDTOa.getProfDTOList().add(profDTOc); // -----> LINK
    profDAO.update(profDTOc);

    // d2->(e2, e4)
    ProfDTO profDTOd = (ProfDTO) profDAO.loadById(4);
    //profDTOd.setDeptRef(deptDTOb); // ----------> LINK
    profDTOd.setDeptDTORef(deptDTOb); // ----------> LINK
    // If I don't add I can't compare below
    deptDTOb.getProfDTOList().add(profDTOd); // -----> LINK
    profDAO.update(profDTOd);

    // d3->Empty
    // d4->Empty

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / CHECK Depts
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    DeptDTO deptDTOX = (DeptDTO) deptDAO.loadById(1);
    // XXX: Not needed anymore, it loads lazy on request
    //deptDAO.loadProfList(deptDTOX);
    assertTrue(compareDTODeep(deptDTOa, deptDTOX));

    DeptDTO deptDTOY = (DeptDTO) deptDAO.loadById(2);
    // XXX: Not needed anymore, it loads lazy on request
    //deptDAO.loadProfList(deptDTOY);
    assertTrue(compareDTODeep(deptDTOb, deptDTOY));

    ConnectionFactory.closeConnection(connectionBean.getConnection());

    // ----------------------------------------
    // LOAD / CHECK Profs
    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    ProfDTO profDTOX = (ProfDTO) profDAO.loadById(1);
    // XXX: Not needed anymore, it loads lazy on request
    //profDAO.loadDeptRef(profDTOX);
    assertTrue(compareDTODeep(profDTOa, profDTOX));

    ProfDTO profDTOY = (ProfDTO) profDAO.loadById(2);
    // XXX: Not needed anymore, it loads lazy on request
    //profDAO.loadDeptRef(profDTOY);
    assertTrue(compareDTODeep(profDTOb, profDTOY));

    ProfDTO profDTOZ = (ProfDTO) profDAO.loadById(3);
    // XXX: Not needed anymore, it loads lazy on request
    //profDAO.loadDeptRef(profDTOZ);
    assertTrue(compareDTODeep(profDTOc, profDTOZ));

    ProfDTO profDTOW = (ProfDTO) profDAO.loadById(4);
    // XXX: Not needed anymore, it loads lazy on request
    //profDAO.loadDeptRef(profDTOW);
    assertTrue(compareDTODeep(profDTOd, profDTOW));

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }

  // --------------------------------------------------------------------------------

  public void testRefCrash() throws Exception {
    ConnectionBean connectionBean;

    DeptDAO deptDAO;
    ProfDAO profDAO;

    FactoryDAO fd = AbstractFactoryDAO.getFactoryDAO();

    // ----------------------------------------

    connectionBean = ConnectionFactory.getConnectionBean();

    // ----------------------------------------
    // INSERT Depts
    // ----------------------------------------

    deptDAO = (DeptDAO) fd.getDAO( //
        DeptDAO.class, connectionBean);

    deptDAO.createTable();

    DeptDTO deptDTO = (DeptDTO) fd.getDTO( //
        DeptDTO.class, connectionBean);

    deptDTO.setDeptAtt1("DeptAtt1_111");
    deptDTO.setDeptAtt2("DeptAtt2_111");
    deptDAO.insert(deptDTO);
    assertEquals(deptDTO.getId(), 1);

    // ----------------------------------------
    // INSERT Profs
    // ----------------------------------------

    profDAO = (ProfDAO) fd.getDAO( //
        ProfDAO.class, connectionBean);

    profDAO.createTable();

    // d1 -> e1
    ProfDTO profDTO = (ProfDTO) fd.getDTO( //
        ProfDTO.class, connectionBean);

    profDTO.setProfAtt1("ProfAtt1_111");
    profDTO.setProfAtt2("ProfAtt2_111");
    profDTO.setDeptDTORef(deptDTO); // ----------> LINK
    deptDTO.getProfDTOList().add(profDTO); // -----> LINK
    profDAO.insert(profDTO);
    assertEquals(profDTO.getId(), 1);

    ConnectionFactory.closeConnection(connectionBean.getConnection());
  }
}
