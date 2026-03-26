package com.adobe.aem.practice.site.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TeaserItemImpl {

    private static final Logger LOG = LoggerFactory.getLogger(TeaserItemImpl.class);
    @ValueMapValue
    public String title;

    @ValueMapValue
    public String description;

    @ValueMapValue (name = "fileReference")
    public String image;

    @ValueMapValue
    public String link;

    public String getTitle() {
        LOG.debug("Title::{}",title);
        return title;
    }

    public String getDescription() {
        LOG.debug("Description::{}",description);
        return description;
    }

    public String getImage() {
        LOG.debug("Image Path::{}",image);
        return image;
    }

    public String getLink() {
        if(link != null && link.startsWith("/content")) {
            LOG.debug("Link Path::{}",link);
            return link + ".html";
        }
        return link;
    }
}
