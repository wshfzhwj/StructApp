package com.saint.struct;

import android.content.Context;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.*;

import com.saint.struct.network.service.ConnectService;

import java.io.IOException;

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
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.sf.back", appContext.getPackageName());
    }

    @Test
    public void testRetrofit() throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("<https://api.uomg.com/>")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectService iCity = retrofit.create(ConnectService.class);
        Call call = iCity.getArticleList(1,1);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        call.execute();
    }
}