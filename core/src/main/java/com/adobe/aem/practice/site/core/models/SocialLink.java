package com.adobe.aem.practice.site.core.models;

public class SocialLink {
    private final String label;
    private final String url;

    public SocialLink(String label, String url) {
        this.label = label;
        this.url   = url;
    }

    public String getLabel() { return label; }
    public String getUrl()   { return url; }
}
