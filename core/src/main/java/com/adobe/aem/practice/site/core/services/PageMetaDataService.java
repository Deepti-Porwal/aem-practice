package com.adobe.aem.practice.site.core.services;

import com.day.cq.wcm.api.Page;

public interface PageMetaDataService {
    String getTitle(Page page);

    String getDescription(Page page);

    String getTemplate(Page page);

    String getLanguage(Page page);

    String getLastModified(Page page);
}
