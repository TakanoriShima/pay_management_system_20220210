package actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.PropertyConst;

/**
 * 各アクションクラスの親クラス。共通の処理を行う
 *
 */


public abstract class ActionBase {
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    /**
     * 初期化の設定
     * サーブレットコンテキスト、リクエスト、レスポンス
     * @param servletContext
     * @param servletRequest
     * @param servletResponse
     */
    public void init(
            ServletContext servletContext,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {
        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;
    }

    /**
     * フロントコントローラから呼び出されるメソッド
     * @throws IOException
     * @throws ServletException
     *
     */
    public abstract void process() throws ServletException, IOException;

    /**
     * クエリパラメータのcommandの値に該当するメソッドを実行
     * @throws IOException
     * @throws ServletException
     */
    protected void invoke() throws ServletException, IOException {

        Method commandMethod;


            try {
                //パラメータからcommandを取得
                String command = request.getParameter(ForwardConst.CMD.getValue());
                //commandに該当するメソッドを実行
                commandMethod = this.getClass().getDeclaredMethod(command, new Class[0]);
                commandMethod.invoke(this, new Object[0]);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                // 例外をコンソールに表示
                e.printStackTrace();
                //commandの値が不正で実行できない場合エラー画面を呼び出し
                forward(ForwardConst.FW_ERR_UNKNOWN);
            }

    }


    /**
     * @param target 遷移先jsp画面のファイル名
     * @throws ServletException
     * @throws IOException
     */
    protected void forward(ForwardConst target) throws ServletException, IOException {

        //jspファイルの相対パスを作成
        String forward = String.format("/WEB-INF/views/%s.jsp", target.getValue());
        RequestDispatcher  rd = request.getRequestDispatcher(forward);

        //jspファイルの呼び出し
        rd.forward(request, response);
    }

    /**
     * URLを構築してリダイレクトを行う
     * @param action パラメータに設定する値
     * @param command パラメータに設定する値
     * @throws IOException
     */
    protected void redirect(ForwardConst action, ForwardConst command) throws IOException {

        //リダイレクトするURLの作成
        String redirectUrl = request.getContextPath() + "/?action=" + action.getValue();
        //commandに値があった場合は先ほど作成したURLの後ろに追記する
        if(command != null) {
            redirectUrl = redirectUrl + "&command=" + command.getValue();
        }

        //作成したURLにリダイレクト
        response.sendRedirect(redirectUrl);
    }

    /**
     * CSRF対策 tokenが不正の亜愛はエラー画面を表示
     * @return true: tokenが有効 false: token不正 エラー画面表示
     * @throws ServletException
     * @throws IOException
     */
    protected boolean checkToken() throws ServletException, IOException {

        //パラメータからtokenの値を取得
        String _token = getRequestParam(AttributeConst.TOKEN);

        //もし_tokenが空もしくはセッションIDと異なる場合はエラー画面を表示
        if(_token == null || !(_token.equals(getTokenId()))) {
            forward(ForwardConst.FW_ERR_UNKNOWN);

            return false;
        }else {
            return true;
        }
    }

    /**
     * セッションIDを取得
     * @return セッションID
     */
    protected String getTokenId() {
        return request.getSession().getId();
    }

    /**
     * リクエストスコープから指定されたパラメータの値を取得し、返却する
     * @param key パラメータ名
     * @return パラメータの値
     */
    protected String getRequestParam(AttributeConst key) {
        return request.getParameter(key.getValue());
    }

    /**
     * 文字列を数値に変換する
     * @param strNumber 変換前文字列
     * @return 変換後数値
     */
    protected int toNumber(String strNumber) {
        int number = 0;
        number = Integer.parseInt(strNumber);

        return number;
    }

    /**
     * リクエストスコープにパラメータを設定
     * @param key パラメータ名
     * @param value パラメータの値（ジェネリクス）
     */
    protected <V> void putRequestScope(AttributeConst key, V value) {
        request.setAttribute(key.getValue(), value);
    }

    /**
     * セッションスコープから指定された名前のパラメータを取得し、返却する
     * @param key パラメータ名
     * @return パラメータの値（ジェネリクス）
     */
    @SuppressWarnings("unchecked")
    protected <R> R getSessionScope(AttributeConst key) {
        return (R) request.getSession().getAttribute(key.getValue());
    }

    /**
     * セッションスコープにパラメータを設定
     * @param key パラメータ名
     * @param value パラメータの値（ジェネリクス）
     */
    protected <V> void putSessionScope(AttributeConst key, V value) {
        request.getSession().setAttribute(key.getValue(), value);
    }

    /**
     * セッションスコープの指定された名前のパラメータを除去する
     * @param key パラメータ名
     */
    protected void removeSessionScope(AttributeConst key) {
        request.getSession().removeAttribute(key.getValue());
    }

    /**
     * アプリケーションスコープから指定されたパラメータの値を取得し、返却する
     * @param key パラメータ名
     * @return パラメータの値（ジェネリクス）
     */
    @SuppressWarnings("unchecked")
    protected <R> R getContextScope(PropertyConst key) {
        return (R)context.getAttribute(key.getValue());
    }
}
