

package tv.danmaku.ijk.media.example.fragments;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class Util {

    private static final String TAG = Util.class.getSimpleName();

    interface Callback {
        void onResult(Object obj);
    }

    public static final void getVideoList(final Context ctx, final Callback cbk) {
//        final Request request = new Request.Builder().url(
//                "http://service.inke.tv/api/live/simpleall").build();
        final Request request = new Request.Builder().url(
                "http://video.m.fangyantianxia.cn:8000/video/bulk/?page=0&page_size=20").build();
        final Call call = new OkHttpClient().newCall(request);

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e);

                new Handler(ctx.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        cbk.onResult(null);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v(TAG, "onResponse: " + response + " " + response.body().toString());

                final Object obj = new DataParser().parseData(response.body().string());
                new Handler(ctx.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        cbk.onResult(obj);
                    }
                });
            }
        });
    }

    static final class DataParser {

        public Object parseData(String s) {
            Log.v(TAG, "parse data: " + s);

            if (s == null || s.length() == 0) {
                return new ArrayList<>(0);
            }

            try {
                JSONObject obj = new JSONObject(s);
                if (!obj.optBoolean("ok", false)) {
                    return new ArrayList<>(0);
                }

                final JSONArray arr = obj.optJSONArray("data");
                if (arr == null) {
                    return new ArrayList<>(0);
                }

                final int size = arr.length();
                final ArrayList result = new ArrayList(size);
                String url;
                for (int i = 0; i < size; i++) {
                    obj = arr.optJSONObject(i);
                    if (obj == null) {
                        continue;
                    }

                    url = obj.optString("vurl");
                    if (url != null) {
                        result.add(url);
                    }
                }

                return result;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList<>(0);
        }
    }


    public static final void getInkeVideoList(final Context ctx, final Callback cbk) {
        final Request request = new Request.Builder().url(
                "http://service.inke.tv/api/live/simpleall?lc=3000000000000502&cv=IK2.1.10_Android&cc=TG36001&ua=samsungSM-N9008S&uid=341235&sid=Fkni0PABb4VuMR6lQi08STai0Mi3&devi=352625065807685&imsi=460003442228892&imei=352625065807685&icc=89860063191507194205&conn=WIFI&vv=1.0.2-201511261613.android&aid=6d721f8dc4e6a753&osversion=android_21").build();

        final Call call = new OkHttpClient().newCall(request);

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e);

                new Handler(ctx.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        cbk.onResult(null);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v(TAG, "onResponse: " + response + " " + response.body().toString());

                final Object obj = new DataParserInke().parseData(response.body().string());
                new Handler(ctx.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        cbk.onResult(obj);
                    }
                });
            }
        });
    }
    static final class DataParserInke {

        public Object parseData(String s) {
            Log.v(TAG, "parse data: " + s);

            if (s == null || s.length() == 0) {
                return new ArrayList<>(0);
            }

            try {
                JSONObject obj = new JSONObject(s);

                final JSONArray arr = obj.optJSONArray("lives");
                if (arr == null) {
                    Log.e(TAG, "gdebug not find lives......" );
                    return new ArrayList<>(0);
                }

                final int size = arr.length();
                final ArrayList result = new ArrayList(size);
                String url;
                for (int i = 0; i < size/20; i++) {
                    obj = arr.optJSONObject(i);
                    if (obj == null) {
                        continue;
                    }

                    url = obj.optString("stream_addr");
                    if (url != null) {
                        result.add(url);
                    }
                }

                return result;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList<>(0);
        }
    }
}
