package com.saint.struct;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.saint.struct.bean.WanAndroidBean;
import com.saint.struct.network.service.ConnectService;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        Rect rect = new Rect(0, 0, 1, 1);
        Rect rect2 = rect;
        rect2.setEmpty();
        Log.e("aaa", "cc=============" + rect.bottom);
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.saint.struct", appContext.getPackageName());

    }

    @Test
    public void testRetrofit() throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("<https://api.uomg.com/>")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectService iCity = retrofit.create(ConnectService.class);
        Call<WanAndroidBean> call = iCity.getArticleList(1, 1);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {

            }
        });

        call.execute();
    }

    @Test
    public void testOkHttp() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();
        okhttp3.Call call = client.newCall(request);
//        okhttp3.Call call1 = new RealCall(client,request,false);
        okhttp3.Response response = call.execute();
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {

            }
        });
    }


    @Test
    public void testRxJava() {
        String[] strings = {"a", "b", "c"};
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e("aaa", "cc=============" + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Observable<String> observable = Observable.fromArray(strings);
        observable.subscribe(observer);
    }

    @Test
    public void isCorrect() {
        String a = "-------a";
        System.out.println(System.identityHashCode(a));
        a = "-----------b";
        System.out.println(System.identityHashCode(a));

        assert ((2 + 2) == 4);
    }
}