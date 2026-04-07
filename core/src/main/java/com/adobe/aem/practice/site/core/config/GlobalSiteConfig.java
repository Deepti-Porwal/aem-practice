package com.adobe.aem.practice.site.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "Global Site Configuration",
        description = "Site-wide settings consumed by components across the application"
)
public @interface GlobalSiteConfig {

    @AttributeDefinition(
            name = "Site Name",
            description = "Display name of the site — used in nav, meta tags, and page title suffix",
            type = AttributeType.STRING
    )
    String siteName() default "AEM Practice Site";

    @AttributeDefinition(
            name = "Site Logo Path",
            description = "DAM path to the site logo image",
            type = AttributeType.STRING
    )
    String siteLogoPath() default "/content/dam/aem-practice/logo.png";

    @AttributeDefinition(
            name = "CDN Base URL",
            description = "Base URL prepended to DAM asset paths in production. Leave empty for local.",
            type = AttributeType.STRING
    )
    String cdnBaseUrl() default "";

    @AttributeDefinition(
            name = "Google Analytics ID",
            description = "GA4 Measurement ID (e.g. G-XXXXXXXXXX). Leave empty to disable tracking.",
            type = AttributeType.STRING
    )
    String googleAnalyticsId() default "";

    @AttributeDefinition(
            name = "Cookie Consent Endpoint",
            description = "URL of the cookie consent API used by the consent banner",
            type = AttributeType.STRING
    )
    String cookieConsentEndpoint() default "";

    @AttributeDefinition(
            name = "Social Links",
            description = "Social media URLs in label:url format e.g. Twitter:https://twitter.com/foo",
            type = AttributeType.STRING
    )
    String[] socialLinks() default {};

    @AttributeDefinition(
            name = "Enable Maintenance Mode",
            description = "When true, components can render a maintenance notice instead of content",
            type = AttributeType.BOOLEAN
    )
    boolean maintenanceMode() default false;
}