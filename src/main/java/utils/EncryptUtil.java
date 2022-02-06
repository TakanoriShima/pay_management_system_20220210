package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

/**
 * パスワードのハッシュ化を行うクラス
 *
 */

public class EncryptUtil {

    //入力されたパスワードとpepper文字列を連結した文字列をSHA-256関数でハッシュ化し、返却する
    public static String getPasswordEncrypt(String plainPass, String pepper) {
        String ret = "";

        if(plainPass != null && !plainPass.equals("")) {
            byte[] bytes;
            String password = plainPass + pepper;

            try {
            	//SHA-256でハッシュ化
                bytes = MessageDigest.getInstance("SHA-256").digest(password.getBytes());
                ret = DatatypeConverter.printHexBinary(bytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }



        }
        return ret;
    }

}
