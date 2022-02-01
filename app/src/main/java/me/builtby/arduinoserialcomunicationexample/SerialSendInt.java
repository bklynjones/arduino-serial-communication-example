package me.builtby.arduinoserialcomunicationexample;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class SerialSendInt implements Runnable{
    private static final String TAG = "SERIAL";
    private  final UsbSerialDriver mDriver;
    private final UsbManager mManager;
    private final byte[] mByte;


    public SerialSendInt(UsbManager manager, Integer count){

        ProbeTable cdcTable = new ProbeTable();
        cdcTable.addProduct(0x2341, 0x8037, CdcAcmSerialDriver.class);
        UsbSerialProber prober = new UsbSerialProber(cdcTable);
        List<UsbSerialDriver> drivers = prober.findAllDrivers(manager);


        // Open a connection to the first available driver.
        this.mDriver = drivers.get(0);
        this.mManager = manager;
        this.mByte = ByteBuffer.allocate(1).putInt(count).array();
    }

    @Override
    public void run() {
        UsbDeviceConnection connection = mManager.openDevice(mDriver.getDevice());
        UsbSerialPort port = mDriver.getPorts().get(0);
        try{
            port.open(connection);
            port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            //port.write(mByte,1000);
            port.write(mByte, 1000);
            Log.d(TAG, "mByte: " + ByteBuffer.wrap(mByte).getInt());
//            port.read(mByte,1000);
//            int responseVal  = ByteBuffer.wrap(mByte).getInt();
//            Log.d(TAG, "BYTE READ: " + responseVal);
            port.close();

            Log.d(TAG, "onClick: Write to Port");
        }catch (IOException error){
            Log.e(TAG, "IO Exception: error getting the port", error);
        }
    }
}
