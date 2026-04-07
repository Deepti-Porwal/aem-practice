package com.adobe.aem.practice.site.core.models;

import com.adobe.aem.practice.site.core.services.PageMetaDataService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PageInfoModel {

    @ScriptVariable
    private Page currentPage;

    @OSGiService
    PageMetaDataService pageMetaDataService;

    private String title;
    private String description;
    private static final Logger LOG= LoggerFactory.getLogger(PageInfoModel.class);

    @PostConstruct
    protected void init(){
        if(currentPage == null){
            LOG.debug("Page is coming as null");
            return;
        }
        if(pageMetaDataService == null){
            LOG.debug("pageMetaDataService is coming as null");
            return;
        }
        title= pageMetaDataService.getTitle(currentPage);
        description= pageMetaDataService.getDescription(currentPage);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
