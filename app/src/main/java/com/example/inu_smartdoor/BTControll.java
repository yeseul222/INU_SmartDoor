package com.example.inu_smartdoor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.os.CountDownTimer;

public class BTControll extends com.example.inu_smartdoor.MainActivity {

	private String connectedDeviceName;
	private BluetoothAdapter btAdapter = null;
	private BluetoothDevice btDevice = null;
	private BluetoothSocket btSocket = null;
	private OutputStream btos;

	String pass;
	String ans = "";
	Button btn1;
	Button btn2;
	Button btn3;
	Button btn4;
	Button btn5;
	Button btn6;
	Button btn7;
	Button btn8;
	Button btn9;
	Button btn0;
	Button reset;
	Button enter;
	Button cam;
	int re = 0;	//비밀번호 변경 가능 여부 상태값(1이면 변경 가능)
	int openAndclose = 0;	//닫힌 상태에서 시작
	int wrongNum = 0;	//잘못된 비밀번호 입력한 횟수
	CountDownTimer mCountDown = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn1 = findViewById(R.id.button1);
		btn2 = findViewById(R.id.button2);
		btn3 = findViewById(R.id.button3);
		btn4 = findViewById(R.id.button4);
		btn5 = findViewById(R.id.button5);
		btn6 = findViewById(R.id.button6);
		btn7 = findViewById(R.id.button7);
		btn8 = findViewById(R.id.button8);
		btn9 = findViewById(R.id.button9);
		btn0 = findViewById(R.id.button0);
		cam = findViewById(R.id.buttoncam);
		reset = findViewById(R.id.buttonr);
		enter = findViewById(R.id.buttonE);

        getPreferences();
		setListener();
		
	}

	//SharedPrefrences 객체를 받아옴
	//DB를 쓰기에는 너무 간단한 Data를 저장할 경우
	private void getPreferences() {
		SharedPreferences mPref = getSharedPreferences("pref", MODE_PRIVATE);
		pass = mPref.getString("pass", "0000");	//Default 값은 "0000"
	}

	public void setListener() {
		try {
			openBT();	//Bluetooth 연결 여부 try
			Toast.makeText(getApplicationContext(), "open Success",
					Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "open Failed",
					Toast.LENGTH_LONG).show();
			finish();
		}

		btn1.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "1";

				}
				return false;
			}
		});
		btn2.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "2";

				}
				return false;
			}
		});
		btn3.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "3";

				}
				return false;
			}
		});
		btn4.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "4";

				}
				return false;
			}
		});
		btn5.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "5";

				}
				return false;
			}
		});
		btn6.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "6";

				}
				return false;
			}
		});
		btn7.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "7";

				}
				return false;
			}
		});
		btn8.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "8";

				}
				return false;
			}
		});
		btn9.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "9";

				}
				return false;
			}
		});
		btn0.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					ans = ans + "0";

				}
				return false;
			}
		});
		enter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (re == 1) {
					Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다.",
							Toast.LENGTH_SHORT).show();
					SharedPreferences mPref = getSharedPreferences("pref",
							MODE_PRIVATE);
					SharedPreferences.Editor editor = mPref.edit();
					editor.remove("pass");	//기존의 pass값 지우고
					editor.putString("pass", ans);	//변경된 값 저장
					editor.commit();	//저장(put), 삭제(remove, clear) 할 경우 꼭 호출
					pass = ans;
					re = 2;

				} else {
					if (pass.equals(ans)) {	//pass와 ans가 일치하면
						if(openAndclose == 0){	//닫힌 상태일 때
                           	write("a");	//Bluetooth로 "a"라는 문자 전송
							reset.setEnabled(true);	//비밀번호 변경 버튼 활성화
							Toast.makeText(getApplicationContext(), "열렸습니다.",
									Toast.LENGTH_SHORT).show();
							openAndclose = 1;	//열림으로 상태 변화
							wrongNum = 0;	//비밀번호 틀린 횟수 초기화
						}
						else{	//열린 상태일 때
							write("a");	//Bluetooth로 "a"라는 문자 전송
							reset.setEnabled(true);	//비밀번호 변경 버튼 활성화
							Toast.makeText(getApplicationContext(), "닫혔습니다.",
									Toast.LENGTH_SHORT).show();
							openAndclose = 0;	//닫힘으로 상태 변화
							wrongNum = 0;	//비밀번호 틀린 횟수 초기화
						}
					} else {	//비밀번호가 틀렸을 때
						Toast.makeText(getApplicationContext(), "틀렸습니다.",
								Toast.LENGTH_SHORT).show();
						reset.setEnabled(false);	//비밀번호 변경버튼 비활성화
						wrongNum++;	//비밀번호 틀린 횟수++
						if(wrongNum == 5){	//5번 틀리면 숫자버튼 비활성화(shutdown)
							shutdown();
						}
					}
				}
				ans = "";
			}
		});

		reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				re = 1;
				reset.setEnabled(false);
			}
		});
		
		cam.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	//webcam.class로 이동
				Intent intent = new Intent(getApplicationContext(), webcam.class);
				startActivity(intent);
			}
		});
		
	}
	
	private void shutdown() {	//숫자 버튼 비활성화 후 n초 카운트 -> 숫자 버튼 활성화
		btn1.setEnabled(false);
		btn2.setEnabled(false);
		btn3.setEnabled(false);
		btn4.setEnabled(false);
		btn5.setEnabled(false);
		btn6.setEnabled(false);
		btn7.setEnabled(false);
		btn8.setEnabled(false);
		btn9.setEnabled(false);
		btn0.setEnabled(false);
		enter.setEnabled(false);
		final Toast toast1 = Toast.makeText(getApplicationContext(), "잠시 후 다시 시도해주세요.",
				Toast.LENGTH_SHORT);
		mCountDown = new CountDownTimer(10000, 1000) {
			public void onTick(long millisUntilFinished) {
				toast1.show();
			}

			public void onFinish() {	//카운트 끝나고 나면 숫자버튼 활성화
				toast1.cancel();
				btn1.setEnabled(true);
				btn2.setEnabled(true);
				btn3.setEnabled(true);
				btn4.setEnabled(true);
				btn5.setEnabled(true);
				btn6.setEnabled(true);
				btn7.setEnabled(true);
				btn8.setEnabled(true);
				btn9.setEnabled(true);
				btn0.setEnabled(true);
				enter.setEnabled(true);
				wrongNum = 0;
			}
		}.start();
	}
	public void openBT() throws IOException {
		//MainActivity에서 보낸 Intent 받음
		Intent intent = getIntent();

		//mac(key)으로 받은 Extras 저장
		connectedDeviceName = intent.getExtras().getString("mac");

		//기기 자체의 BluetoothAdapter 반환
		btAdapter = BluetoothAdapter.getDefaultAdapter();

		//페어링된 기기를 나타내는 BluetoothDevice 집합이 반환
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				//집합값 중에 Intent로 전달 받은 값이 있으면
				if (device.getAddress().equals(connectedDeviceName)) {
					btDevice = device;	//그 기기를 저장
					break;
				}
			}
		}

		//IP, MAC,USIM은 디바이스를 구별할 수 있는 고유한 아이디를 할당하기 적합하지 않기 때문에
		//UUID(Universally Unique Identifier : 범용 고유 식별자)를 사용 하는데
		//여기서는 블루투스 프로토콜 BASE UUID 사용
		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

		//createRfcommSocketToServiceRecord를 호출해서 BluetoothSocket을 얻음
		btSocket = btDevice.createRfcommSocketToServiceRecord(uuid);
		//연결 시작 -> 실패하면 Exception 발생 -> Catch문 실행
		btSocket.connect();

		Toast.makeText(getApplicationContext(),
				String.valueOf(btSocket.isConnected()), Toast.LENGTH_LONG)
				.show();

		//소켓 전송을 처리할 OutputStream 얻음
		btos = btSocket.getOutputStream();
	}

	public void closeBt() throws IOException {
		btos.close();
		btSocket.close();
	}

	//데이터를 블루투스로 전송
	public void write(String str) {
		try {
			btos.write(str.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
