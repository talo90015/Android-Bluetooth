package com.example.bluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private TextView show, pair;
    Button open, close, search, get;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pair = (TextView)findViewById(R.id.pairTv);
        open = (Button)findViewById(R.id.open);
        close = (Button)findViewById(R.id.close);
        search = (Button)findViewById(R.id.search);
        get = (Button)findViewById(R.id.get);
        open.setOnClickListener(this);
        close.setOnClickListener(this);
        search.setOnClickListener(this);
        get.setOnClickListener(this);
        if (bluetoothAdapter == null){
            showToast("Bluetooth is not available");
        }else{
            showToast("Bluetooth is available");
            if (bluetoothAdapter.isEnabled()){
                showToast("藍芽未開啟");
            }else{
                showToast("藍芽已開啟");
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        switch(requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK){
                    showToast("Bluetooth is ON");
                }else{
                    showToast("Bluetooth is OFF");
                }break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.open:
                open();
                break;
            case R.id.close:
                close();
                break;
            case R.id.search:
                search();
                break;
            case R.id.get:
                get();
                break;

        }
    }

    private void open() {
        if (!bluetoothAdapter.isEnabled()){
            showToast("Turning on Bluetooth..");
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        }else{
            showToast("Bluetooth is already ON");
        }
    }
    private void close() {
        if (bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();
            showToast("urning  Bluetooth off");
        }else{
            showToast("Bluetooth is already off");
        }
    }

    private void search() {
        if (!bluetoothAdapter.isDiscovering()){
            showToast("Making Your Device Discoverable");
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(intent, REQUEST_DISCOVER_BT);
        }
    }

    private void get() {
        if (bluetoothAdapter.isEnabled()){
            pair.setText("Paired Devices");
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();

            for (BluetoothDevice device : devices){
                pair.append("\n Device : " + device.getName() + "," + device);
            }
        }else{
            showToast("Turn On bluetooth to get paired devices");
        }
    }
}