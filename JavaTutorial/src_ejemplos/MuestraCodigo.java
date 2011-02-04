/*
 * Copyright 2004 Minotauro C.A. Reservados todos los derechos.
 * Created on Jan 8, 2004
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author DMI: Demian Gutierrez
 */
public class MuestraCodigo extends HttpServlet
{
	/**
	 *
	 */
	public MuestraCodigo()
	{
		// Empty
	}

	/**
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");

		ServletContext servletContext = getServletContext();
		PrintWriter out = response.getWriter();
		InputStream is = servletContext.getResourceAsStream(request.getParameter("nombre"));

		out.println("<HTML><HEAD><TITLE>" + getClass().getName() + "</TITLE>");
		out.println("<LINK type=\"text/css\" href=\"./page.css\" rel=\"stylesheet\"></HEAD><BODY class=\"main\">");

		if (is != null)
		{
			out.println("<H2 class=\"title\">" + request.getParameter("nombre") + "</H2>");
			//			out.println("<TABLE border=\"0\" cellspacing=\"0\" cellpadding=\"10\">");
			out.println("<TABLE class=\"externals_table\" cellspacing=\"1\" cellpadding=\"10\" width=\"100%\">");

			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			Vector fuente = new Vector();

			String linea;

			while ((linea = rd.readLine()) != null)
			{
				linea = linea.replaceAll("\t", "     ");
				linea = linea.replaceAll(" ", "&nbsp;");
				linea = linea.replaceAll("<", "&lt;");
				linea = linea.replaceAll(">", "&gt;");

				fuente.add(linea);
			}

			NumberFormat nf = NumberFormat.getInstance();
			nf.setMinimumIntegerDigits(String.valueOf(fuente.size()).length());

			out.println("<TD class=\"externals_th\">");

			for (int i = 0; i < fuente.size(); i++)
			{
				out.println(nf.format(i) + "<BR>");
			}

			out.println("</TD><TD class=\"externals_td\">");

			Iterator itt = fuente.iterator();

			while (itt.hasNext())
			{
				out.println(itt.next() + "<BR>");
			}

			out.println("</TD>");
			out.println("</TABLE>");
		}
		else
		{
			out.println("<H2>No se encuentra " + request.getParameter("nombre") + "</H2>");
		}

		out.println("<P class=\"download\">");
		out.println("Descargar los ejemplos: ");
		out.println("<A href=\"FundamentosJava20.tar.gz\">tar.gz</A><BR>");
//		out.println("<A href=\"FundamentosJava20.zip\">zip</A> / <A href=\"FundamentosJava20.tar.gz\">tar.gz</A><BR>");
		out.println("</P>");

		out.println("<P class=\"logos_bar\">");
//		out.println("<A class=\"logos\" href=\"http://www.emana.net\"><IMG src=\"emana.gif\"></A>");
		out.println("<A class=\"logos\" href=\"http://www.minotauro.com.ve\"><IMG src=\"minotauro.gif\"></A>");
		out.println("</P>");
		out.println("<P class=\"copyright\">Copyright 2004 Minotauro C.A. Reservados todos los derechos.</P>");
		out.println("</BODY></HTML>");
	}
}
