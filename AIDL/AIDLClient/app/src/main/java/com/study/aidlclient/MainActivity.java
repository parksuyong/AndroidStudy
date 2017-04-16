package com.study.aidlclient;

/*
  클라이언트 앱 입니다.
  1. ICalc.aidl 파일 생성 (서버의 ICalc.aidl과 패키지 경로가 같아야함)
  2. ServiceConnection 객체 생성
  3. onResme에서 서버로 바인딩(롤리팝 이후부터 타킷 패키지명 입력해야함)
  4. onPause에서 바인딩 해제

  테스트
  각 버튼 클릭하여 연산 수행 확인
 */

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.study.aidlservice.ICalc;


public class MainActivity extends AppCompatActivity {

    ICalc mCalc = null;
    TextView result_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result_tv=(TextView) findViewById(R.id.resultText);
        Button addButton=(Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCalc!=null){
                    try{
                        result_tv.setText(mCalc.Add(5, 5)+"");
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }

                }
            }
        });
        Button subButton=(Button)findViewById(R.id.subButton);
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCalc!=null){
                    try{
                        result_tv.setText(mCalc.Sub(5, 5)+"");
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }

                }
            }
        });
        Button mulButton=(Button)findViewById(R.id.mulButton);
        mulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCalc!=null){
                    try{
                        result_tv.setText(mCalc.Mul(5, 5)+"");
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }

                }
            }
        });
        Button divButton=(Button)findViewById(R.id.divButton);
        divButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCalc!=null){
                    try{
                        result_tv.setText(mCalc.Div(5, 5)+"");
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Intent it = new Intent();
        it.setAction("com.study.aidlservice.CALC");
        it.setPackage("com.study.aidlservice");
        bindService(it, mSrvConn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        unbindService(mSrvConn);
    }

    ServiceConnection mSrvConn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            mCalc = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            mCalc = ICalc.Stub.asInterface(service);
        }
    };


}
