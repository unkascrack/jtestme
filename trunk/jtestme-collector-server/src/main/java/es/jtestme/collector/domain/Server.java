package es.jtestme.collector.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import es.jtestme.collector.domain.reference.ServerType;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooEquals
@RooSerializable
public class Server {

	@NotNull
	@Enumerated
	private ServerType serverType;

	@NotNull
	@Size(max = 100)
	private String name;

	@NotNull
	@Size(max = 255)
	private String url;

	@Size(max = 50)
	private String username;

	@Size(max = 50)
	private String password;

	private String proxyHost;

	private String proxyPort;

	private String proxyUser;

	private String proxyPassword;

	private String trustStorePassword;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Environment environment;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private final Set<ServerState> states = new HashSet<ServerState>();
}
