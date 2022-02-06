package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
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
    
    public void entryNew() throws ServletException, IOException {
    	
    	putRequestScope(AttributeConst.TOKEN, getTokenId());  //CSRF対策用トークン
    	putRequestScope(AttributeConst.USER, new UserView());  //空のユーザーインスタンス
    	
    	//新規登録画面を表示
    	forward(ForwardConst.FW_USER_NEW);
    }

}
