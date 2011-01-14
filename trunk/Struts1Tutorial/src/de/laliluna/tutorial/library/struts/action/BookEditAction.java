package de.laliluna.tutorial.library.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import de.laliluna.tutorial.library.SimulateDB;
import de.laliluna.tutorial.library.struts.form.BookEditForm;

/**
 * @author laliluna
 */
public class BookEditAction extends DispatchAction {

  public BookEditAction() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public ActionForward editBook( //
      ActionMapping mapping, ActionForm form, //
      HttpServletRequest request, HttpServletResponse response) {

    BookEditForm bookEditForm = (BookEditForm) form;

    long id = Long.parseLong(request.getParameter("id"));

    SimulateDB simulateDB = new SimulateDB();
    bookEditForm.setBook(simulateDB.loadBookById(id, request.getSession()));

    return mapping.findForward("showEdit");
  }

  // --------------------------------------------------------------------------------

  public ActionForward deleteBook( //
      ActionMapping mapping, ActionForm form, //
      HttpServletRequest request, HttpServletResponse response) {

    // BookEditForm bookEditForm = (BookEditForm) form;

    long id = Long.parseLong(request.getParameter("id"));

    SimulateDB simulateDB = new SimulateDB();
    simulateDB.deleteBookById(id, request.getSession());

    return mapping.findForward("showList");
  }

  // --------------------------------------------------------------------------------

  public ActionForward addBook( //
      ActionMapping mapping, ActionForm form, //
      HttpServletRequest request, HttpServletResponse response) {

    // BookEditForm bookEditForm = (BookEditForm) form;

    return mapping.findForward("showAdd");
  }

  // --------------------------------------------------------------------------------

  public ActionForward saveBook( //
      ActionMapping mapping, ActionForm form, //
      HttpServletRequest request, HttpServletResponse response) {

    BookEditForm bookEditForm = (BookEditForm) form;

    SimulateDB simulateDB = new SimulateDB();
    simulateDB.saveToDB(bookEditForm.getBook(), request.getSession());

    return mapping.findForward("showList");
  }
}
