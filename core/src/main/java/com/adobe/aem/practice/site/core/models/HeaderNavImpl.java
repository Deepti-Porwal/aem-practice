package com.adobe.aem.practice.site.core.models;

import com.adobe.aem.practice.site.core.services.GlobalSiteConfigService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Model(adaptables = SlingHttpServletRequest.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeaderNavImpl {

    @OSGiService
    private GlobalSiteConfigService globalSiteConfigService;
    @Self
    private SlingHttpServletRequest request;

    @Inject
    private ResourceResolver resourceResolver;

    @ValueMapValue
    private String navigationRoot;
    @ValueMapValue
    private int depth;

    private List<NavItems> items;

    private static final Logger LOG= LoggerFactory.getLogger(HeaderNavImpl.class);

    public List<NavItems> getItems() {
        return items;
    }

    private String siteName;
    private String logoPath;
    private String googleAnalyticsId;
    private boolean maintenanceMode;
    //private Map<String, String> socialLinks;
    private List<SocialLink> socialLinks;

    @PostConstruct
    protected void init() {
        LOG.debug("HeaderNavImpl INIT START");
        siteName          = globalSiteConfigService.getSiteName();
        logoPath          = globalSiteConfigService.getSiteLogoPath();
        googleAnalyticsId = globalSiteConfigService.getGoogleAnalyticsId();
        maintenanceMode   = globalSiteConfigService.isMaintenanceMode();
        socialLinks = globalSiteConfigService.getSocialLinkList();
        //socialLinks       = globalSiteConfigService.getSocialLinks();


        LOG.debug("HeaderNavigationModel init — site={}, logoPath={}, maintenanceMode={}",
                siteName, logoPath, maintenanceMode);
        items = new ArrayList<>();
        try {
            LOG.debug("Request object :: {}", request);
            if (request == null) {
                LOG.debug("Request is NULL");
                return;
            }
            ResourceResolver resourceResolver = request.getResourceResolver();
            LOG.debug("Resolver :: {}", resourceResolver);
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            LOG.debug("PageManager :: {}", pageManager);
            LOG.debug("navigationRoot :: {}", navigationRoot);
            if (navigationRoot == null) {
                LOG.debug("navigationRoot is NULL");
                return;
            }
            Page rootPage = pageManager.getPage(navigationRoot);
            LOG.debug("rootPage :: {}", rootPage);
            if (rootPage == null) {
                LOG.debug("rootPage is NULL");
                return;
            }
            Page currentPage = request.adaptTo(Page.class);
            LOG.debug("currentPage :: {}", currentPage);
            Iterator<Page> children = rootPage.listChildren();
            while (children.hasNext()) {
                Page child = children.next();
                LOG.debug("child page :: {}", child.getPath());
                if (child.isHideInNav()) {
                    LOG.debug("child hidden :: {}", child.getPath());
                    continue;
                }
                List<NavItems> subItems = new ArrayList<>();
                Iterator<Page> subPages = child.listChildren();
                while (subPages.hasNext()) {
                    Page sub = subPages.next();
                    LOG.debug("sub page :: {}", sub.getPath());
                    subItems.add(
                            new NavItems(
                                    sub.getTitle(),
                                    sub.getPath() + ".html",
                                    null,
                                    currentPage != null &&
                                            currentPage.getPath().equals(sub.getPath())
                            )
                    );
                }
                items.add(
                        new NavItems(
                                child.getTitle(),
                                child.getPath() + ".html",
                                subItems,
                                currentPage != null &&
                                        currentPage.getPath().startsWith(child.getPath())
                        )
                );
            }

            LOG.debug("HeaderNavImpl INIT END");

        } catch (Exception e) {
            LOG.debug("HEADER NAV CRASH", e);
        }
    }

    // ── Getters (expose to HTL via the interface) ─────────────────────────

    public String getSiteName() {
        return siteName;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public String getGoogleAnalyticsId() {
        return googleAnalyticsId;
    }

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }
    public List<SocialLink> getSocialLinks() { return socialLinks; }

}
