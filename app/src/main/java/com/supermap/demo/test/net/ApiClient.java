package com.supermap.demo.test.net;

import com.supermap.demo.test.constants.Constant;
import com.supermap.demo.test.manager.DataCacheManager;
import com.supermap.demo.test.utils.SharePreferenceUtil;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ApiClient {
//    private static final String BASE_URL;
//    private static final String URL;

    private final static String REQUEST_TOKEN = "token";
    private final static String REQUEST_USER_ID = "user_id";
    private final static String REQUEST_TIMESTAMP = "timeStamp";
    private final static String REQUEST_DEVICE_ID = "deviceId";
    private final static String OS_TYPE = "osType";
    private final static String APPID = "appid";
    private final static String VERSION = "version";
    private final static String LANGUAGE = "language";

    static {
//        BASE_URL = TextUtils.isEmpty(BuildConfig.API_HOST) ? "http://api.anyinfo.com" : BuildConfig.API_HOST;
//        URL = BASE_URL + "/app/";
    }

    public static ApiClient getInstance() {
        return Holder.Instance;
    }

    private static class Holder {

        private static final ApiClient Instance = new ApiClient();
    }

    public static Retrofit retrofit() {
        return new Retrofit.Builder()
//                .baseUrl("http://ww1.bizbook.cn/attendance/")
            .baseUrl("http://47.93.7.27/attendance/") //websocket 测试链接
            .addConverterFactory(FastJsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getClient())
            .build();
    }

    public static OkHttpClient getClient() {
        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        try {
            return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(
                    chain -> {
                        Request srcRequest = chain.request();
                        if (srcRequest.method().equals("POST")) {//post请求
                            if (srcRequest.body() instanceof FormBody) {
                                FormBody.Builder newBodyBuilder = new FormBody.Builder();
                                FormBody formBody = (FormBody) srcRequest.body();
                                for (int i = 0; i < formBody.size(); i++) {
                                    newBodyBuilder.addEncoded(formBody.encodedName(i),
                                        formBody.encodedValue(i));
                                }
                                if (SharePreferenceUtil.getBoolean(Constant.IS_LOGIN, false)) {
                                    formBody = newBodyBuilder
                                        .addEncoded(REQUEST_TOKEN, DataCacheManager.getToken())
                                        .addEncoded(REQUEST_USER_ID,
                                            DataCacheManager.getUserInfo().getId() + "")
                                        .build();
                                } else {
                                    formBody = newBodyBuilder.build();
                                }
                                srcRequest = srcRequest.newBuilder().post(formBody).build();
                            }
                        }
                        return chain.proceed(srcRequest);
                    }
                )
                .addInterceptor(httpLoggingInterceptor)
                .build();
        } catch (Exception e) {
            return new OkHttpClient.Builder().build();
        }
    }
}
