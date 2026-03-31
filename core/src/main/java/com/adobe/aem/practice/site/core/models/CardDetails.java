package com.adobe.aem.practice.site.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardDetails {

    @ValueMapValue
    private String title;
    @ValueMapValue
    private String description;
    @ValueMapValue
    private String image;
    @ValueMapValue
    private String ctaText;
    @ValueMapValue
    private String link;
    @ValueMapValue
    private boolean openInNewTab;
    @ValueMapValue
    private boolean hideImage;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getCtaText() {
        return ctaText;
    }

    public String getLink() {
        return link;
    }

    public boolean isOpenInNewTab() {
        return openInNewTab;
    }

    public boolean isHideImage() {
        return hideImage;
    }
}
