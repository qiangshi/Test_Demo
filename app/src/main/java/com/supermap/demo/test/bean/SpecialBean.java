package com.supermap.demo.test.bean;

/**
 * @ClassName: SpecialBean
 * @Description: java类作用描述
 * @Author: 曾海强
 * @CreateDate: 2019/4/10 23:43
 */
public class SpecialBean {
    private String specialName;

    private boolean isSelect;

    public String getSpecialName() {
        return specialName;
    }

    public SpecialBean(String specialName, boolean isSelect) {
        this.specialName = specialName;
        this.isSelect = isSelect;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
