package es.jtestme.verificators.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import es.jtestme.domain.VerificatorResult;
import es.jtestme.logger.JTestMeLogger;

public final class FilePermissionVerificator extends AbstractVerificator {

    private static final String PARAM_PATH = "path";

    private final String path;

    public FilePermissionVerificator(final Map<String, String> params) {
        super(params);
        this.path = getParamString(PARAM_PATH);
    }

    public VerificatorResult execute() {
        final VerificatorResult result = super.getResult();
        if (this.path == null) {
            result.setMessage(getClass().getSimpleName() + ": no se ha definido la ruta para verificar.");
            return result;
        }
        try {
            final File file = new File(this.path);
            verificateExists(file);
            verificateDirectory(file);
            verificateFile(file);
            result.setSuccess(true);
        } catch (final Throwable cause) {
            JTestMeLogger.warn(getClass().getSimpleName() + ": " + cause.getMessage(), cause);
            result.setSuccess(false);
            result.setMessage(getClass().getSimpleName() + ":" + cause.getMessage());
            result.setCause(cause);
        }
        return result;
    }

    private void verificateExists(final File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(String.format("Path %s not found", file.getAbsolutePath()));
        }
    }

    private void verificateFile(final File file) throws IOException {
        if (file.isFile()) {
            if (!file.canRead()) {
                throw new IOException("FilePermission can read");
            }
            if (!file.canWrite()) {
                throw new IOException("FilePermission can write");
            }
        }
    }

    private void verificateDirectory(final File file) throws IOException {
        if (file.isDirectory()) {
            final String tmpFileName = ".jtestme-" + System.currentTimeMillis();
            final File sample = new File(file.getAbsolutePath(), tmpFileName);
            sample.createNewFile();
            sample.delete();
        }
    }

}
