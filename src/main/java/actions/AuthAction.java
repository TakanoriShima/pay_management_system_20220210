package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.UserService;

/**
 * 認証に関する処理を行うActionクラス
 *
 */

public class AuthAction extends ActionBase {

    private UserService service;

    /**
     * メソッドの実行
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new UserService();

        //メソッドの実行
        invoke();

        service.close();
    }

    /**
     * ログイン画面を表示
     * @throws ServletException
     * @throws IOException
     */
    public void showLogin() throws ServletException, IOException {

        //CSRF対策用トークンの設定
        putRequestScope(AttributeConst.TOKEN, getTokenId());

        //セッションにフラッシュメッセージが登録されている場合はリクエストスコープに設定
        String flush = getSessionScope(AttributeConst.FLUSH);
        if(flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        forward(ForwardConst.FW_LOGIN);
    }

    public void login() throws ServletException, IOException {

        String email = getRequestParam(AttributeConst.USER_EMAIL);
        String plainPass = getRequestParam(AttributeConst.USER_PASSWORD);
        String pepper = getContextScope(PropertyConst.PEPPER);

        //有効な従業員か認証する
        Boolean isValidUser = service.validateLogin(email, plainPass, pepper);

        if(isValidUser) {

            //CSRF対策 tokenのチェック
            if(checkToken()) {

                //認証成功の場合

                //ログインしたユーザーのDBデータを取得
                UserView uv = service.findOne(email, plainPass, pepper);
                //セッションにログインしたユーザーを設定
                putSessionScope(AttributeConst.LOGIN_USER, uv);
                //セッションにログイン完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_LOGINED.getMessage());
                //トップページにリダイレクト
                redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
            }
        }else {
            //認証失敗の場合

            //CSRF対策用トークン
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            //認証失敗エラーメッセージ表示フラグに立てる
            putRequestScope(AttributeConst.LOGIN_ERR, true);
            //入力されたユーザーコードを設定
            putRequestScope(AttributeConst.USER_EMAIL, email);

            //ログイン画面を表示
            forward(ForwardConst.FW_LOGIN);
        }
    }

}
