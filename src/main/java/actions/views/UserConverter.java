package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.User;

/**
 * ユーザーデータのDTO↔︎Viewモデルの変換を行うクラス
 *
 */

public class UserConverter {


    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param uv UserViewのインスタンス
     * @return Userのインスタンス
     */
    public static User toModel(UserView uv) {

        return new User(
                uv.getId(),
                uv.getName(),
                uv.getEmail(),
                uv.getPassword());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param u Userのインスタンス
     * @return UserViewのインスタンス
     */
    public static UserView toView(User u) {

        if(u == null) {
            return null;
        }

        return new UserView(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getPassword());
    }

    /**
     * DTOモデルからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<UserView> toViewList(List<User> list){
        List<UserView> uvs = new ArrayList<>();

        for(User u : list) {
            uvs.add(toView(u));
        }

        return uvs;
    }
    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param u
     * @param uv
     */
    public static void copyViewToModel(User u, UserView uv) {
        u.setId(uv.getId());
        u.setName(uv.getName());
        u.setEmail(uv.getEmail());
        u.setPassword(uv.getPassword());
    }
}
