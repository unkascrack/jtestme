package es.jtestme.verificators.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.utils.JTestMeUtils;

public final class FTPVerificator extends AbstractVerificator {

    private static final int DEFAULT_FTP_PORT = 21;

    private static final String PARAM_HOST = "host";
    private static final String PARAM_PORT = "port";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";

    private final String host;
    private final Integer port;
    private final String username;
    private final String password;

    public FTPVerificator(final Map<String, String> params) {
        super(params);
        this.host = getParamString(PARAM_HOST);
        this.port = getParamInteger(PARAM_PORT, DEFAULT_FTP_PORT);
        this.username = getParamString(PARAM_USERNAME, "anonymous");
        this.password = getParamString(PARAM_PASSWORD, "anonymous");
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();

        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            socket = new Socket(this.host, this.port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String response = readLine(reader);
            if (!response.startsWith("220 ")) {
                throw new IOException("FTP received an unknown response when connecting: " + response);
            }

            sendLine(writer, "USER " + this.username);
            response = readLine(reader);
            if (!response.startsWith("331 ")) {
                throw new IOException("FTP received an unknown response after sending the user: " + response);
            }

            sendLine(writer, "PASS " + this.password);
            response = readLine(reader);
            if (!response.startsWith("230 ")) {
                throw new IOException("FTP was unable to log in with the supplied password: " + response);
            }
            result.setSuccess(true);
            sendLine(writer, "QUIT");
        } catch (final IOException e) {
            result.setCause(e);
        } catch (final Throwable e) {
            result.setCause(e);
        } finally {
            JTestMeUtils.closeQuietly(reader);
            JTestMeUtils.closeQuietly(writer);
            JTestMeUtils.closeQuietly(socket);
        }
        return result;
    }

    /**
     * @param reader
     * @return
     * @throws IOException
     */
    private String readLine(final BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    /**
     * @param writer
     * @param line
     * @throws IOException
     */
    private void sendLine(final BufferedWriter writer, final String line) throws IOException {
        writer.write(line + "\r\n");
        writer.flush();
    }
}
