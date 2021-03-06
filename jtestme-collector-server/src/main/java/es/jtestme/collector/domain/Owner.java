package es.jtestme.collector.domain;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
public class Owner {

    @NotNull
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @NotNull
    @Size(max = 100)
    @Pattern(regexp = "[a-zA-Z0-9\\.\\-]+@[a-zA-Z0-9\\.]+")
    private String email;

    @Size(max = 10)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;
}
