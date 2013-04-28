package es.jtestme.collector.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

	private transient static Logger logger = LoggerFactory
			.getLogger(ConfigurationService.class);

	@Value("${http.proxyHost}")
	private transient String httpProxyHost;

	@Value("${http.proxyPort}")
	private transient String httpProxyPort;

	@Value("${https.proxyHost}")
	private transient String httpsProxyHost;

	@Value("${https.proxyPort}")
	private transient String httpsProxyPort;

	@Value("${http.nonProxyHosts}")
	private transient String nonProxyHosts;

	@Value("${javax.net.ssl.keyStore}")
	private transient String keyStore;

	@Value("${javax.net.ssl.keyStorePassword}")
	private transient String keyStorePassword;

	@Value("${javax.net.ssl.trustStore}")
	private transient String trustStore;

	@Value("${javax.net.ssl.trustStorePassword}")
	private transient String trustStorePassword;

	@PostConstruct
	public void initConfiguration() {
		configureProxy();
		configureSSL();
	}

	/**
	 * 
	 */
	private void configureProxy() {
		if (StringUtils.isNotBlank(httpProxyHost)
				&& StringUtils.isNotBlank(httpProxyPort)) {
			logger.info("http.proxyHost = " + httpProxyHost);
			logger.info("http.proxyPort = " + httpProxyPort);
			System.setProperty("http.proxyHost", httpProxyHost);
			System.setProperty("http.proxyPort", httpProxyPort);
		}
		if (StringUtils.isNotBlank(httpsProxyHost)
				&& StringUtils.isNotBlank(httpsProxyPort)) {
			logger.info("https.proxyPort = " + httpsProxyHost);
			logger.info("https.proxyPort = " + httpsProxyPort);
			System.setProperty("https.proxyHost", httpsProxyHost);
			System.setProperty("https.proxyPort", httpsProxyPort);
		}
		if (StringUtils.isNotBlank(nonProxyHosts)) {
			logger.info("http.nonProxyHosts = " + nonProxyHosts);
			System.setProperty("http.nonProxyHosts", nonProxyHosts);
		}
	}

	/**
	 * 
	 */
	private void configureSSL() {
		if (StringUtils.isNotBlank(keyStore)
				&& StringUtils.isNotBlank(keyStorePassword)) {
			logger.info("javax.net.ssl.keyStore = " + keyStore);
			logger.info("javax.net.ssl.keyStorePassword = " + keyStorePassword);
			System.setProperty("javax.net.ssl.keyStore", keyStore);
			System.setProperty("javax.net.ssl.keyStorePassword",
					keyStorePassword);
		}
		if (StringUtils.isNotBlank(trustStore)
				&& StringUtils.isNotBlank(trustStorePassword)) {
			logger.info("javax.net.ssl.trustStore = " + trustStore);
			logger.info("javax.net.ssl.trustStorePassword = "
					+ trustStorePassword);
			System.setProperty("javax.net.ssl.trustStore", trustStore);
			System.setProperty("javax.net.ssl.trustStorePassword",
					trustStorePassword);
		}
	}
}
