package com.adobe.aem.practice.site.core.models.impl;

import com.adobe.aem.practice.site.core.models.PromoBanner;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = PromoBanner.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class PromoBannerModel implements PromoBanner {

    private static final Logger LOG = LoggerFactory.getLogger(PromoBannerModel.class);

    @ValueMapValue
    private String promoLabel;

    @ValueMapValue
    private String ctaText;

    @ValueMapValue
    private String ctaLink;

    @Override
    public String getPromoLabel() {
        return promoLabel;
    }

    @Override
    public String getCtaText() {
        return ctaText;
    }

    @Override
    public String getCtaLink() {
        if (ctaLink != null && ctaLink.startsWith("/content")) {
            return ctaLink + ".html";
        }
        return ctaLink;
    }

    @PostConstruct
    protected void init() {
        LOG.debug("PromoBannerModel initialized with promoLabel='{}', ctaText='{}', ctaLink='{}'",
                promoLabel, ctaText, ctaLink);
    }
}
