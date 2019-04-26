package com.supermap.demo.test.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FavoriteDB {

    @Id(autoincrement = true)
    private Long id;

    public String userId;

    public int favoriteType;

    public long saveTime;

    public String favoriteId;






    @Generated(hash = 387371353)
    public FavoriteDB(Long id, String userId, int favoriteType, long saveTime,
            String favoriteId) {
        this.id = id;
        this.userId = userId;
        this.favoriteType = favoriteType;
        this.saveTime = saveTime;
        this.favoriteId = favoriteId;
    }

    @Generated(hash = 120560402)
    public FavoriteDB() {
    }




    

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFavoriteType() {
        return this.favoriteType;
    }

    public void setFavoriteType(int favoriteType) {
        this.favoriteType = favoriteType;
    }


    public String getFavoriteId() {
        return this.favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public long getSaveTime() {
        return this.saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

}
