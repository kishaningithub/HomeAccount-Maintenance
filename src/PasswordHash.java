import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;
public final class PasswordHash
{
  private static PasswordHash instance;
  private PasswordHash()
  {
  }
  public synchronized String encrypt(String plaintext)
  {
    MessageDigest md = null;
    try
    {
      md = MessageDigest.getInstance("SHA"); //step 2
    }
    catch(NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    try
    {
      md.update(plaintext.getBytes("UTF-8")); //step 3
    }
    catch(UnsupportedEncodingException e)
    {
    	e.printStackTrace();
    }
    byte raw[] = md.digest(); 
    String hash = (new BASE64Encoder()).encode(raw); //step 5
    return hash; //step 6
  }
  public static synchronized PasswordHash getInstance() //step 1
  {
    if(instance == null)
    {
      return new PasswordHash();
    }
    else    
    {
      return instance;
    }
  }
  public static void main(String[] args) {
	System.out.println(PasswordHash.getInstance().encrypt("Kishan"));
}
}

