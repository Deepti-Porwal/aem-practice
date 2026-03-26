package com.adobe.aem.practice.site.core.models.impl;

import com.adobe.aem.practice.site.core.models.TeaserList;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
        adapters = TeaserList.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TeaserListImpl implements TeaserList{
    private static final Logger LOG = LoggerFactory.getLogger(TeaserListImpl.class);
    @ChildResource(name = "teaserdetails")
    public List<TeaserItemImpl> teasers;

    @Override
    public List<TeaserItemImpl> getTeasers() {
        if(teasers != null && !teasers.isEmpty()){
            LOG.debug("Titles: {}", teasers.get(0).getTitle());
            LOG.debug("Link: {}", teasers.get(0).getLink());
        }
        return teasers;
    }
}
