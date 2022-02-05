package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
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

        forward(ForwardConst .FW_LOGIN);
    }

}
