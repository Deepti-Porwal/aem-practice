package com.adobe.aem.practice.site.core.models;

public interface AuthorProfile {
    public String getAuthorName();

    public String getAuthorDescription();

    public String getFileReference();

    public String getCtaText();

    public String getCtaLink();

    public boolean isOpenInNewTab();
}
