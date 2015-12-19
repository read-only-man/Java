package com.restfully.shop.services.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.HttpMethod;

import org.junit.Test;

import com.restfully.shop.domain.Customer;

public class ObjectStreamTest {

	@Test
	public void test01() throws IOException {
		System.out.println("Create a new Customer");
		Customer param = new Customer();
		param.setFirstName("Bill");
		param.setLastName("Burke");
		param.setStreet("256 Claredon Street");
		param.setCity("Boston");
		param.setState("MA");
		param.setZip("02115");
		param.setCountry("USA");

		URL postURL = new URL("http://localhost:8080/JAX-RS-test05/test1/customers/");
		HttpURLConnection conn = (HttpURLConnection) postURL.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod(HttpMethod.POST);
		conn.setRequestProperty("Content-type", "application/x-java-serualized-object");
		// conn.setRequestProperty("Content-Type", MediaType.APPLICATION_XML);

		OutputStream os = conn.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(param);
		oos.flush();
		oos.close();
		conn.disconnect();
	}

	@Test
	public void test02() throws IOException {
		System.out.println("Get a Customer");

		URL getURL = new URL("http://localhost:8080/JAX-RS-test05/test1/customers/1/");
		HttpURLConnection conn = (HttpURLConnection) getURL.openConnection();
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod(HttpMethod.GET);

		InputStream is = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = "";
		while ((line = reader.readLine()) != null) {
			System.out.print(line);
		}
		reader.close();
		conn.disconnect();
	}

}
