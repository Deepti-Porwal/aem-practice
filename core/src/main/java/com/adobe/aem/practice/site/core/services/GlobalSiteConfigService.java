package com.adobe.aem.practice.site.core.services;

import com.adobe.aem.practice.site.core.models.SocialLink;

import java.util.List;
import java.util.Map;

public interface GlobalSiteConfigService {

    /**
     * Display name of the site.
     * Used in navigation, HTML title suffix, and meta tags.
     */
    String getSiteName();

    /**
     * DAM path to the site logo, optionally prefixed with the CDN base URL.
     * Always returns a usable src value — never null.
     */
    String getSiteLogoPath();

    /**
     * Resolves an asset path by prepending the CDN base URL when configured.
     * Returns the path unchanged when CDN is not configured (local/dev).
     * Safe to call with null — returns empty string.
     */
    String resolveCdnUrl(String assetPath);

    /**
     * Google Analytics 4 Measurement ID.
     * Returns empty string when not configured — safe to use in HTL condition.
     */
    String getGoogleAnalyticsId();

    /**
     * Cookie consent endpoint URL.
     * Returns empty string when not configured.
     */
    String getCookieConsentEndpoint();

    /**
     * Parsed social links map: label → URL.
     * Returns an empty map when none are configured — never null.
     */
    Map<String, String> getSocialLinks();
    List<SocialLink> getSocialLinkList();

    /**
     * Whether the site is in maintenance mode.
     */
    boolean isMaintenanceMode();
}
