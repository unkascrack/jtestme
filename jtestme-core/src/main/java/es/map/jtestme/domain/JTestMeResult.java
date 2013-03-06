package es.map.jtestme.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JTestMeResult implements Serializable {

    private static final long serialVersionUID = -2134315643510887806L;

    private String type;
    private String name;
    private String description;
    private boolean optional = false;
    private Map<String, String> parameters = new HashMap<String, String>();
    private boolean suscess = false;
    private String message;
    private Date time;

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
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isOptional() {
        return optional;
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

    public void setSuscess(final boolean suscess) {
        this.suscess = suscess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(final Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("type: ").append(type).append(", ");
        sb.append("name: ").append(name).append(", ");
        sb.append("description: ").append(description).append(", ");
        sb.append("optional: ").append(optional).append(", ");
        sb.append("suscess: ").append(suscess).append(", ");
        sb.append("message: ").append(message).append(", ");
        sb.append("time: ").append(time).append(", ");
        sb.append("parameters: ").append(parameters);
        return sb.toString();
    }

    public String toXML() {
        final StringBuilder builder = new StringBuilder();
        return builder.toString();
    }

    public String toHTML() {
        final StringBuilder builder = new StringBuilder();
        return builder.toString();
    }

}
