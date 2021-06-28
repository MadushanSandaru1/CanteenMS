package uor.fot.canteenMS.helpers;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public class Encription {
    public  static String sha1(String pwd)
    {
        String sha1 = null;
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(pwd.getBytes("UTF-8"),0,pwd.length());
            sha1 = DatatypeConverter.printHexBinary(messageDigest.digest());

        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return sha1;
    }
}
