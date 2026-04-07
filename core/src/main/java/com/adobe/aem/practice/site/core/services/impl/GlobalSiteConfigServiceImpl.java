package com.adobe.aem.practice.site.core.services.impl;

import com.adobe.aem.practice.site.core.config.GlobalSiteConfig;
import com.adobe.aem.practice.site.core.models.SocialLink;
import com.adobe.aem.practice.site.core.services.GlobalSiteConfigService;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Component(service = GlobalSiteConfigService.class, immediate = true)
@Designate(ocd = GlobalSiteConfig.class)
public class GlobalSiteConfigServiceImpl implements GlobalSiteConfigService {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalSiteConfigServiceImpl.class);

    // Separator used in the socialLinks multi-value config entries
    private static final String SOCIAL_LINK_SEPARATOR = ":";

    // Index positions after splitting a social link entry
    private static final int SOCIAL_LABEL_INDEX = 0;
    private static final int SOCIAL_URL_INDEX   = 1;

    // ── Fields populated on activate/modify ──────────────────────────────

    private String siteName;
    private String siteLogoPath;
    private String cdnBaseUrl;
    private String googleAnalyticsId;
    private String cookieConsentEndpoint;
    private Map<String, String> socialLinks;
    private List<SocialLink> socialLinkList;
    private boolean maintenanceMode;

    @Activate
    @Modified
    protected void activate(GlobalSiteConfig config) {
        this.siteName              = config.siteName();
        this.siteLogoPath          = config.siteLogoPath();
        this.cdnBaseUrl            = StringUtils.trimToEmpty(config.cdnBaseUrl());
        this.googleAnalyticsId     = StringUtils.trimToEmpty(config.googleAnalyticsId());
        this.cookieConsentEndpoint = StringUtils.trimToEmpty(config.cookieConsentEndpoint());
        this.maintenanceMode       = config.maintenanceMode();
        this.socialLinks           = parseSocialLinks(config.socialLinks());
        this.socialLinkList  = buildSocialLinkList(this.socialLinks);

        LOG.info("GlobalSiteConfigService activated — site={}, cdnBaseUrl={}, maintenanceMode={}",
                siteName, cdnBaseUrl, maintenanceMode);
    }

    // ── Interface implementation ──────────────────────────────────────────

    @Override
    public String getSiteName() {
        return siteName;
    }

    @Override
    public String getSiteLogoPath() {
        // Logo path itself goes through CDN resolution
        return resolveCdnUrl(siteLogoPath);
    }

    @Override
    public String resolveCdnUrl(String assetPath) {
        if (StringUtils.isBlank(assetPath)) {
            return StringUtils.EMPTY;
        }
        // Only prepend CDN when configured — keeps local dev working without changes
        if (StringUtils.isNotBlank(cdnBaseUrl)) {
            return cdnBaseUrl + assetPath;
        }
        return assetPath;
    }

    @Override
    public String getGoogleAnalyticsId() {
        return googleAnalyticsId;
    }

    @Override
    public String getCookieConsentEndpoint() {
        return cookieConsentEndpoint;
    }

    @Override
    public Map<String, String> getSocialLinks() {
        return socialLinks;
    }

    @Override
    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    // ── Private helpers ───────────────────────────────────────────────────

    /**
     * Parses the raw String[] from OSGi config into an ordered label → URL map.
     *
     * Each entry is expected as "Twitter:https://twitter.com/example".
     * Entries that don't contain the separator or have a blank URL are skipped
     * with a debuging — this prevents silent failures from misconfiguration.
     */
    private Map<String, String> parseSocialLinks(String[] rawEntries) {
        if (rawEntries == null || rawEntries.length == 0) {
            return Collections.emptyMap();
        }

        Map<String, String> parsed = new LinkedHashMap<>();

        for (String entry : rawEntries) {
            // Only split on the FIRST colon — URLs contain colons too (https://)
            int separatorIndex = entry.indexOf(SOCIAL_LINK_SEPARATOR);

            if (separatorIndex < 1) {
                LOG.debug("GlobalSiteConfig: skipping malformed social link entry '{}' " +
                        "— expected format is label:url", entry);
                continue;
            }

            String label = entry.substring(0, separatorIndex).trim();
            String url   = entry.substring(separatorIndex + 1).trim();

            if (StringUtils.isBlank(url)) {
                LOG.debug("GlobalSiteConfig: skipping social link '{}' — URL is blank", label);
                continue;
            }

            parsed.put(label, url);
        }

        return Collections.unmodifiableMap(parsed);
    }

    private List<SocialLink> buildSocialLinkList(Map<String, String> links) {
        LOG.debug("Inside buildSocialLinkList");
        if (links == null || links.isEmpty()) {
            LOG.debug("links is null or empty::{}",links);
            return Collections.emptyList();
        }
        List<SocialLink> list = new ArrayList<>();
        links.forEach((label, url) -> list.add(new SocialLink(label, url)));
        return Collections.unmodifiableList(list);
    }
    @Override
    public List<SocialLink> getSocialLinkList() {
        return socialLinkList;
    }
}
