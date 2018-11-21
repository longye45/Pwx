package org.dhy.Util;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName: HttpUtil
 * @Author: dhy
 * @Date: 2018/11/21 3:49 PM
 **/
public class HttpUtil {
    public static final void get() throws Exception {
        String url = "https://wkbjbos.bdimg.com/v1/docconvert3257/wk/559107b472e82681d33a1e3c02058828/0.json?token=4f56ccc0d6ec1244cffc9e90b6e27b31d561c28c9220284010b387389c6943e0&expire=2018-11-21T08:59:16Z";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url).header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
                .build();
        final Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        byte[] bytes = response.body().bytes();
        System.out.println(new String(bytes, "GBK"));
    }

    public static void main(String[] args) throws Exception {
        get();
    }
}
