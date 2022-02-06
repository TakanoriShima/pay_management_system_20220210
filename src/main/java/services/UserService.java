package services;

import java.util.List;

import actions.views.UserConverter;
import actions.views.UserView;
import constants.JpaConst;
import models.User;
import models.validators.UserValidator;
import utils.EncryptUtil;

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

    /**
     * メールアドレスを条件に該当するデータの件数を取得し、返却する
     * @param email
     * @return
     */
    public long countByEmail(String email) {

        //指定したメールアドレスを保持するユーザーの件数を取得
        long users_count = (long) em.createNamedQuery(JpaConst.Q_USER_COUNT_RESISTERED_BY_EMAIL, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMAIL, email)
                .getSingleResult();
        return users_count;

    }

    /**
     * メールアドレス、パスワードの条件に取得したデータをUserViewインスタンスで返却する
     * @param email メールアドレス
     * @param plainPass パスワード文字列
     * @param pepper pepper文字列
     * @return 取得したデータのインスタンス 取得できない場合はnullで返す
     */
    public UserView findOne(String email, String plainPass, String pepper) {
        User u = null;

        //パスワードのハッシュ化
        String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

        //メールアドレスとハッシュ化済みパスワードを条件にユーザーを一件取得
        u = em.createNamedQuery(JpaConst.Q_USER_GET_BY_EMAIL_AND_PASSWORD, User.class)
                .setParameter(JpaConst.JPQL_PARM_EMAIL, email)
                .setParameter(JpaConst.JPQL_PARM_PASSWORD, pass)
                .getSingleResult();

        return UserConverter.toView(u);

    }

    /**
     * idを条件に取得したデータをUserViewのインスタンスで返却する
     * @param id
     * @return  取得データのインスタンス
     */
    public UserView findOne(int id) {
        User u = findOneInternal(id);
        return UserConverter.toView(u);
    }

    /**
     * idを条件にデータを一件取得し、Userインスタンスで返却
     * @param id
     * @return 取得データのインスタンス
     */
    public User findOneInternal(int id) {
        User u = em.find(User.class, id);
        return u;
    }

    /**
     * 画面から入力されたユーザーの登録内容を元にデータを一件作成し、ユーザーテーブルに登録する
     * @param uv  画面から入力されたユーザーの登録内容
     * @param pepper  pepper文字列
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(UserView uv, String pepper){

        //パスワードをハッシュ化して設定
        String pass = EncryptUtil.getPasswordEncrypt(uv.getPassword(), pepper);
        uv.setPassword(pass);

        //登録内容のバリデーションを行う
        List<String> errors = UserValidator.validate(this, uv, true, true);

        //バリデーションエラーがなければデータを登録
        if(errors.size() == 0) {
            create(uv);
        }

        //エラーがあれば該当メッセージを返却
        return errors;
    }

    public List<String> update(UserView uv, String pepper){

        List<String> errors = null;

        //idを条件に登録済みのユーザー情報を取得
        UserView savedUser = findOne(uv.getId());

        boolean validateEmail = false;
        //メールアドレスが登録されているものとあっていればパスワードを変更
        if(!savedUser.getEmail().equals(uv.getEmail())) {

            boolean validatePass  = false;
            if(uv.getPassword() != null && uv.getPassword().equals("")) {
                //パスワードに入力がある場合

                //パスワードについてのバリデーションを行う
                validatePass = true;

                //変更後のパスワードをハッシュ化し設定する
                savedUser.setPassword(
                        EncryptUtil.getPasswordEncrypt(uv.getPassword(), pepper));

                savedUser.setName(uv.getName());

                //更新内容にバリデーションを行う
                errors  = UserValidator.validate(this, savedUser, validateEmail, validatePass);

                //バリデーションエラーがなければデータを更新する
                if(errors.size() == 0) {
                    update(savedUser);

                  //エラーを返却
                    return errors;

                }
            }
        }
        //
        return errors;
    }

    public Boolean validateLogin(String email, String plainPass, String pepper) {

        boolean isValidUser = false;
        //メールアドレスとパスワードが未入力でない場合
        if(email != null && !email.equals("") && plainPass != null && !plainPass.equals("")){
            //メールアドレス、パスワードを条件に取得したデータをUserViewのインスタンスで返却
            UserView uv = findOne(email, plainPass, pepper);

            if(uv != null && uv.getId() != null) {

                //データが認証できた場合、認証成功
                isValidUser = true;
            }
        }
        //認証結果を返却する
        return isValidUser;
    }

    /**
     * ユーザーデータを一件登録する
     * @param uv ユーザーデータ
     */
    private void create(UserView uv) {

        em.getTransaction().begin();
        em.persist(UserConverter.toModel(uv));
        em.getTransaction().commit();
    }

    /**
     * ユーザーデータを更新する
     * @param uv  画面から入力されたユーザーの登録内容
     */
    private void update(UserView uv) {

        em.getTransaction().begin();
        User u =  findOneInternal(uv.getId());
        UserConverter.copyViewToModel(u, uv);
        em.getTransaction().commit();
    }



}
