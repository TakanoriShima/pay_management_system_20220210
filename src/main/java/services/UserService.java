package services;

import constants.JpaConst;

/**
 * ユーザーテーブルの操作に関わる処理を行うクラス
 *
 */

public class UserService extends ServiceBase {

    /**
     * ユーザーテーブルnのデータの件数を取得し、返却する
     * @return ユーザーテーブルのデータの件数
     */
    public long countAll() {
        long userCount = (long) em.createNamedQuery(JpaConst.Q_USER_COUNT, Long.class)
                .getSingleResult();

        return userCount;
    }

    public long countByEmail(String email) {

        //指定したメールアドレスを保持するユーザーの件数を取得
        long users_count = (long) em.createNamedQuery(JpaConst.Q_USER_COUNT_RESISTERED_BY_EMAIL, long.class)
                .setParameter(JpaConst.JPQL_PARM_EMAIL, email)
                .getSingleResult();
        return users_count;

    }


}
