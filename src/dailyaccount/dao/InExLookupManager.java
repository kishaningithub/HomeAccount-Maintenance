package dailyaccount.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dailyaccount.exceptions.UnwantedStringException;

public class InExLookupManager {

	String lookupXML;
	public InExLookupManager() 
	{
		lookupXML="";
	}
	public String getLookup(String whichLookup) throws UnwantedStringException
	{
		try{
			if(whichLookup.equals("e"))
				lookupXML=getExpenseLookup();
			else if(whichLookup.equals("i"))
				lookupXML=getIncomeLookup();
			else
				throw new UnwantedStringException();
		}catch (NullPointerException e) {
			lookupXML="";
		}
		return lookupXML;
		
	}
	public String getExpenseLookup()
	{
		String query="select in_ex_code,in_ex_value from income_expenditure_type where in_or_ex='e'";
		String expenseComboXML="<expense_combo>";
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/Kishan$$44@localhost:1521/XE");
			stmt=conn.prepareStatement(query);
			rs=stmt.executeQuery();
			while(rs.next()){
				expenseComboXML+="<element><code>"+rs.getString(1)+"</code><value>"+rs.getString(2)+"</value></element>";
			
			}
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
		expenseComboXML+="</expense_combo>";
		return expenseComboXML;
	}
	public String getIncomeLookup()
	{
		String query="select in_ex_code,in_ex_value from income_expenditure_type where in_or_ex='i'";
		String incomeComboXML="<income_combo>";
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/Kishan$$44@localhost:1521/XE");
			stmt=conn.prepareStatement(query);
			rs=stmt.executeQuery();
			while(rs.next())
				incomeComboXML+="<element><code>"+rs.getString(1)+"</code><value>"+rs.getString(2)+"</value></element>";
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
		incomeComboXML+="</income_combo>";
		return incomeComboXML;
	}
	public void addIncOrExpType(String inExTypeValue) throws UnwantedStringException
	{
		String[] inExTypeValues;
		try{
		 inExTypeValues=inExTypeValue.split("_");
		}
		catch (NullPointerException e) {
	        return;
		}
		if(inExTypeValues.length!=2)
			throw new UnwantedStringException();
		addIncOrExpType(inExTypeValues[0],inExTypeValues[1]);
	}
	public void addIncOrExpType(String InOrEx,String typeValue) throws UnwantedStringException
	{
		
		if(InOrEx==null||typeValue==null)
			return;
		if(InOrEx.trim().isEmpty()||typeValue.trim().isEmpty()||!(InOrEx.equals("e")||InOrEx.equals("i")))
			throw new UnwantedStringException();
		String query="insert into income_expenditure_type(in_or_ex,in_ex_code,in_ex_value) values(?,genTypeCode.nextval,?)";
		Connection conn=null;
		PreparedStatement prepStmt=null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/Kishan$$44@localhost:1521/XE");
			prepStmt=conn.prepareStatement(query);
			prepStmt.setString(1, InOrEx);
			prepStmt.setString(2, typeValue);
			prepStmt.executeUpdate();
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
	}
	public void addExpenseType(String expenseType) throws UnwantedStringException
	{
		addIncOrExpType("e",expenseType);
		
	}
	public void addIncomeType(String incomeType) throws UnwantedStringException
	{
		addIncOrExpType("i",incomeType);
	}
	public void updIncOrExpType(String inExTypeValue) throws UnwantedStringException
	{
		String[] inExTypeValues=null;
		try{
		inExTypeValues=inExTypeValue.split("_");
		}
		catch (NullPointerException e) {
			return;
		}
		if(inExTypeValues.length!=2)
			throw new UnwantedStringException();
		addIncOrExpType(inExTypeValues[0],inExTypeValues[1]);
	}
	public void updIncOrExpType(String InOrExCode, String InOrExValue) throws UnwantedStringException 
	{
		if(InOrExCode==null||InOrExValue==null)
			return;
		if(InOrExCode.trim().isEmpty()||InOrExValue.trim().isEmpty())
			throw new UnwantedStringException();
		String query="update income_expenditure_type set in_ex_value=? where in_ex_code=?";
		Connection conn=null;
		PreparedStatement prepStmt=null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/Kishan$$44@localhost:1521/XE");
			prepStmt=conn.prepareStatement(query);
			prepStmt.setString(1, InOrExValue);
			prepStmt.setString(2, InOrExCode);
			if(prepStmt.executeUpdate()==0){
				throw new UnwantedStringException();
			}
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
	}
	public static void main(String[] args) throws UnwantedStringException {
		System.out.println(" _ ".split("_").length);
		System.out.println(" _ ".split("_")[0]);
		new InExLookupManager().addIncOrExpType(" _ ");
	}
	
}
