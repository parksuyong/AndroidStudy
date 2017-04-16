package com.hoamict.servicetest;

/*
    1. onStart에서 서비스와 바인드를 연결
    2. 서비스 시작과 동시에 버튼 화면에 그리기(API 21 이상 버전은 권한 허용해야함)
    3. 바인드 버튼 클릭 시 서비스의 getRandomNumber()에 접근하여 count Toast
    4. cancel 버튼 클릭 시 서비스 연결 종료
    5. onStop에서 바인드 연결 해제

    테스트 (로그캣으로 서비스의 콜백메서드마다 로그 찍어놨으니 참고해서 보세요)
    1. 앱 실행 후 logcat에 서비스와 바인드 연결 되었는지 확인
    2. 바인드 버튼 클릭 시 Toast 정상적으로 출력하는 지 확인
    3. 앱 종료 후 다시 실행 시 onCreate 함수 호출하지 않는지 확인
    4. 바인드 count 데이터 유지 되어 있는지 확인
    5. 서비스와 바인드 모두 연결 해제 되었을 때 onDestroy함수 호출 하는지 확인


    자세한 설명은 이곳에 나와있습니다.(윈도우 화면에 그린 버튼이나 권한 획득부분은 안보셔도 되요)
    https://developer.android.com/guide/components/services.html?hl=ko

 */


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final int DRAWPERMISSION=0;
    MyService mService;
    boolean mBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bindButton=(Button)findViewById(R.id.bindButton);
        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {

                    int num = mService.getRandomNumber();
                    Toast.makeText(getApplicationContext(), "number: " + num, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        //화면에 그리기 권한 획득(api 21이상만 해당)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, DRAWPERMISSION);
                return;
            }
        }
        Intent intent = new Intent(this, MyService.class);
        startService(intent);//서비스 시작
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);//바인드 연결

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);//바인드 종료
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };



    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);

        switch (requestCode) {
            case DRAWPERMISSION:{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {
                        // SYSTEM_ALERT_WINDOW permission not granted...
                        finish();
                    }else{
                        Intent intent = new Intent(this, MyService.class);
                        startService(intent);
                        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                    }
                }
            }
            break;

        }
    }
}
