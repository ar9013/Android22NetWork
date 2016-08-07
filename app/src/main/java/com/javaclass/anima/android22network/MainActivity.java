package com.javaclass.anima.android22network;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View stat,myip;
    private TextView mesg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mesg = (TextView) findViewById(R.id.mesg);
        stat = findViewById(R.id.stat);
        myip = findViewById(R.id.ip);

        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNetworkInfo();
            }
        });

        myip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyIPAddress();
            }
        });

    }

    private void getNetworkInfo(){
        StringBuffer sb = new StringBuffer();
        ConnectivityManager cmgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cmgr.getActiveNetworkInfo();

        if(networkInfo!= null && networkInfo.isConnected()){
            sb.append("Type: " + networkInfo.getTypeName() + "\n");

            try{
                Enumeration<NetworkInterface> ifcs = NetworkInterface.getNetworkInterfaces();

                while (ifcs.hasMoreElements()){
                    NetworkInterface ifc = ifcs.nextElement();
                    sb.append("Interface: " + ifc.getDisplayName() + "\n");
                    List<InterfaceAddress> ips = ifc.getInterfaceAddresses();
                    for (InterfaceAddress ip : ips) {
                        sb.append("\tIP: " + ip.getAddress().getHostAddress()
                                + "\n");
                    }
                }

            }catch (SocketException e){
                sb.append("XXX");
            }
        }else {
            sb.append("Not Connect");
        }
        mesg.setText(sb.toString());
    }

    private void getMyIPAddress(){
        try{

            for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){

            NetworkInterface inf = en.nextElement();
                for(Enumeration<InetAddress> enumIpAddress = inf.getInetAddresses(); enumIpAddress.hasMoreElements();){
                    InetAddress inetAddress = enumIpAddress.nextElement();

                    if(!inetAddress.isLoopbackAddress()){
                        mesg.setText(inetAddress.getHostAddress());
                    }

                }
            }

        }catch (Exception e){

        }
    }
}
