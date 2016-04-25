package test.http;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

// ruby -rsinatra -e 'set :port, 8000; set :public_folder, "./", post("/"){"Hello world"}'
public class Utility {

	private static final Semaphore semaphore = new Semaphore(20);
	
	public static String sendHttpRequest() throws Exception {

		HttpURLConnection conn = null;
		BufferedOutputStream ou = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();

		URL url = new URL("http://127.0.0.1:8000/");
		try {
			semaphore.acquire();
			
			// sun.net.www.protocol.http.HttpURLConnection
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);

//			conn.setRequestProperty("Connection", "Close");
			conn.setRequestMethod("POST");

			ou = new BufferedOutputStream(conn.getOutputStream());
			ou.write("Hello".getBytes());

			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while ((line = in.readLine()) != null)
				result.append(line);
		} finally {
			if (ou != null)
				ou.close();
			if (in != null)
				in.close();
			if (conn != null)
				conn.disconnect();
			semaphore.release();
		}
		return result.toString();
	}

	public static String sendHttpRequestCommons() throws Exception {

		List<Header> headers = new ArrayList<Header>();
//		headers.add(new BasicHeader("Connection", "Close"));

		HttpClient httpClient = HttpClientBuilder.create().setDefaultHeaders(headers).build();
		HttpPost method = new HttpPost("http://127.0.0.1:8000/");
		HttpResponse response = httpClient.execute(method);

		return EntityUtils.toString(response.getEntity(), "UTF-8");
	}
}
