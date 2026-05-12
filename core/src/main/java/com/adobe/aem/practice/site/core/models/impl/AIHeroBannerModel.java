package com.adobe.aem.practice.site.core.models.impl;

import com.adobe.aem.practice.site.core.models.AIHeroBanner;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = AIHeroBanner.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AIHeroBannerModel implements AIHeroBanner {

    private static final Logger LOG = LoggerFactory.getLogger(AIHeroBannerModel.class);

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String subtitle;

    @ValueMapValue
    private String backgroundImage;

    @ValueMapValue
    private String ctaText;

    @ValueMapValue
    private String ctaLink;

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    @PostConstruct
    protected void init() {
        LOG.info("AIHeroBannerModel initialized: title='{}', subtitle='{}', backgroundImage='{}', ctaText='{}', ctaLink='{}'", title, subtitle, backgroundImage, ctaText, ctaLink);
    }

    public String getBackgroundImageStyle() {
        if (backgroundImage == null || backgroundImage.isEmpty()) {
            LOG.debug("No backgroundImage value present for AIHeroBannerModel");
            return "";
        }
        String style = "background-image:url('" + backgroundImage + "')";
        LOG.info("AIHeroBannerModel backgroundImageStyle='{}'", style);
        return style;
    }

    public String getCtaText() {
        return ctaText;
    }

    public String getCtaLink() {
        return ctaLink;
    }

}
