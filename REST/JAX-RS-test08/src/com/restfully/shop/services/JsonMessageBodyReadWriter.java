package com.restfully.shop.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JsonMessageBodyReadWriter implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

	@Override
	public boolean isReadable(Class<?> clazz, Type genericType, Annotation[] annotations, MediaType mediatype) {
		// たぶんシリアライズ可能かどうかを見ている
		return Serializable.class.isAssignableFrom(clazz);
	}

	@Override
	public boolean isWriteable(Class<?> clazz, Type genericType, Annotation[] annotations, MediaType mediatype) {
		// たぶんシリアライズ可能かどうかを見ている
		return Serializable.class.isAssignableFrom(clazz);
	}

	@Override
	public Object readFrom(Class<Object> clazz, Type genericType, Annotation[] annotations, MediaType mediatype,
			MultivaluedMap<String, String> httpHeaders, InputStream is) throws IOException, WebApplicationException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder buf = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null)
			buf.append(line);
		String json = buf.toString();

		ObjectMapper mapper = new ObjectMapper();
		Object o = mapper.readValue(json, clazz);
		return o;
	}

	@Override
	public long getSize(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException, WebApplicationException {
		ObjectMapper mapper = new ObjectMapper();
		
		// JSONを見やすいようにフォーマットする。
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		String json = mapper.writeValueAsString(o);

		PrintWriter writer = new PrintWriter(os);
		writer.println(json);
		writer.flush();
	}

}
