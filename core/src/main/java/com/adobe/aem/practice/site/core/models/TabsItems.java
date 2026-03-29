package com.adobe.aem.practice.site.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TabsItems {

    @ValueMapValue
    @Default(values = "Tab")
    private String title;

    @ValueMapValue
    private String content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
