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
    private final String endpoint;
    private final String namespaceURI;
    private final String localPart;
    private final String trustStore;
    private String defaultTrustStore;
    private final String trustStorePassword;
    private String defaultTrustStorePassword;

    public WebServiceExecutor(final Map<String, String> params) {
        super(params);
        protocol = getParamString(PARAM_PROTOCOL, "soap");
        endpoint = getParamString(PARAM_ENDPOINT);
        namespaceURI = getParamString(PARAM_NAMESPACE_URI);
        localPart = getParamString(PARAM_LOCAL_PART);
        trustStore = getParamString(PARAM_TRUSTSTORE);
        trustStorePassword = getParamString(PARAM_TRUSTSTOREPASSWORD);
    }

    public JTestMeResult executeTestMe() {
        final JTestMeResult result = super.getResult();
        try {
            if ("rpc".equalsIgnoreCase(protocol)) {
                result.setSuscess(testRPC());
            } else if ("rest".equalsIgnoreCase(protocol)) {
                result.setSuscess(testREST());
            } else if ("soap".equalsIgnoreCase(protocol)) {
                result.setSuscess(testSOAP());
            } else {
                result.setMessage("WebService protocol not supported: " + protocol);
            }
        } catch (final Throwable e) {
            result.setMessage(e.toString());
        }
        return result;
    }

    private boolean testRPC() throws javax.xml.rpc.ServiceException, RemoteException {
        final javax.xml.rpc.ServiceFactory factory = javax.xml.rpc.ServiceFactory.newInstance();
        final javax.xml.rpc.Service service = factory.createService(new QName(namespaceURI));

        final QName port = new QName(localPart);
        final javax.xml.rpc.Call call = service.createCall(port);
        call.setTargetEndpointAddress(endpoint);
        final Object result = call.invoke(null);
        return result != null;
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

        final SOAPMessage response = connection.call(message, endpoint);
        connection.close();

        return response != null;
    }
}
