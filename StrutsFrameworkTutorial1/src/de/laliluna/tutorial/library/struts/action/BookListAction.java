package de.laliluna.tutorial.library.struts.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.laliluna.tutorial.library.Book;
import de.laliluna.tutorial.library.SimulateDB;
import de.laliluna.tutorial.library.struts.form.BookListForm;

/**
 * @author laliluna
 */
public class BookListAction extends Action {

  public BookListAction() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) {

    // --------------------------------------------------------------------------------
    // El MVC (Struts) se encarga de instanciar el form según sea necesario
    // --------------------------------------------------------------------------------

    BookListForm bookListForm = (BookListForm) form;

    // --------------------------------------------------------------------------------
    // SimulateDB "Simula" una capa de persistencia o de negocio
    // --------------------------------------------------------------------------------

    SimulateDB simulateDB = new SimulateDB();
    Collection<Book> bookList = simulateDB.getAllBooks(request.getSession());
    bookListForm.setBooks(bookList);

    // --------------------------------------------------------------------------------
    // A que forward (vista / action) hace la redirección
    // --------------------------------------------------------------------------------

    return mapping.findForward("showList");
  }
}
