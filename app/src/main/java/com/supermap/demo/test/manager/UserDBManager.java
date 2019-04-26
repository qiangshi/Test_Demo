package com.supermap.demo.test.manager;

import android.text.TextUtils;

import com.supermap.demo.test.AppLike;
import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.database.UserDB;
import com.supermap.demo.test.database.UserDBDao;
import com.supermap.demo.test.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zenghaiqiang on 2019/4/7.
 * 描述：用户相关数据表管理类
 */
public class UserDBManager {
    private static String userId;
    public static UserDB userDB = null;

    public static UserDBManager getInstance() {
        userId = SharePreferenceUtil.getString(Constant.USER_ID, "");
        return Holder.Instance;
    }

    /**
     * 获取userId
     */
    public String getUserId() {
        userId = SharePreferenceUtil.getString(Constant.USER_ID, "");
        return userId;
    }

    /**
     * 获取UserInfo
     */
    public UserDB getUserInfo() {
        userId = getUserId();
        UserDB userDB = queryUserDB(userId);
        if (userDB != null) return userDB;
        return null;
    }

    public List<UserDB> queryPasswordByUserName(String userName) {
        List<UserDB> userDBS = new ArrayList<>();
        userDBS = AppLike.getDaoInstance().getUserDBDao().queryBuilder().where(UserDBDao.Properties.UserName.eq(userName)).list();
        return userDBS;
    }


    /**
     * 更新用户信息
     *
     * @param mUserInfo
     */
    public void updateUserInfo(UserDB mUserInfo) {
        if (mUserInfo == null) return;
        AppLike.getDaoInstance().getUserDBDao().update(mUserInfo);
    }


    public void inserUserListInfo(List<UserDB> list) {
        if (list == null && list.size() <= 0) return;
        else AppLike.getDaoInstance().getUserDBDao().insertInTx(list);
    }

    /**
     * 获取用户密码(加密的)
     */
    public String getUserPassword() {
        String userPassword = "";
        UserDB mUserInfo = getUserInfo();
        if (mUserInfo != null) userPassword = mUserInfo.getPassWord();
        return userPassword;
    }

    /**
     * 更新用户密码
     *
     * @param userPassword 新密码
     */
    public void updateUserPassword(String userPassword) {
        UserDB userDB = queryUserDB(userId);
        if (userDB != null) {
            userDB.setPassWord(userPassword);
            AppLike.getDaoInstance().getUserDBDao().update(userDB);
        }
    }


    /**
     * 根据userId查询user对应的userdb
     *
     * @param userId
     */
    public UserDB queryUserDB(String userId) {
        if (userDB == null) {
            if (!TextUtils.isEmpty(userId)) {
                List<UserDB> listUserDB = queryListUserDB(userId);
                if (listUserDB.size() > 0) userDB = listUserDB.get(0);
            }
        }
        return userDB;
    }

    /**
     * 根据userid获取对应的user数据
     *
     * @param userId
     * @return
     */
    public List<UserDB> queryListUserDB(String userId) {
        AppLike.getDaoInstance().clear();
        List<UserDB> listUserDB = new ArrayList<>();
        if (!TextUtils.isEmpty(userId))
            listUserDB = AppLike.getDaoInstance().getUserDBDao().queryBuilder().where(UserDBDao.Properties.UserId.eq(userId)).list();
        return listUserDB;
    }


    private static class Holder {
        private static final UserDBManager Instance = new UserDBManager();
    }
}
