package constants;

/**
 * DB関連の項目値をインターフェースとして定義
 *
 *
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "pay_management_system";

    //ユーザーテーブル
    String TABLE_USER = "users";  //テーブル名
    //ユーザーテーブルカラム
    String USER_COL_ID="id";  //id
    String USER_COL_NAME="name";  //名前
    String USER_COL_EMAIL="email";  //メールアドレス
    String USER_COL_PASSWORD="password";  //パスワード

    //Entity名
    String ENTITY_USER = "user";

    //JPQL内のパラメータ
    String JPQL_PARM_EMAIL = "email";
    String JPQL_PARM_PASSWORD = "password";

    //NamedQueryのnameとquery
    //全てのユーザーをidの降順に取得する
    String Q_USER_GET_ALL = ENTITY_USER + ".getAll";  //name
    String Q_USER_GET_ALL_DEF = "SELECT e FROM User AS e ORDER BY e.id DESC";  //query
    //全てのユーザーの件数を取得する
    String Q_USER_COUNT =  ENTITY_USER + ".count";  //name
    String Q_USER_COUNT_DEF = "SELECT COUNT(e) FROM User AS e";  //query
    //メールアドレスとハッシュ化済みパスワードを条件にユーザーを取得
    String Q_USER_GET_BY_EMAIL_AND_PASSWORD = ENTITY_USER + "getByCodeAndPassword";
    String Q_USER_GET_BY_EMAIL_AND_PASSWORD_DEF = "SELECT e FROM User AS e WHERE e.email = :" + JPQL_PARM_EMAIL + " AND e.password = :" + JPQL_PARM_PASSWORD;
  //メールアドレスを条件にユーザーを取得
    String Q_USER_GET_BY_EMAIL = ENTITY_USER + ".getByEmail";
    String Q_USER_GET_BY_EMAIL_DEF = "SELECT e FROM User AS e WHERE e.email = :" + JPQL_PARM_EMAIL;
    //指定したemailを保持するユーザーの件数を取得
    String Q_USER_COUNT_RESISTERED_BY_EMAIL = ENTITY_USER + ".countRegisteredByCode";
    String Q_USER_COUNT_RESISTERED_BY_EMAIL_DEF = "SELECT COUNT(e) FROM User AS e WHERE e.email = :" + JPQL_PARM_EMAIL;
}
