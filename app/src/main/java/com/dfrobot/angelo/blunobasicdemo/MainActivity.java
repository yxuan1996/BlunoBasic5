package com.dfrobot.angelo.blunobasicdemo;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import android.media.MediaPlayer;

public class MainActivity  extends BlunoLibrary {
	private Button buttonScan;
	private TextView serialReceivedText;
	private TextView serialValue1;
	private TextView serialValue2;
	private TextView serialValue3;
	private TextView serialValue4;
	private TextView serialValue5;
	private TextView serialVoltage1;
	private TextView serialVoltage2;
	private TextView serialVoltage3;
	private TextView serialVoltage4;
	private TextView serialVoltage5;

	private MediaPlayer onel;
	private MediaPlayer twol;
	private MediaPlayer threel;
	private MediaPlayer fourl;
	private MediaPlayer fivel;

	public String myString;
	public Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        onCreateProcess();														//onCreate Process by BlunoLibrary


        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

		serialReceivedText=(TextView) findViewById(R.id.editText2);
        serialValue1=(TextView) findViewById(R.id.value1);
        serialValue2=(TextView) findViewById(R.id.value2);
		serialValue3=(TextView) findViewById(R.id.value3);
		serialValue4=(TextView) findViewById(R.id.value4);
		serialValue5=(TextView) findViewById(R.id.value5);
		serialVoltage1=(TextView) findViewById(R.id.voltage1);
		serialVoltage2=(TextView) findViewById(R.id.voltage2);
		serialVoltage3=(TextView) findViewById(R.id.voltage3);
		serialVoltage4=(TextView) findViewById(R.id.voltage4);
		serialVoltage5=(TextView) findViewById(R.id.voltage5);

		onel = MediaPlayer.create(this, R.raw.onel);
		twol = MediaPlayer.create(this, R.raw.twol);
		threel = MediaPlayer.create(this, R.raw.threel);
		fourl = MediaPlayer.create(this, R.raw.fourl);
		fivel = MediaPlayer.create(this, R.raw.fivel);


		handler.postDelayed(runnable, 1000);

		buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
			}
		});
	}

	protected void onResume(){
		super.onResume();
		System.out.println("BlUNOActivity onResume");
		onResumeProcess();														//onResume Process by BlunoLibrary
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
		super.onActivityResult(requestCode, resultCode, data);
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }
	
	protected void onStop() {
		super.onStop();
		onStopProcess();														//onStop Process by BlunoLibrary
	}
    
	@Override
    protected void onDestroy() {
        super.onDestroy();	
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

	@Override
	public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
		switch (theConnectionState) {											//Four connection state
		case isConnected:
			buttonScan.setText("Connected");
			break;
		case isConnecting:
			buttonScan.setText("Connecting");
			break;
		case isToScan:
			buttonScan.setText("Scan");
			break;
		case isScanning:
			buttonScan.setText("Scanning");
			break;
		case isDisconnecting:
			buttonScan.setText("isDisconnecting");
			break;
		default:
			break;
		}
	}

	@Override
	public void onSerialReceived(String theString) {							//Once connection data received, this function will be called

		myString = theString;


		//The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
	}

	public Runnable runnable = new Runnable(){
		@Override
		public void run(){
			if(myString != null){

				char first = myString.charAt(0);
				if (first == '&'){
					String [] separated = myString.split("\\:");
					if (separated.length >= 5) {
						String token1 = separated[0].substring(1);
						String token2 = separated[1];
						String token3 = separated[2];
						String token4 = separated[3];
						String token5 = separated[4];
						serialValue1.setText(token1);
						serialValue2.setText(token2);
						serialValue3.setText(token3);
						serialValue4.setText(token4);
						serialValue5.setText(token5);

						//Conversion to voltage value
						if (token1 != null && token1.length() > 0 && token1 != " "){
							double d1 = Double.valueOf(token1);

							double v1 = d1 * 5 / 1023;

							if (v1 > 800){
								onel.start();
							}

							serialVoltage1.setText(String.format("%.1f",v1));
						}

						if (token2 != null && token2.length() > 0 && token2 != " "){
							double d2 = Double.valueOf(token2);

							double v2 = d2 * 5 / 1023;

							if (v2 > 800){
								twol.start();
							}

							serialVoltage2.setText(String.format("%.1f",v2));
						}

						if (token3 != null && token3.length() > 0 && token3 != " "){
							double d3 = Double.valueOf(token3);

							double v3 = d3 * 5 / 1023;

							if (v3 > 800){
								threel.start();
							}

							serialVoltage3.setText(String.format("%.1f",v3));
						}

						if (token4 != null && token4.length() > 0 && token4 != " "){
							double d4 = Double.valueOf(token4);

							double v4 = d4 * 5 / 1023;

							if (v4 > 800){
								fourl.start();
							}

							serialVoltage4.setText(String.format("%.1f",v4));
						}

						if (token5 != null && token5.length() > 0 && token5 != " "){
							double d5 = Double.valueOf(token5);

							double v5 = d5 * 5 / 1023;

							if (v5 > 800){
								fivel.start();
							}

							serialVoltage5.setText(String.format("%.1f",v5));
						}


					}
				}

			}
			handler.postDelayed(this,100);

		}
	};

}