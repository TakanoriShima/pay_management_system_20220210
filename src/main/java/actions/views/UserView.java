package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ユーザー情報について画面の入力値・出力値を扱うViewモデル
 *
 */

@Getter //全てのクラスフィールドのGetterを自動作成
@Setter  //全てのクラスフィールドのSetterを自動作成
@NoArgsConstructor //引数なしコンストラクタを自動作成
@AllArgsConstructor  //全てのクラスフィールドを引数にもつコンストラクタを自動作成
public class UserView {

    /**
     * id
     */
    private Integer id;

    /**
     * 名前
     */
    private String name;

    /**
     * メールアドレス
     */
    private String email;

    /**
     * パスワード
     */
    private String password;
}
