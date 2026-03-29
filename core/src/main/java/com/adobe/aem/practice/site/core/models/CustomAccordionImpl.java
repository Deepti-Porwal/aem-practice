package com.adobe.aem.practice.site.core.models;

import com.adobe.aem.practice.site.core.models.impl.AccordionItems;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = "practice/components/content/customAccordion")
public class CustomAccordionImpl {

    @ChildResource(name = "accordionItems")
    private List<AccordionItems> itemsList;

    public List<AccordionItems> getAccordionItems() {
        return itemsList;
    }
}
