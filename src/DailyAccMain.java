

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dailyaccount.dao.DailyAccountManager;
import dailyaccount.dao.InExLookupManager;
import dailyaccount.exceptions.UnwantedStringException;

/**
 * Servlet implementation class Main
 */
public class DailyAccMain extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DailyAccMain() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Author : Kishan B <kishancs46@gmail.com>
	 * Comments
	 * Terminal for all operations on the system
	 * 
	 *    
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InExLookupManager InExLookup=new InExLookupManager();
		DailyAccountManager dailyAccMgr=new DailyAccountManager();
		PrintWriter writer=response.getWriter();
		try{
			InExLookup.addIncOrExpType(request.getParameter("addType"));
			InExLookup.updIncOrExpType(request.getParameter("updType"));
			dailyAccMgr.addInEx(request.getParameter("addInEx"));
			if(request.getParameter("getLog")!=null){
				response.setContentType("text/xml");
				writer.write(dailyAccMgr.getLog());
			}
			if(request.getParameter("getLookup")!=null){
				response.setContentType("text/xml");
				writer.write(InExLookup.getLookup(request.getParameter("getLookup")));
			}
		} catch (UnwantedStringException e) {
			writer.write("Wrong Request!");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

