package com.adobe.aem.practice.site.core.models.impl;

import com.adobe.aem.practice.site.core.models.AuthorProfile;
import com.adobe.aem.practice.site.core.models.Profile;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = AuthorProfile.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class AuthorProfileModel implements AuthorProfile {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorProfileModel.class);
    @ValueMapValue
    private String authorName;

    @ValueMapValue
    private String authorDescription;

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    @Optional
    private String ctaText;

    @ValueMapValue
    @Optional
    private String ctaLink;

    @ValueMapValue
    @Default(values = "true")
    private boolean openInNewTab;

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public String getFileReference() {
        return fileReference;
    }

    public String getCtaText() {
        return ctaText;
    }

    public String getCtaLink() {
        if(ctaLink != null && ctaLink.startsWith("/content")) {
            return ctaLink + ".html";
        }
        return ctaLink;
    }

    @Override
    public boolean isOpenInNewTab() {
        return openInNewTab;
    }

    @PostConstruct
    protected void init() {
            LOG.debug("Inside AuthorProfileModel:: Name: {}, Description: {}, CTA Text : {}, CTA Link: {}, Image Path:{}, New Tab:{}",authorName, authorDescription, ctaText,ctaLink, fileReference, openInNewTab);
        if (ctaText == null) {
            ctaText = "View Profile";
        }
    }
}
