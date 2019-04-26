package com.supermap.demo.test.bean;

/**
 * @ClassName: MapMenuBean
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/11
 */
public class MapMenuBean {

    private int imgUrl;
    private String mapName;
    private boolean isSelect;

    public MapMenuBean(int imgUrl, String mapName, boolean isSelect) {
        this.imgUrl = imgUrl;
        this.mapName = mapName;
        this.isSelect = isSelect;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
