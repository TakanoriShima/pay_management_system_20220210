package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {

    @Override
    public void process() throws ServletException, IOException {

        //メソッドの実行
        invoke();
    }

    /**
     * 一覧画面を表示
     * @throws IOException
     * @throws ServletException
     */

    public void index() throws ServletException, IOException {

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替えて、セッションからは削除
        String flush = getSessionScope(AttributeConst.FLUSH);
        if(flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面の表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}
