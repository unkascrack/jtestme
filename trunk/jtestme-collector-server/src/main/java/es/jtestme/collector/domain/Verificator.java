package es.jtestme.collector.domain;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooEquals
@RooSerializable
public class Verificator {

    @NotNull
    private String name;

    @NotNull
    private String type;

    @NotNull
    private String success;

    private String message;

    private String cause;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationState applicationState;
}
