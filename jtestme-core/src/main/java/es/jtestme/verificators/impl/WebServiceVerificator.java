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

import com.sun.star.lang.IllegalArgumentException;

import es.jtestme.domain.VerificatorResult;

public final class WebServiceVerificator extends AbstractVerificator {

    private static final String PARAM_PROTOCOL = "protocol";
    private static final String PARAM_ENDPOINT = "endpoint";
    private static final String PARAM_NAMESPACE_URI = "namespaceuri";
    private static final String PARAM_LOCAL_PART = "localpart";
    private static final String PARAM_TRUSTSTORE = "truststore";
    private static final String PARAM_TRUSTSTOREPASSWORD = "truststorepassword";

    private static final int MAX_CONNECT_TIMEOUT = 1000;

    private final String protocol;
    private final WebServiceProtocolType protocolType;
    private final String endPoint;
    private final String namespaceURI;
    private final String localPart;
    private final String trustStore;
    private final String trustStorePassword;

    public WebServiceVerificator(final Map<String, String> params) {
        super(params);
        this.protocol = getParamString(PARAM_PROTOCOL, "soap");
        this.protocolType = WebServiceProtocolType.toType(this.protocol);
        this.endPoint = getParamString(PARAM_ENDPOINT);
        this.namespaceURI = getParamString(PARAM_NAMESPACE_URI);
        this.localPart = getParamString(PARAM_LOCAL_PART);
        this.trustStore = getParamString(PARAM_TRUSTSTORE);
        this.trustStorePassword = getParamString(PARAM_TRUSTSTOREPASSWORD);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();
        if (this.protocolType == null) {
            result.setMessage("WebService protocol not supported [RPC|SOAP|REST]: " + this.protocol);
        } else {
            loadTrustStore(this.trustStore, this.trustStorePassword);
            try {
                switch (this.protocolType) {
                case RPC:
                    result.setSuccess(testRPC());
                    break;
                case SOAP:
                    result.setSuccess(testSOAP());
                    break;
                case REST:
                    result.setSuccess(testREST());
                    break;
                default:
                    throw new IllegalArgumentException("Unsatisfied Web Service type: SOAP or RPC or REST");
                }
            } catch (final Throwable e) {
                result.setCause(e);
            } finally {
                relaseTrustStore(this.trustStore, this.trustStorePassword);
            }
        }
        return result;
    }

    private boolean testRPC() throws javax.xml.rpc.ServiceException, RemoteException, MalformedURLException {
        final javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        final java.net.URL url = new java.net.URL(this.endPoint);
        final javax.xml.namespace.QName serviceName = new javax.xml.namespace.QName(this.namespaceURI, this.localPart);
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
        if (this.namespaceURI != null && this.localPart != null) {
            body.addBodyElement(new QName(this.namespaceURI, this.localPart));
        }

        final SOAPMessage response = connection.call(message, this.endPoint);
        connection.close();
        return response != null;
    }

    private boolean testREST() throws MalformedURLException, IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(this.endPoint).openConnection();
            connection.setConnectTimeout(MAX_CONNECT_TIMEOUT);
            final int responseCode = connection.getResponseCode();
            return responseCode >= HttpURLConnection.HTTP_OK && responseCode <= HttpURLConnection.HTTP_PARTIAL;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            relaseTrustStore(this.trustStore, this.trustStorePassword);
        }
    }

    enum WebServiceProtocolType {

        RPC,
        SOAP,
        REST;

        static WebServiceProtocolType toType(final String str) {
            return str != null ? valueOf(str.toUpperCase()) : null;
        }
    }
}
