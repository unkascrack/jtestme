package es.jtestme.collector.domain.reference;

public enum StateType {

    OK,
    ERROR,
    NO_CONNECT,
    NO_PERMISSION;

    public static StateType getStateTypeByHttpResponseCode(int code) {
        StateType type = null;
        if (code >= 200 && code <= 399) {
            type = OK;
        } else if (code >= 500 && code <= 599) {
            type = NO_PERMISSION;
        } else {
            type = NO_CONNECT;
        }
        return type;
    }

    public boolean isOk() {
        return this == OK;
    }

    public boolean isError() {
        return this == ERROR;
    }

    public boolean isNoConnect() {
        return this == NO_CONNECT || this == NO_PERMISSION;
    }
}
