package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.UserView;
import constants.MessageConst;
import services.UserService;

/**
 * ユーザーインスタンスに設定されている値のバリデーションを行う
 *
 */
public class UserValidator {

    public static List<String> validate(
            UserService service, UserView uv, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag){

        //各項目でバリデーションを行い、未入力であればエラーメッセージを格納
        List<String> errors = new ArrayList<String>();

        //メールアドレスのチェック
        String emailError = validateEmail(service, uv.getEmail(), codeDuplicateCheckFlag);
        //エラーメッセージがあれば、errorsにメッセージを格納
        if(!emailError.equals("")) {
            errors.add(emailError);
        }

        //名前のチェック
        String nameError = validateName(uv.getName());
        //エラーメッセージがあれば、errorsにメッセージを格納
        if(!nameError.equals("")) {
            errors.add(nameError);
        }

        //パスワードのチェック
        String passwordError = validatePassword(uv.getPassword(), passwordCheckFlag);
        //エラーメッセージがあれば、errorsにメッセージを格納
        if(!passwordError.equals("")) {
            errors.add(passwordError);
        }

        return errors;
    }

    /**
     * メールアドレスの入力チェックを行い、未入力であればエラーメッセージを返却
     * @param service UserServiceのインスタンス
     * @param email メールアドレス
     * @param emailDuplicateCheckFlag メールアドレスの重複チェックを実施するかどうか(実施する:true, 実施しない:false)
     * @return
     */
    private static String validateEmail(UserService service, String email, Boolean emailDuplicateCheckFlag) {

        //入力がなければエラーメッセージを返却
        if(email == null || email.equals("")) {
            return MessageConst.E_NOEMAIL.getMessage();
        }

        if(emailDuplicateCheckFlag) {
            //メールアドレスの重複チェックを実施

            long usersCount = isDuplicateUser(service, email);

            //同一のメールアドレスが既に登録されている場合はエラーメッセージを返却
            if(usersCount > 0) {
                return MessageConst.E_USER_EMAIL_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * @param service UserServiceのインスタンス
     * @param email メールアドレス
     * @return ユーザーテーブルに登録されている同一メールアドレスのデータの件数
     */
    private static long isDuplicateUser(UserService service, String email) {

        //メールアドレスを条件にテーブルに登録されている同一アドレスの件数を変数に格納
        long usersCount = service.countByEmail(email);
        return usersCount;
    }

    /**
     * 名前に入力値があるかをチェックし、入力がなければエラーメッセージを返却
     * @param name 名前
     * @return エラーメッセージ (入力値があれば空文字)
     */
    private static String validateName(String name) {

        //名前が未入力ならエラーメッセージを返却
        if(name  == null || name.equals("")) {
            return MessageConst.E_NONAME.getMessage();
        }
        //値があれば、空文字を返す
        return "";
    }


    /**
     * パスワードの入力チェックを行い、エラーメッセージを返却
     * @param password  パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか（実施する:true, 実施しない:false）
     * @return
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施、かつ入力値がなければエラーメッセージを返却
        if(passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.E_NOPASSWORD.getMessage();
        }

        //問題がない場合は空文字を返却
        return "";
    }

}
