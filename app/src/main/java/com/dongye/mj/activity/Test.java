package com.dongye.mj.activity;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dongye.mj.BaseActivity;
import com.dongye.mj.R;
import com.dongye.mj.util.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;

/**
 * 测试红包功能，不要删
 * Created by yu on 15-11-16.
 */
public class Test extends BaseActivity {
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.send)
    Button btnSend;
    @Bind(R.id.grab)
    Button btnGrab;
    @Bind(R.id.et_num)
    EditText etNum;
    @Bind(R.id.et_money)
    EditText etMoney;

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;
    private static final int MESSAGE_GRAB_MSG = 3;  //抢红包
    private static final int MESSAGE_SEND_MSG = 4;  //发红包

    private Socket mClientSocket;
    private PrintWriter mPrientWriter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    tv.setText(tv.getText() + "\n" + msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    tv.setText("连接服务端成功");
                    break;
                case MESSAGE_SEND_MSG:
                    tv.setText(tv.getText() + "\n你发了一个红包！");
                    break;
                case MESSAGE_GRAB_MSG:
                    tv.setText(tv.getText() + "\n你参与了抢红包！");
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btnSend.setOnClickListener(this);
        btnGrab.setOnClickListener(this);
        new Thread() {
            @Override
            public void run() {
                super.run();
                connectTCPServer();
            }
        }.start();
    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("10.0.1.131", 6666);
                mClientSocket = socket;
                mPrientWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                LogUtil.i("connect server success");
            } catch (IOException e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
                LogUtil.e("connect server failed, retry...");
            }
        }

        //接受服务端消息
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
                    while (!Test.this.isFinishing()) {
                        String msg = br.readLine();
                        LogUtil.i("receive: " + msg);
                        if (msg != null) {
                            String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
                            final String showedMsg = "server " + time + ":\t" + msg + "\n";
                            mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMsg).sendToTarget();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    protected void onDestroy() {
        super.onDestroy();
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.grab:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //抢红包
                        grab();
                    }
                }.start();
                break;
            case R.id.send:
                if (TextUtils.isEmpty(etNum.getText()) || TextUtils.isEmpty(etMoney.getText())) {
                    showToast("数量或金额不能为空");
                    return;
                }
                if (Integer.parseInt(etNum.getText().toString())<=0 || Integer.parseInt(etNum.getText().toString())<=0) {
                    showToast("数量或金额不能为0");
                    return;
                }
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mClientSocket.getOutputStream()));
                    bw.write("f,"+etNum.getText()+","+etMoney.getText());
                    bw.newLine();
                    bw.flush();
                    mHandler.sendEmptyMessage(MESSAGE_SEND_MSG);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void grab() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mClientSocket.getOutputStream()));
            bw.write("q");
            bw.newLine();
            bw.flush();
            mHandler.sendEmptyMessage(MESSAGE_GRAB_MSG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
