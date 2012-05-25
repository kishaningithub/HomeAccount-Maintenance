package dailyaccount.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dailyaccount.exceptions.UnwantedStringException;

public class DailyAccountManager {
	public void addInEx(String inExString) throws UnwantedStringException
	{
		System.out.println("inside addInEx addInEx(String inExString)");
		String [] InExParams;
		try{
			InExParams=inExString.split("_");
 	    }
		catch (NullPointerException e) {
			return;
		}
		if(InExParams.length!=5)
			throw new UnwantedStringException();
		addInEx(InExParams[0], InExParams[1], InExParams[2], InExParams[3], InExParams[4]);
		System.out.println("outside addInEx addInEx(String inExString)");
	}
	public void addInEx(String date,String InCode,String InAmount,String ExCode,String ExAmount ) throws UnwantedStringException
	{
		System.out.println("inside addInExaddInEx(String date,String InCode,String InAmount,String ExCode,String ExAmount");
		addInEx(date,InCode,InAmount);
		addInEx(date,ExCode,ExAmount);
		System.out.println("outside addInExaddInEx(String date,String InCode,String InAmount,String ExCode,String ExAmount");
	}
	public void addInEx(String date,String InExCode,String amount) throws UnwantedStringException
	{
		System.out.println("inside addInExaddInEx(String date,String InExCode,String amount)");
		int amt;// Just for a check of whether amt is 0
		if(InExCode==null||InExCode.trim().isEmpty())
			return;
		try{
			Integer.parseInt(InExCode);
			amt=Integer.parseInt(amount);
		}
		catch (NumberFormatException e) {
			throw new UnwantedStringException();
		}
		if(amt==0)
			return;
		if(date==null||date.trim().isEmpty())
			throw new UnwantedStringException();
		
		String query="insert into dailyaccount(income_expenditure_date,in_or_ex_code,amount) values(to_date(?,'DD-Mon-YYYY'),?,?)";
		Connection conn=null;
		PreparedStatement prepStmt=null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/Kishan$$44@localhost:1521/XE");
			prepStmt=conn.prepareStatement(query);
			prepStmt.setString(1, date);
			prepStmt.setString(2, InExCode);
			prepStmt.setString(3, amount);
			prepStmt.executeUpdate();
		} catch (SQLException e){
			//02291 Integrity constraint
			//1861 literal does not match format string
			if(e.getErrorCode()==1861||e.getErrorCode()==2291)
				throw new UnwantedStringException();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		System.out.println("outside addInExaddInEx(String date,String InExCode,String amount)");
	}
	public String getLog()
	{
		
		String query=" select d.income_expenditure_date,t.in_or_ex,t.in_ex_value,d.amount"+
		             " from DAILYACCOUNT d,income_expenditure_type t " +
		             " where d.in_or_ex_code=t.in_ex_code ";
		             //" and d.income_expenditure_date between to_date(?,'dd-mon-yyyy') and to_date(?,'dd-mon-yyyy')";
		String inExLog="<in_ex_log>";
		Connection conn=null;
		PreparedStatement prepStmt=null;
		ResultSet rs=null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/Kishan$$44@localhost:1521/XE");
			prepStmt=conn.prepareStatement(query);
			rs=prepStmt.executeQuery();
			while(rs.next())
				inExLog+="<element>" +
						 "<date>"+rs.getString(1)+"</date><inorex>"+rs.getString(2)+"</inorex>" +
						 "<typevalue>"+rs.getString(3)+"</typevalue>"+"<amount>"+rs.getString(4)+"</amount>" +
						 "</element>";
		} catch (SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		inExLog+="</in_ex_log>";
	 return inExLog;	
	}
	
}
