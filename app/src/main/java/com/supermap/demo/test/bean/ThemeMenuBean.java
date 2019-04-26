package com.supermap.demo.test.bean;

/**
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/11 13:47
 */
public class ThemeMenuBean {
    private int imgId;
    private String themeName;
    private boolean isSelect;

    public ThemeMenuBean(int imgId, String themeName, boolean isSelect) {
        this.imgId = imgId;
        this.themeName = themeName;
        this.isSelect = isSelect;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
