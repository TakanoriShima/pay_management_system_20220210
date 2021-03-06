package constants;

/**
 * 画面遷移に関わる値を定義する定数クラス
 *
 */

public enum ForwardConst {

    //action
    ACT("action"),
    ACT_AUTH("Auth"),
    ACT_USER("User"),
    ACT_TOP("Top"),
    ACT_PAYMENT("Payment"),


    //command
    CMD("command"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_INDEX("index"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_UPDATE("update"),
    CMD_EDIT("edit"),
    CMD_SHOW_LOGIN("showLogin"),
    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_USER_NEW("users/new"),
    FW_USER_EDIT("users/edit"),
    FW_LOGIN("login/login"),
    FW_TOP_INDEX("topPage/index");

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
