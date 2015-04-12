package dailyaccount.dao;

import org.dailyaccount.dto.UserData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.vms.util.VmsSessionFactory;

public class UserMangager {
	SessionFactory sessionFactory=VmsSessionFactory.getSessionFactory();
	
	public UserMangager() 
	{
		if (sessionFactory==null)
			sessionFactory=new Configuration().configure().buildSessionFactory();
	}
	// Cannot login after forgot password is clicked
	public Boolean Authenticate(String emailId,String password)
	
	{
		UserData userData=new UserData(emailId,password);
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		UserData retrievedUserData=(UserData)session.get(UserData.class,userData.getEmailId());
		session.close();
		return userData.equals(retrievedUserData);

	}
	public Boolean signUp(UserData userData)
	{
	   Session session=sessionFactory.openSession();
	   session.beginTransaction();
	   session.save(userData);
	   session.getTransaction().commit();
	   session.close();
	   return true;
	}
	

	public static void main(String[] args) {
//      Connection limit test 
//		Integer i=0;
//		while(true)
//         {
//          System.out.println(i);
//		  System.out.println(new UserMangager().Authenticate("b)_yahoo", "newPass"));
//		  i++;
//         }
		UserData userData=new UserData("b_kisha_n@yahoo.com","newPass");
		new UserMangager().signUp(userData);
		
		
	}
	
}
