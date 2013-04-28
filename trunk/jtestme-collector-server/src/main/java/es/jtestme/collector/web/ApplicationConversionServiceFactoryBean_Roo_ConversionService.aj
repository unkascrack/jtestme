// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package es.jtestme.collector.web;

import es.jtestme.collector.domain.Application;
import es.jtestme.collector.domain.Owner;
import es.jtestme.collector.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<Long, Application> ApplicationConversionServiceFactoryBean.getIdToApplicationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, es.jtestme.collector.domain.Application>() {
            public es.jtestme.collector.domain.Application convert(java.lang.Long id) {
                return Application.findApplication(id);
            }
        };
    }
    
    public Converter<String, Application> ApplicationConversionServiceFactoryBean.getStringToApplicationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, es.jtestme.collector.domain.Application>() {
            public es.jtestme.collector.domain.Application convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Application.class);
            }
        };
    }
    
    public Converter<Long, Owner> ApplicationConversionServiceFactoryBean.getIdToOwnerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, es.jtestme.collector.domain.Owner>() {
            public es.jtestme.collector.domain.Owner convert(java.lang.Long id) {
                return Owner.findOwner(id);
            }
        };
    }
    
    public Converter<String, Owner> ApplicationConversionServiceFactoryBean.getStringToOwnerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, es.jtestme.collector.domain.Owner>() {
            public es.jtestme.collector.domain.Owner convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Owner.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getApplicationToStringConverter());
        registry.addConverter(getIdToApplicationConverter());
        registry.addConverter(getStringToApplicationConverter());
        registry.addConverter(getOwnerToStringConverter());
        registry.addConverter(getIdToOwnerConverter());
        registry.addConverter(getStringToOwnerConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
