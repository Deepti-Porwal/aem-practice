package com.adobe.aem.practice.site.core.models.impl;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccordionItems {

    @ValueMapValue
    @Default(values = "Title")
    private String title;
    @ValueMapValue
    private String description;
    @ValueMapValue
    @Default(booleanValues = false)
    private boolean expanded;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isExpanded() {
        return expanded;
    }
}
