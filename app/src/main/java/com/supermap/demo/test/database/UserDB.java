package com.supermap.demo.test.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserDB {
    @Id(autoincrement = true)
    private Long id;

    public String userId;

    public String userName;

    public String passWord;


    public UserDB(String userId, String userName, String passWord) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
    }


    @Generated(hash = 2096115468)
    public UserDB(Long id, String userId, String userName, String passWord) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
    }


    @Generated(hash = 1312299826)
    public UserDB() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDB{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord +
                '}';
    }


}
