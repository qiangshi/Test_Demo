package com.supermap.demo.test.ui.fragment.tool;

import java.lang.reflect.Field;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * @ClassName: BaseToolFragment
 * @Description: 覆盖在MainActivity的基类
 * @Author: 曾海强
 * @CreateDate: 2019/4/13 10:58
 */
public abstract class BaseToolFragment extends DialogFragment  {



    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
