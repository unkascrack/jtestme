package es.jtestme.verificators.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

import es.jtestme.domain.VerificatorResult;

public class WebServiceVerificator extends AbstractVerificator {

    private static final String PARAM_PROTOCOL = "protocol";
    private static final String PARAM_ENDPOINT = "endpoint";
    private static final String PARAM_NAMESPACE_URI = "namespaceuri";
    private static final String PARAM_LOCAL_PART = "localpart";
    private static final String PARAM_TRUSTSTORE = "truststore";
    private static final String PARAM_TRUSTSTOREPASSWORD = "truststorepassword";

    private final String protocol;
    private final WebServiceProtocolType protocolType;
    private final String endPoint;
    private final String namespaceURI;
    private final String localPart;
    private final String trustStore;
    private final String trustStorePassword;

    public WebServiceVerificator(final Map<String, String> params) {
        super(params);
        protocol = getParamString(PARAM_PROTOCOL, "soap");
        protocolType = WebServiceProtocolType.toType(protocol);
        endPoint = getParamString(PARAM_ENDPOINT);
        namespaceURI = getParamString(PARAM_NAMESPACE_URI);
        localPart = getParamString(PARAM_LOCAL_PART);
        trustStore = getParamString(PARAM_TRUSTSTORE);
        trustStorePassword = getParamString(PARAM_TRUSTSTOREPASSWORD);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();
        if (protocolType == null) {
            result.setMessage("WebService protocol not supported [RPC|SOAP|REST]: " + protocol);
        } else {
            loadTrustStore(trustStore, trustStorePassword);
            try {
                switch (protocolType) {
                    case RPC:
                        result.setSuccess(testRPC());
                    break;
                    case SOAP:
                        result.setSuccess(testSOAP());
                    break;
                    case REST:
                        result.setSuccess(testREST());
                    break;
                }
            } catch (final Throwable e) {
                result.setCause(e);
            } finally {
                relaseTrustStore(trustStore, trustStorePassword);
            }
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

    private boolean testSOAP() throws MalformedURLException, SOAPException {
        final SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        final SOAPConnection connection = soapConnectionFactory.createConnection();

        final MessageFactory factory = MessageFactory.newInstance();
        final SOAPMessage message = factory.createMessage();

        final SOAPHeader header = message.getSOAPHeader();
        header.detachNode();
        final SOAPBody body = message.getSOAPBody();
        if (namespaceURI != null && localPart != null) {
            body.addBodyElement(new QName(namespaceURI, localPart));
        }

        final SOAPMessage response = connection.call(message, endPoint);
        connection.close();
        return response != null;
    }

    private boolean testREST() throws MalformedURLException, IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(endPoint).openConnection();
            connection.setConnectTimeout(1000);
            final int responseCode = connection.getResponseCode();
            return responseCode >= 200 && responseCode <= 399;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            relaseTrustStore(trustStore, trustStorePassword);
        }
    }

    enum WebServiceProtocolType {

        RPC,
        SOAP,
        REST;

        static WebServiceProtocolType toType(final String str) {
            WebServiceProtocolType type = null;
            try {
                type = str != null ? valueOf(str.toUpperCase()) : null;
            } catch (final IllegalArgumentException e) {
                type = null;
            }
            return type;
        }
    }
}
