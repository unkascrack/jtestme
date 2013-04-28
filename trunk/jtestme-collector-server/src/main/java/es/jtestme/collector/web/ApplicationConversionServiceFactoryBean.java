package es.jtestme.collector.web;

import es.jtestme.collector.domain.Application;
import es.jtestme.collector.domain.Owner;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Application, String> getApplicationToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<es.jtestme.collector.domain.Application, java.lang.String>() {
            public String convert(Application application) {
                return new StringBuilder().append(application.getName()).append('-').append(application.getEnvironmentType()).toString();
            }
        };
    }

	public Converter<Owner, String> getOwnerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<es.jtestme.collector.domain.Owner, java.lang.String>() {
            public String convert(Owner owner) {
                return new StringBuilder().append(owner.getEmail()).toString();
            }
        };
    }
}
