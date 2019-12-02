package com.example.inu_smartdoor;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends Activity {

	private static final int REQUEST_ENABLE_BT = 3;
	private ArrayAdapter<String> mArrayAdapter;
	private ListView mConversationView;
	private static final String TAG = "MainActivity";
	Button cam;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Firebase 연결을 위한 Token값 얻는 함수 -> Python 코드 실행시 필요
	    FirebaseInstanceId.getInstance().getInstanceId()
				.addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
					@Override
					public void onComplete(@NonNull Task<InstanceIdResult> task) {
						if (!task.isSuccessful()) {
							Log.w(TAG, "getInstanceId failed", task.getException());
							return;
						}
						// Token ID 저장
						String token = task.getResult().getToken();

						// Log 와 Toast로 출력
						String msg = getString(R.string.msg_token_fmt, token);
						Log.d(TAG, msg);
						//Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
					}
				});

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//Bluetooth 연결 전 웹캠 확인하기 위한 버튼
		cam = findViewById(R.id.btcam);

		//장치의 Bluetooth Adapter를 얻기 위한 함수
		//지원하지 않으면 null을 반환
		BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter();

		//ListView 객체를 연결하기 위한 ArrayAdapter객체
		mArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);

		if (!mBTAdapter.isEnabled()) {	//Bluetooth가 활성 상태가 아니면
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE); //Intent를 발생시켜 다이얼로그 띄움
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}

		//Pairing 된 기기 목록을 가져온다
		Set<BluetoothDevice> pairedDevices = mBTAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {	//Pairing 된 기기 목록만큼
				mArrayAdapter
						.add(device.getName() + "\n" + device.getAddress()); //mArrayAdapter에 기기이름과 MAC주소를 저장
			}
		}

		mConversationView = findViewById(R.id.in);

		//ListView 객체에 mArrayAdapter 객체를 연결
		mConversationView.setAdapter(mArrayAdapter);

		//ListView Item 클릭 시 ListView의 Position(Data)을 mac(Key)에 담아 가지고
		// BTControll.class로 이동
		mConversationView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				Intent btIntent = new Intent(MainActivity.this,
						BTControll.class);
				btIntent.putExtra("mac", arg0.getAdapter().getItem(arg2)
						.toString().split("\n")[1]);
				startActivity(btIntent);
			}
		});

		//cam 버튼 클릭 시 Password.class로 이동
		cam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Password.class);
				startActivity(intent);
			}
		});
	}

	//현재 사용중인 menu 에 우리가 작성한 xml 파일을 등록하기
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

}