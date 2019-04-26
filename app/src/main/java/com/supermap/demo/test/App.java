package com.supermap.demo.test;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by zenghaiqiang on 2019/1/11.
 */
public class App extends TinkerApplication {

    public App() {
        super(ShareConstants.TINKER_ENABLE_ALL, AppLike.class.getCanonicalName(),
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
