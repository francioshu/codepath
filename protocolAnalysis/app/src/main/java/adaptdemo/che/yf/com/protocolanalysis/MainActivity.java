package adaptdemo.che.yf.com.protocolanalysis;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String domain;
    String intents;
    String number;
    HashMap<String, Byte> domainMap = new HashMap<String, Byte>();
    HashMap<String, Byte> inentMap = new HashMap<String, Byte>();


    byte[] bytes = null;
    byte[] temp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d(TAG, " onCreate");

        domain = "telephone";
        intents = "call";
        number = "张三";


        for (int i = 0; i < Config.DOMAIN.length; i++) {
            domainMap.put(Config.DOMAIN[i], Config.DOMAIN_VALUE[i]);
        }
        inentMap.put("call", Config.INTENT_VALUE[0]);

        try {
            temp = number.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "temp   :" + temp.toString());
                if (domainMap.containsKey(domain)) {
                    Byte domainvalue = domainMap.get(domain);
                    Log.d(TAG, "value   :" + domainvalue);
                    if (inentMap.containsKey(intents)) {
                        Byte inentvalue = inentMap.get(intents);
                        Log.d(TAG, "value   :" + inentvalue.byteValue());

                        bytes = new byte[temp.length + 6];

                        int a = Integer.toHexString(temp.length);
                        bytes[0] = (byte) 0x22;
                        bytes[1] = (byte) 0x66;
                        bytes[2] = (byte) 0x07;
                        bytes[3] = (byte) 0x44;
                        bytes[4] = domainvalue;
                        bytes[5] = inentvalue;
                        bytes[6] = (byte) a;

                        System.arraycopy(temp, 0, bytes, 6, temp.length);

                        for (int i = 0; i < bytes.length; i++) {
                            Log.d(TAG, "bytes"+i+"       :" + bytes[i]);
                        }
                    }
                    byte[] num = new byte[bytes.length - 6];

                    System.arraycopy(bytes, 6, num, 0, num.length);



                    for (int i = 0; i < num.length; i++) {
                        Log.d(TAG, "num   :" + num[i]);
                    }
                    String t = new String(num);
                    Log.d(TAG, "tttttt    :" + t);
                }
            }
        });



    }
}
