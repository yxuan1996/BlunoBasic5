package com.dfrobot.angelo.blunobasicdemo;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
	private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0.##");
	
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
		// TODO Auto-generated method stub

		String [] separated = {"100","100","100","100","100"};
		try {
			separated = theString.split("\\:");
		} catch (Exception e){
			e.printStackTrace();
		}



//		List<String> tokens = new ArrayList<>();
//		StringTokenizer tokenizer = new StringTokenizer(theString, ":");
//		while (tokenizer.hasMoreElements()) {
//			tokens.add(tokenizer.nextToken());
//		}

		String token1 = separated[0];
		String token2 = separated[1];
		String token3 = separated[2];
		String token4 = separated[3];
		String token5 = separated[4];


//		String token1 = tokens.get(0);
//		String token2 = tokens.get(1);
//		String token3 = tokens.get(2);
//		String token4 = tokens.get(3);
//		String token5 = tokens.get(4);

		serialValue1.setText(token1);
		serialValue2.setText(token2);
		serialValue3.setText(token3);
		serialValue4.setText(token4);
		serialValue5.setText(token5);

		//Conversion to voltage value
		double d1 = Double.parseDouble(token1);
		double d2 = Double.parseDouble(token2);
		double d3 = Double.parseDouble(token3);
		double d4 = Double.parseDouble(token4);
		double d5 = Double.parseDouble(token5);

		double v1 = d1 * 5 / 1023;
		double v2 = d2 * 5 / 1023;
		double v3 = d3 * 5 / 1023;
		double v4 = d4 * 5 / 1023;
		double v5 = d5 * 5 / 1023;


		serialVoltage1.setText(REAL_FORMATTER.format(v1));
		serialVoltage2.setText(REAL_FORMATTER.format(v2));
		serialVoltage3.setText(REAL_FORMATTER.format(v3));
		serialVoltage4.setText(REAL_FORMATTER.format(v4));
		serialVoltage5.setText(REAL_FORMATTER.format(v5));

		//The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
	}

}