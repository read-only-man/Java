package com.restfully.shop.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;

import com.restfully.shop.domain.Customer;

public final class CustomerUtil {

    private CustomerUtil() {
        ;
    }

    public static Customer readCustomer(InputStream is) {
        try {
            /*
             * DocumentBuilder builder =
             * DocumentBuilderFactory.newInstance().newDocumentBuilder();
             * Document doc = builder.parse(is); Element root =
             * doc.getDocumentElement();
             * 
             * Customer customer = new Customer();
             * 
             * customer.setId( StringUtils.isNotEmpty(root.getAttribute("id")) ?
             * Integer.valueOf(root.getAttribute("id")) : null);
             * 
             * NodeList nodes = root.getChildNodes(); for (int i = 0; i <
             * nodes.getLength(); i++) { Element elem = (Element) nodes.item(i);
             * switch (elem.getTagName()) { case "first-name":
             * customer.setFirstName(elem.getTextContent()); break; case
             * "last-name": customer.setLastName(elem.getTextContent()); break;
             * case "street": customer.setStreet(elem.getTextContent()); break;
             * case "city": customer.setCity(elem.getTextContent()); break; case
             * "state": customer.setState(elem.getTextContent()); break; case
             * "zip": customer.setZip(elem.getTextContent()); break; case
             * "country": customer.setCountry(elem.getTextContent()); break;
             * default: } } return customer;
             */

            return JAXB.unmarshal(is, Customer.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    public static void outputCustomer(OutputStream os, Customer customer) {
        PrintStream writer = new PrintStream(os);
        writer.println("<customer id=\"" + customer.getId() + "\">");
        writer.println("<first-name>" + customer.getFirstName() + "</first-name>");
        writer.println("<last-name>" + customer.getLastName() + "</last-name>");
        writer.println("<street>" + customer.getStreet() + "</street>");
        writer.println("<city>" + customer.getCity() + "</city>");
        writer.println("<state>" + customer.getState() + "</state>");
        writer.println("<zip>" + customer.getZip() + "</zip>");
        writer.println("<country>" + customer.getCountry() + "</country>");
        writer.println("</customer>");
    }
}
