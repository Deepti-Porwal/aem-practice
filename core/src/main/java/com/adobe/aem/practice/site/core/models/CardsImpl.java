package com.adobe.aem.practice.site.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardsImpl {

    @ChildResource(name = "cards")
    private List<CardDetails> cards;

    public List<CardDetails> getCards() {
        return cards;
    }
}
