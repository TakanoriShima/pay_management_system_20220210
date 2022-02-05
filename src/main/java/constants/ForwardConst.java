package constants;

/**
 * 画面遷移に関わる値を定義する定数クラス
 *
 */

public enum ForwardConst {

    //action
    ACT("action"),
    ACT_AUTH("Auth"),

    //command
    CMD("command"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_LOGIN("login/login");

    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private ForwardConst(final String text) {
        this.text = text;
    }

    /**
     * 文字列取得
     */
    public String getValue() {
        return this.text;
    }
}
