package es.jtestme.executors.impl;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import es.jtestme.domain.JTestMeResult;

public class WebServiceExecutor extends JTestMeDefaultExecutor {

    private static final String PARAM_PROTOCOL = "protocol";
    private static final String PARAM_ENDPOINT = "endpoint";
    private static final String PARAM_NAMESPACE_URI = "namespaceuri";
    private static final String PARAM_LOCAL_PART = "localpart";
    private static final String PARAM_TRUSTSTORE = "truststore";
    private static final String PARAM_TRUSTSTOREPASSWORD = "truststorepassword";

    private final String protocol;
    private final String endPoint;
    private final String namespaceURI;
    private final String localPart;
    private final String trustStore;
    private final String trustStorePassword;

    public WebServiceExecutor(final Map<String, String> params) {
        super(params);
        protocol = getParamString(PARAM_PROTOCOL, "soap");
        endPoint = getEndPoint();
        namespaceURI = getParamString(PARAM_NAMESPACE_URI);
        localPart = getParamString(PARAM_LOCAL_PART);
        trustStore = getParamString(PARAM_TRUSTSTORE);
        trustStorePassword = getParamString(PARAM_TRUSTSTOREPASSWORD);
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();

        loadTrustStore(trustStore, trustStorePassword);

        try {
            if ("rpc".equalsIgnoreCase(protocol)) {
                result.setSuscess(testRPC());
            } else if ("rest".equalsIgnoreCase(protocol)) {
                result.setSuscess(testREST());
            } else if ("soap".equalsIgnoreCase(protocol)) {
                result.setSuscess(testSOAP());
            } else {
                result.setMessage("WebService protocol not supported [RPC|SOAP|REST]: " + protocol);
            }
        } catch (final Throwable e) {
            result.setCause(e);
        } finally {
            relaseTrustStore(trustStore, trustStorePassword);
        }
        return result;
    }

    private boolean testRPC() throws javax.xml.rpc.ServiceException, RemoteException, MalformedURLException {
        final javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        final java.net.URL url = new java.net.URL(endPoint);
        final javax.xml.namespace.QName serviceName = new javax.xml.namespace.QName(namespaceURI, localPart);
        final javax.xml.rpc.Service service = serviceFactory.createService(url, serviceName);
        return service != null;
    }

    private boolean testREST() {
        // TODO Auto-generated method stub
        return false;
    }

    private boolean testSOAP() throws MalformedURLException, SOAPException {
        final SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        final SOAPConnection connection = soapConnectionFactory.createConnection();

        final MessageFactory factory = MessageFactory.newInstance();
        final SOAPMessage message = factory.createMessage();

        final SOAPHeader header = message.getSOAPHeader();
        header.detachNode();
        final SOAPBody body = message.getSOAPBody();
        body.addBodyElement(new QName(namespaceURI, localPart));

        final SOAPMessage response = connection.call(message, endPoint);
        connection.close();
        return response != null;
    }

    private String getEndPoint() {
        String endPoint = getParamString(PARAM_ENDPOINT);
        if (endPoint != null && !endPoint.toLowerCase().endsWith("?wsld")) {
            endPoint = endPoint + "?wsdl";
        }
        return endPoint;
    }
}
