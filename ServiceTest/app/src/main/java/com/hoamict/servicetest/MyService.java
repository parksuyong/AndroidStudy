package com.hoamict.servicetest;
/*
    WindowManager 부분은 안보셔도 됩니다.
    displayButton() 함수가 버튼 그리는 부분인데 취소 버튼 리스너 부분에서 서비스 종료 부분만 보시면 되요
    cancel_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf(); // 서비스 종료
                if (view != null) {
                    winMgr.removeView(view);
                    view=null;

                }
            }
        });

 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MyService extends Service {
    private WindowManager winMgr;
    private View view=null;



    private final IBinder mBinder = new LocalBinder();
    private int count=0;

    public class LocalBinder extends Binder {
        MyService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i("Service","onBind()");
        return mBinder;
    }
    @Override
    public void onCreate() {
        Log.i("Service","onCreate()");
        displayButton();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service","onStartCommand()");
        // If we get killed, after returning from here, restart
        return super.onStartCommand(intent,flags,startId);

        /*
            START_NOT_STICKY,
            START_STICKY,
            START_REDELIVER_INTENT
            https://developer.android.com/reference/android/app/Service.html?hl=ko#START_NOT_STICKY
         */
    }
    public int getRandomNumber() {
        return count++;
    }
    @Override
    public void onDestroy() {
        Log.i("Service","onDestroy()");
        stopSelf();
        if (view != null) {
            winMgr.removeView(view);
            view=null;
        }
    }



    private void displayButton(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = (View) inflater.inflate(R.layout.service, null);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,       //터치 인식
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        ImageView cancel_b=(ImageView)view.findViewById(R.id.cancelButton);
        cancel_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
                if (view != null) {
                    winMgr.removeView(view);
                    view=null;

                }
            }
        });

        try {
            winMgr = (WindowManager) getSystemService(WINDOW_SERVICE);
            winMgr.addView(view, params);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
