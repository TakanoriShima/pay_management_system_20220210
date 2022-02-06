package constants;

/**
 * 各出力メッセージを定義
 *
 */

public enum MessageConst {
    //DB更新
    I_REGISTERED("登録が完了しました。"),
    //バリデーション
    E_NONAME("名前を入力してください"),
    E_NOEMAIL("メールアドレスを入力してください"),
    E_NOPASSWORD("パスワードを入力してください"),
    E_USER_EMAIL_EXIST("入力されたメールアドレス情報は既に存在しています");


    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private MessageConst(final String text) {
        this.text = text;
    }

    /**
     * 文字列を取得
     */
    public String getMessage() {
        return this.text;
    }

}
