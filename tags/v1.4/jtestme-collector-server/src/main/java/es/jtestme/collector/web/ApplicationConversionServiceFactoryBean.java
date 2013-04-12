package es.jtestme.collector.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import es.jtestme.collector.domain.Application;
import es.jtestme.collector.domain.Environment;
import es.jtestme.collector.domain.Owner;
import es.jtestme.collector.domain.Server;

/**
 * A central place to register application converters and formatters.
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(final FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
		registry.addConverter(getOwnerToStringConverter());
		registry.addConverter(getApplicationToStringConverter());
		registry.addConverter(getServerToStringConverter());
		registry.addConverter(getEnvironmentToStringConverter());
	}

	public Converter<Owner, String> getOwnerToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<es.jtestme.collector.domain.Owner, java.lang.String>() {
			@Override
			public String convert(final Owner owner) {
				return new StringBuilder().append(owner.getEmail()).toString();
			}
		};
	}

	public Converter<Application, String> getApplicationToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<es.jtestme.collector.domain.Application, java.lang.String>() {
			@Override
			public String convert(final Application application) {
				return new StringBuilder().append(application.getName()).toString();
			}
		};
	}

	public Converter<Environment, String> getEnvironmentToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<es.jtestme.collector.domain.Environment, java.lang.String>() {
			@Override
			public String convert(final Environment environment) {
				return new StringBuilder().append(environment.getEnvironmentType()).append(' ')
						.append(environment.getName()).toString();
			}
		};
	}

	public Converter<Server, String> getServerToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<es.jtestme.collector.domain.Server, java.lang.String>() {
			@Override
			public String convert(final Server server) {
				return new StringBuilder().append(server.getServerType()).append(' ').append(server.getUrl())
						.toString();
			}
		};
	}
}
