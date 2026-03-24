package com.adobe.aem.practice.site.core.models.impl;

import com.adobe.aem.practice.site.core.models.Author;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.*;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables= SlingHttpServletRequest.class,
        adapters = Author.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AuthorImpl implements Author {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;
    private String message;
    @ScriptVariable
    Page currentPage;
    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    @RequestAttribute(name="myAttribute")
    private String reqAttribute;

    @ResourcePath(path="/content/practice/us/en/components") @Via("resource")
    Resource myresource;

    @ValueMapValue
    @Named("jcr:lastModifiedBy")
    private String lastModifiedBy;

    @ValueMapValue
    @Default(values = "Deepti")
    private String fname;

    @ValueMapValue
    @Default(values = "Porwal")
    private String lname;

    @ValueMapValue
    @Default(values = "true")
    private boolean profession;

    @ValueMapValue
    private List<String> books;
    
    @Override
    public String getFirstName() {
        return fname;
    }

    @Override
    public String getLastName() {
        return lname;
    }

    @Override
    public boolean getProfession() {
        return profession;
    }

    public String getPageTitle(){
        return currentPage.getTitle();
    }

    @Override
    public String getRequestAttribute() {
        return reqAttribute;
    }

    @Override
    public String getResourceName() {
        return myresource.getName();
    }

    @Override
    public String getLastModified() {
        return lastModifiedBy;
    }

    @Override
    public List<String> getBookDetails() {
        return books;
    }

    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        String currentPagePath = Optional.ofNullable(pageManager)
                .map(pm -> pm.getContainingPage(currentResource))
                .map(Page::getPath).orElse("");

        message = "Hello World!\n"
                + "Resource type is: " + resourceType + "\n"
                + "Current page is:  " + currentPagePath + "\n";
    }

}
