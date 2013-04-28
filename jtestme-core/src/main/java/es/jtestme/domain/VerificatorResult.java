package es.jtestme.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;

import es.jtestme.viewers.Viewer;

@XStreamAlias("verificator")
public class VerificatorResult implements Serializable {

    private static final long serialVersionUID = -2134315643510887806L;

    @XStreamAlias("type")
    private String type;
    
    @XStreamAlias("name")
    private String name;
    
    @XStreamAlias("description")
    private String description;
    
    @XStreamAlias("success")
	@XStreamConverter(value=BooleanConverter.class, booleans={false}, strings={"OK", "ERROR"})
    private boolean success = false;
    
    @XStreamAlias("message")
    private String message;
    
    @XStreamAlias("cause")
    private Throwable cause;
    
    @XStreamAlias("resolution")
    private String resolution;

    @XStreamOmitField
    private boolean optional = false;
    
    @XStreamOmitField
    private Map<String, String> parameters = new HashMap<String, String>();


    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description != null ? description : "";
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    
    public boolean isSuccess() {
        return success;
    }

    public String getSuccessString() {
        return success ? "OK" : "ERROR";
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message != null ? message : "";
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Throwable getCause() {
        return cause;
    }

    public String getCauseString() {
        final StringBuilder builder = new StringBuilder();
        if (cause != null) {
            final StackTraceElement elements[] = cause.getStackTrace();
            for (final StackTraceElement element : elements) {
                builder.append(element).append(Viewer.NEW_LINE);
            }
        }
        return builder.toString();
    }

    public void setCause(final Throwable cause) {
        this.cause = cause;
        message = cause == null ? message : cause.getMessage() != null ? cause.getMessage() : cause.toString();
    }

    public String getResolution() {
        return resolution != null ? resolution : "";
    }

    public void setResolution(final String resolution) {
        this.resolution = resolution;
    }

    public boolean isOptional() {
        return optional;
    }

    public String getOptionalString() {
        return Boolean.toString(optional);
    }

    public void setOptional(final boolean optional) {
        this.optional = optional;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void addParameter(final String key, final String value) {
        parameters.put(key, value);
    }

    public void setParameters(final Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("type: ").append(getType()).append(", ");
        sb.append("name: ").append(getName()).append(", ");
        sb.append("description: ").append(getDescription()).append(", ");
        sb.append("optional: ").append(getOptionalString()).append(", ");
        sb.append("success: ").append(isSuccess()).append(", ");
        if (!isSuccess()) {
            sb.append("message: ").append(getMessage()).append(", ");
            sb.append("resolution: ").append(getResolution()).append(", ");
            sb.append("cause: ").append(getCauseString()).append(", ");
        }
        return sb.toString();
    }
}
