package com.adobe.aem.practice.site.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "My Test OSGi Configuration", description = "This is a sample OSGi Configuration")
public @interface MyOSGiConfig {
    @AttributeDefinition(
            name="Service ID",
            description = "Enter Service ID",
            type = AttributeType.INTEGER)
    int serviceID();

    @AttributeDefinition(
            name="Service Name",
            description = "Enter Service Name",
            type = AttributeType.STRING)
    public String serviceName() default "My Configuration Name";

    @AttributeDefinition(
            name="Service URL",
            description = "Enter Service URL",
            type = AttributeType.STRING
    )
    public String serviceURL() default "localhost";

}
