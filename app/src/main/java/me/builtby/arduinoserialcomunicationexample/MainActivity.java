package me.builtby.arduinoserialcomunicationexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private Button sendRecieveButton;
    private TextView TxText;
    private TextView RxText;

    private UsbManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        setContentView(R.layout.activity_main);

        sendRecieveButton = findViewById(R.id.send_recieve_btn);
        TxText = findViewById(R.id.tx_text);
        RxText = findViewById(R.id.rx_text);




    }

    private void TransmitText(int msg){

        SerialSendInt serialSendInt = new SerialSendInt(manager,msg);
        new Thread(serialSendInt).start();


    }

}