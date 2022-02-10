package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import models.validators.UserValidator;
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

		putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
		putRequestScope(AttributeConst.USER, new UserView()); //空のユーザーインスタンス

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
		if (checkToken()) {

			//パラメータの値を元にユーザー情報のインスタンスを作成する
			UserView uv = new UserView(
					null,
					getRequestParam(AttributeConst.USER_NAME),
					getRequestParam(AttributeConst.USER_EMAIL),
					getRequestParam(AttributeConst.USER_PASSWORD));

			//アプリケーションスコープからpepper文字列を取得
			String pepper = getContextScope(PropertyConst.PEPPER);

			//ユーザー情報登録
			List<String> errors = service.create(uv, pepper);

			//登録中にエラーがあった場合
			if (errors.size() > 0) {

				putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
				putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
				putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

				//新規登録画面を再表示
				forward(ForwardConst.FW_USER_NEW);

			} else {
				//登録中にエラーがなかった場合

				//セッションに登録完了のフラッシュメッセージを設定
				putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

				//一覧画面にリダイレクト
				redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);
			}
		}
	}

	/**
	 * パスワード変更画面の表示
	 * @throws ServletException
	 * @throws IOException
	 */
	public void edit() throws ServletException, IOException {

		putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
		putSessionScope(AttributeConst.USER, new UserView()); //空のユーザーインスタンス

		//編集画面を表示する
		forward(ForwardConst.FW_USER_EDIT);
	}

	public void update() throws ServletException, IOException {

		//CSRF対策 tokenのチェック
		if (checkToken()) {

			// 空のエラー配列を作成
			List<String> errors = new ArrayList<>();

			// JSPで入力された値を取得
			String email = getRequestParam(AttributeConst.USER_EMAIL);
			String name = getRequestParam(AttributeConst.USER_NAME);
			String plainPass = getRequestParam(AttributeConst.USER_PASSWORD);
			String pepper = getContextScope(PropertyConst.PEPPER);

			// 入力されたメールアドレスを持つ UserViewインスタンスを探す
			UserView uv = service.findOneByEmail(email);
			System.out.println("見つけたユーザー" + uv);

			// そんなメールアドレスを持つユーザーがいなければ
			if (uv == null) {
				errors.add("そのメールアドレスを持った人はいません");
				System.out.println("いないユーザー" + uv);
			} else {
				// uvのフィールド値を変更
				if(!name.equals("")) {
					uv.setName(name);
				}
				uv.setPassword(plainPass);
				// 入力値の検証
				errors = UserValidator.validate(service, uv, false, true);
			}

			// 入力がすべて正しくおこなわれていれば
			if (errors.size() == 0) {

				service.update(uv, pepper);
				//セッションに更新完了のフラッシュメッセージを設定
				putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATE.getMessage());

				//ログイン画面にリダイレクト
				redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);
			} else {

				//更新中にエラーが発生した場合

				putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
				putRequestScope(AttributeConst.USER, uv); //入力されたユーザー情報
				putRequestScope(AttributeConst.ERR, errors); //エラーのリスト
				System.out.println("再入力");
				//編集画面を再表示
				forward(ForwardConst.FW_USER_EDIT);
			}

		}
	}

}
