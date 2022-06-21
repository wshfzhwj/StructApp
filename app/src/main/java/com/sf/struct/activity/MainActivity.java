package com.sf.struct.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sf.struct.R;
import com.sf.struct.bean.User;
import com.sf.struct.network.HttpManager;
import com.sf.struct.practice.InterviewFun;
import com.sf.struct.service.MessengerService;
import com.sf.struct.viewmodel.MainActivityViewModel;
import com.sf.struct.widget.TouchButton;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();
    Button helloBtn;
    TouchButton helloBtn2;
    ImageView roundImage;
    private Messenger mService;
    private Retrofit mRetrofit;
    private Context context = MainActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_back);
        helloBtn = findViewById(R.id.helloBtn);
        helloBtn2 = findViewById(R.id.helloBtn2);
        roundImage = findViewById(R.id.roundImage);
        helloBtn.setOnClickListener(view -> {
//            testGlide();
//                requestPermissionrequestPermission();
//                handleVue();
//                testEquals();
//                startAidl();
//                executeReq();
//                testLooper();
//                testViewModel();
        });


        helloBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(MainActivity.this, WebActivity.class));
            }
        });
    }

    private void testViewModel() {
        MainActivityViewModel model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
    }

    private void testLooper() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) helloBtn2.getLayoutParams();
                helloBtn2.setText("哈哈哈");
                params.weight = 1;
                helloBtn2.setLayoutParams(params);
                helloBtn2.requestLayout();
                Toast.makeText(MainActivity.this, "ceshi", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
    }

    public void testEquals() {
        User user = new User("a", "a");
        User user2 = new User("a", "a");
        Log.e(TAG, "value 1= " + (user == user2));
        Log.e(TAG, "value 2= " + (user.equals(user2)));
    }

    private void testGlide() {
        String url = "https://alifei04.cfp.cn/creative/vcg/800/version23/VCG41175510742.jpg";
        Glide.with(this).load(url).into(roundImage);
    }

    private void testFunc() {
        new InterviewFun().lightFun();
    }

    private void startAidl() {
        startActivity(new Intent().setClass(this, AidlActivity.class));
    }

    private void executeReq() {
        Call<String> call = HttpManager.getInstance().getmService().getTxt();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure");
            }
        });
    }

    private void executeReq2() {
        Call<String> call = HttpManager.getInstance().getmService().getName();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure");
            }
        });
    }

    public void getTypeClass() {
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        type.toString();
    }

    private void init() {
        Intent intent = new Intent(MainActivity.this, MessengerService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public void handleVue() {
//        String url = "http://webapp.1/a/b/c";
        String url = "qax://webapp.1/a/b/c";
//        String url = "file:///android_asset/dist/index.html";
        //  String url = "file:///android_asset/test.html";
        WebActivity.startActivity(MainActivity.this, "title", url);
    }


    public void requestPermission() {
        //使用兼容库就无需判断系统版本
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission
                .WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            Toast.makeText(MainActivity.this, "get it", Toast.LENGTH_LONG).show();
        } else {
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意，执行操作
                Toast.makeText(MainActivity.this, "get it", Toast.LENGTH_LONG).show();
            } else {
                //用户不同意，向用户展示该权限作用
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("申请权限")
                            .setPositiveButton("OK", (dialog1, which) ->
                                    ActivityCompat.requestPermissions(this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            1))
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                }
            }
        }
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    };

    public void transBitmap() {
        Bundle bundle = new Bundle();
        bundle.putBinder("", null);
    }


    private Handler messengerHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private Messenger mGetMessager = new Messenger(messengerHandler);

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
