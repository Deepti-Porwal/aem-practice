package com.adobe.aem.practice.site.core.services.impl;

import com.adobe.aem.practice.site.core.services.PageMetaDataService;
import com.day.cq.wcm.api.Page;
import org.osgi.service.component.annotations.Component;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Component(service = PageMetaDataService.class)
public class PageMetadataServiceImpl implements PageMetaDataService {
    @Override
    public String getTitle(Page page) {
        if(page.getTitle()==null){
            return "";
        }
        return page.getTitle();
    }

    @Override
    public String getDescription(Page page) {
        if(page.getDescription()==null){
            return "";
        }
        return page.getDescription();
    }

    @Override
    public String getTemplate(Page page) {
        return page.getTemplate() != null
                ? page.getTemplate().getPath()
                : "";
    }

    @Override
    public String getLanguage(Page page) {
        if (page == null) {
            return "";
        }
        Locale locale = page.getLanguage(false);
        return locale != null ? locale.getLanguage() : "";
    }

    @Override
    public String getLastModified(Page page) {
        if (page == null || page.getLastModified() == null) {
            return "";
        }

        return new SimpleDateFormat("yyyy-MM-dd")
                .format(page.getLastModified().getTime());
    }
}


