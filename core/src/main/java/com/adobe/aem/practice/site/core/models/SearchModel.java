package com.adobe.aem.practice.site.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchModel {

    @ValueMapValue
    private String searchRoot;
    @ValueMapValue
    private int limit;
    @ValueMapValue
    private String[] searchIn;
    @ValueMapValue
    private boolean highlight;
    @ValueMapValue
    private boolean pagination;
    @ValueMapValue
    private String sortBy;

/*    @ValueMapValue
    private int debounce;
    @ValueMapValue
    private boolean showDescription;*/

    public String getSearchRoot() {
        return searchRoot;
    }

    public int getLimit() {
        return limit;
    }

    public String[] getSearchIn() {
        return searchIn;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public boolean isPagination() {
        return pagination;
    }

    public String getSortBy() {
        return sortBy;
    }
}
