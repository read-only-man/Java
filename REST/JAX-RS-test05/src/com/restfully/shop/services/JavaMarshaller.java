package com.restfully.shop.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

// @Providerで注釈しすると、アノテーションスキャンで拾ってくれます
@Provider
@Produces("application/x-java-serualized-object")
@Consumes("application/x-java-serualized-object")
public class JavaMarshaller implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

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
		ObjectInputStream ois = new ObjectInputStream(is);

		try {
			return ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getSize(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream os) throws IOException, WebApplicationException {
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(o);
	}
}
