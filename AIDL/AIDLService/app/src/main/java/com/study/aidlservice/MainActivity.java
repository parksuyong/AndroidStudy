package com.study.aidlservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
  서버 앱 입니다.
  1. ICalc.aidl 파일 생성
  2. 서비스 구현
  3. 매니페스트에 인텐트 필터 사용하여 명시적 서비스 설정
  <intent-filter>
      <action android:name="com.study.aidlservice.CALC"></action>
  </intent-filter>
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

}
