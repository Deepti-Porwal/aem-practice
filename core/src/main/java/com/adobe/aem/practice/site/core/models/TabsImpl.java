package com.adobe.aem.practice.site.core.models;

import com.adobe.aem.practice.site.core.models.TabsItems;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TabsImpl {

    @ChildResource(name = "tabs")
   private List<TabsItems> tabsdetails;

    public List<TabsItems> getTabsdetails() {
        return tabsdetails;
    }
}