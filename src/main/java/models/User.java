package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 会員データのDTOモデル
 *
 */

@Table(name = JpaConst.TABLE_USER)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_USER_GET_ALL,
            query = JpaConst.Q_USER_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_USER_COUNT,
            query = JpaConst.Q_USER_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_USER_COUNT_RESISTERED_BY_EMAIL,
            query = JpaConst.Q_USER_COUNT_RESISTERED_BY_EMAIL_DEF),
    @NamedQuery(
            name = JpaConst.Q_USER_GET_BY_EMAIL_AND_PASSWORD,
            query = JpaConst.Q_USER_GET_BY_EMAIL_AND_PASSWORD_DEF),
    @NamedQuery(
            name = JpaConst.Q_USER_GET_BY_EMAIL,
            query = JpaConst.Q_USER_GET_BY_EMAIL_DEF)
})

@Getter  //全てのクラスフィールドのgetterを自動生成　
@Setter  //全てのクラスフィールドのSetterを自動生成　
@NoArgsConstructor  //引数なしコンストラクタを自動生成
@AllArgsConstructor  //全てのクラスフィールドを引数にもつコンストラクタを自動生成
@Entity

public class User {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.USER_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名前
     */
    @Column(name = JpaConst.USER_COL_NAME, nullable=false)
    private String name;

    /**
     * メールアドレス
     */
    @Column(name = JpaConst.USER_COL_EMAIL, nullable=false, unique = true)
    private String email;

    /**
     * パスワード
     */
    @Column(name = JpaConst.USER_COL_PASSWORD, length = 64, nullable=false)
    private String password;




}
