package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.UserService;

public class UserAction extends ActionBase {

    private UserService service;
    @Override
    public void process() throws ServletException, IOException {

        service = new UserService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId());  //CSRF対策用トークン
        putRequestScope(AttributeConst.USER, new UserView());  //空のユーザーインスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_USER_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if(checkToken()) {

            //パラメータの値を元にユーザー情報のインスタンスを作成する
            UserView uv  = new UserView(
                    null,
                    getRequestParam(AttributeConst.USER_NAME),
                    getRequestParam(AttributeConst.USER_EMAIL),
                    getRequestParam(AttributeConst.USER_PASSWORD));

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //ユーザー情報登録
            List<String> errors = service.create(uv, pepper);

            //登録中にエラーがあった場合
            if(errors.size() > 0) {

                putRequestScope(AttributeConst.TOKEN, getTokenId());  //CSRF対策用トークン
                putRequestScope(AttributeConst.USER, uv);  //入力されたユーザー情報
                putRequestScope(AttributeConst.ERR, errors);  //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_USER_NEW);

            }else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);
            }
        }
    }

}
