//create IP address. create wifi connection

Runnable wifiConnectRunnable = new Runnable() {
    @Override
    public void run() {
        try {
            IP = PreferencesUtils.getString(App.getInstance(), PreferencesUtils.MODEL_IP, "192.168.2.1");
            PORT = PreferencesUtils.getString(App.getInstance(), PreferencesUtils.MODEL_PORT, "9100");
            socketAddress = new InetSocketAddress(IP, Integer.parseInt(PORT)); //获取sockaddress对象
            socket = new Socket(); //实例化socket
            socket.connect(socketAddress, 2000); //设置超时参数
            Log.e(TAG, "wifi: " + "wifi连接成功");
            handler.sendEmptyMessage(1);
        } catch (Exception e) {
            Log.e(TAG, "wifi: " + e.getMessage());
            e.printStackTrace();
        }
    }
};

/**
 * get IO stream
*/

mOutputStream = socket.getOutputStream();
mInputStream = socket.getInputStream();

/**
 * read message
*/

private class ReadThread extends Thread {
    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            int size;
            try {
                byte[] buffer = new byte[512];
                if (mInputStream == null) return;
                size = mInputStream.read(buffer);
                if (size > 0) {
                    String mReception = new String(buffer, 0, size);
                    String msg = mReception.toString().trim();
                    Log.e(TAG, "接收短消息：" + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
/**
 * write message
*/
private class WriteRunnable implements Runnable {
	@Override
	public void run() {
		try {
			String cmd="KZMT;";
			Log.e(TAG, "发送短消息：" + cmd);
			mOutputStream.write(cmd.getBytes());
			mOutputStream.flush();
		} catch (IOException e) {
 
		}
	}
}

/**
 * disconnnect WIFI 
*/
public void closeWifiStream() {
	try {
		if (mOutputStream != null) {
			mOutputStream.close();
			mOutputStream = null;
		}
		if (mInputStream != null) {
			mInputStream.close();
			mInputStream = null;
		}
		if (socket != null) {
			socket.close();
			socket = null;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

