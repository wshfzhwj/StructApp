package com.saint.struct.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.saint.struct.service.FirstWorkManager;
import com.saint.struct.service.JobTestService;
import com.sf.biometiriclib.BiometricPromptManager;
import com.saint.struct.R;
import com.saint.struct.bean.User;
import com.saint.struct.network.HttpManager;
import com.saint.struct.practice.InterviewFun;
import com.saint.struct.service.MessengerService;
import com.saint.struct.viewmodel.MainActivityViewModel;
import com.saint.struct.widget.TouchButton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.JobIntentService;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {
    public static final String EXTRA_KEY_SERVICE = "extra_key_service";
    private static final String TAG = MainActivity.class.getName();
    Button helloBtn;
    Button helloBtn2;
    ImageView roundImage;
    TextView mDescTv;
    private Messenger mService;
    private Retrofit mRetrofit;
    private Context context = MainActivity.this;
    private Intent intent;
    private BiometricPromptManager mManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        intent = new Intent(MainActivity.this, MessengerService.class);
        intent.putExtra(EXTRA_KEY_SERVICE, "service");
        helloBtn = findViewById(R.id.helloBtn);
        helloBtn2 = findViewById(R.id.helloBtn2);
        roundImage = findViewById(R.id.roundImage);
        mDescTv = findViewById(R.id.tv_desc);
        helloBtn.setOnClickListener(view -> {
//                testGlide();
//                requestPermissionrequestPermission();
//                handleVue();
//                testEquals();
                startAidl();
//                executeReq();
//                testLooper();
//                testViewModel();
//                testEquals();
//            testConflict();
//            testBitmapMemory();
//            testFinger();
//            testService();
        });


        helloBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                stopService(intent);
//                unbindService(connection);
                startActivity(new Intent().setClass(MainActivity.this, WebActivity.class));
            }
        });
    }

    private void testService() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent);
//        }else{
//            startService(intent);
//        }
        JobTestService.enqueueWork(this, intent);
//        startService(intent);
//                bindService(new Intent(MainActivity.this, MessengerService.class), connection, BIND_AUTO_CREATE);
    }

    private void testFinger() {
        mManager = BiometricPromptManager.from(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SDK version is "+ Build.VERSION.SDK_INT);
        stringBuilder.append("\n");
        stringBuilder.append("isHardwareDetected : "+mManager.isHardwareDetected());
        stringBuilder.append("\n");
        stringBuilder.append("hasEnrolledFingerprints : "+mManager.hasEnrolledFingerprints());
        stringBuilder.append("\n");
        stringBuilder.append("isKeyguardSecure : "+mManager.isKeyguardSecure());
        stringBuilder.append("\n");
        mDescTv.setText(stringBuilder.toString());

        if (mManager.isBiometricPromptEnable()) {
            mManager.authenticate(new BiometricPromptManager.OnBiometricIdentifyCallback() {
                @Override
                public void onUsePassword() {
                    Toast.makeText(MainActivity.this, "onUsePassword", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSucceeded() {

                    Toast.makeText(MainActivity.this, "onSucceeded", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed() {

                    Toast.makeText(MainActivity.this, "onFailed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(int code, String reason) {

                    Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {

                    Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void testBitmapMemory() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        Log.d(TAG,"memory getAllocationByteCount = " + bitmap.getAllocationByteCount());
        Log.d(TAG,"memory getByteCount = " + bitmap.getByteCount());
    }

    private void testConflict() {
        Log.i(TAG, "This method is for testing conflict from Q");
//        Log.d(TAG,"This method is for testing git conflict");
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    private void testViewModel() {
        MainActivityViewModel model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
    }

    private void testLooper() {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                Looper.prepare();
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) helloBtn2.getLayoutParams();
//                helloBtn2.setText("哈哈哈");
//                params.weight = 1;
//                helloBtn2.setLayoutParams(params);
//                helloBtn2.requestLayout();
//                Toast.makeText(MainActivity.this, "ceshi", Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            }
//        }.start();
        new ThreadHandler().start();
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


    class ThreadHandler extends Thread{
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            Handler handler = new Handler();
            Log.e(TAG, "handler id = "+ handler.getLooper().getThread().getId());
            Log.e(TAG, "handler id = "+ getId());
            Looper.loop();
        }
    }
}
