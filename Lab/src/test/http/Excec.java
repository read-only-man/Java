package test.http;

import java.util.ArrayList;
import java.util.List;

// connection:Connection refusedÅ@Ç™èoÇ»Ç¢Ç©ÅH
public class Excec {

	public static void main(String[] args) {

		List<Thread> list = new ArrayList<Thread>(1000);
		for (int i = 0; i < 1000; i++) {
			list.add(new Thread(new Drive(i + 1)));
		}

		list.forEach(th -> th.start());

	}

}

class Drive implements Runnable {

	private int serial;

	public Drive(int serial) {
		this.serial = serial;
	}

	@Override
	public void run() {
		try {
			String ret = Utility.sendHttpRequest();
			// String ret = Utility.sendHttpRequestCommons();
			System.out.println(serial + " : " + ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
