package dailyaccount.action;

import dailyaccount.dao.InputDAO;


public class Input {
	public void writeAccount(Float amountSpentToday)
	{
		new InputDAO().writeAccount(amountSpentToday);
	}
	
}
