package com.adobe.aem.practice.site.core.models;

import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.Resource;
@Model(adaptables = Resource.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselItems {
    
    @ValueMapValue
    private String title;
    
    @ValueMapValue
    private String description;
    
    @ValueMapValue
    private String image;
    
    @ValueMapValue
    private String link;
    
    @ValueMapValue
    @Default(values = "false")
    private boolean openInNewTab;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        if(link != null && link.startsWith("/content")) {
            return link + ".html";
        }
        return link;
    }

    public boolean isOpenInNewTab() {
        return openInNewTab;
    }
}
