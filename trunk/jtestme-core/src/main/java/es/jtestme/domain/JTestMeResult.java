package es.jtestme.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JTestMeResult implements Serializable {

    private static final long serialVersionUID = -2134315643510887806L;

    private String type;
    private String name;
    private String description;
    private String resolution;
    private boolean optional = false;
    private Map<String, String> parameters = new HashMap<String, String>();
    private boolean suscess = false;
    private String message;
    private Throwable cause;

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

    public boolean isSuscess() {
        return suscess;
    }

    public String getSuscessString() {
        return suscess ? "OK" : "KO";
    }

    public void setSuscess(final boolean suscess) {
        this.suscess = suscess;
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

    public void setCause(final Throwable cause) {
        this.cause = cause;
        message = cause != null ? cause.getMessage() : message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("type: ").append(getType()).append(", ");
        sb.append("name: ").append(getName()).append(", ");
        sb.append("description: ").append(getDescription()).append(", ");
        sb.append("optional: ").append(getOptionalString()).append(", ");
        sb.append("suscess: ").append(isSuscess()).append(", ");
        sb.append("message: ").append(getMessage()).append(", ");
        if (getCause() != null) {
            sb.append("cause: ").append(getCause()).append(", ");
        }
        sb.append("resolution: ").append(resolution).append(", ");
        sb.append("parameters: ").append(parameters);
        return sb.toString();
    }
}
