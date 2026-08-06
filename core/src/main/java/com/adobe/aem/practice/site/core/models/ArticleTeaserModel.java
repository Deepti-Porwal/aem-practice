/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.practice.site.core.models;

import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ArticleTeaserModel {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleTeaserModel.class);

    private static final int WORDS_PER_MINUTE = 200;
    private static final DateTimeFormatter LAST_MODIFIED_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);

    @SlingObject
    private ResourceResolver resourceResolver;

    @ScriptVariable
    private Page currentPage;

    @ValueMapValue
    private String articlePath;

    @ValueMapValue
    private String overrideTitle;

    @ValueMapValue
    @Default(booleanValues = false)
    private boolean showReadingTime;

    private String title;
    private int readingTimeMinutes;
    private String readingTimeLabel;
    private String featuredImagePath;
    private String imageFormat;
    private Long imageWidth;
    private Long imageHeight;
    private String lastModifiedDate;
    private String articleUrl;

    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if (pageManager == null) {
            LOG.debug("PageManager unavailable");
            return;
        }

        Page articlePage = resolveArticlePage(pageManager);
        if (articlePage == null) {
            LOG.debug("No article page resolved from articlePath='{}' or current page", articlePath);
            return;
        }

        title = StringUtils.isNotBlank(overrideTitle) ? overrideTitle : articlePage.getTitle();
        articleUrl = articlePage.getPath() + ".html";

        Resource contentResource = articlePage.getContentResource();
        if (contentResource != null) {
            readingTimeMinutes = calculateReadingTimeMinutes(contentResource);
            if (showReadingTime && readingTimeMinutes > 0) {
                readingTimeLabel = readingTimeMinutes + " min read";
            }
            lastModifiedDate = formatLastModified(contentResource.getValueMap());
            resolveFeaturedImage(contentResource);
        }
    }

    private Page resolveArticlePage(PageManager pageManager) {
        if (StringUtils.isNotBlank(articlePath)) {
            return pageManager.getPage(articlePath);
        }
        return currentPage;
    }

    private int calculateReadingTimeMinutes(Resource contentResource) {
        int wordCount = countWords(contentResource);
        if (wordCount == 0) {
            return 0;
        }
        return Math.max(1, (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE));
    }

    private int countWords(Resource resource) {
        int total = 0;
        ValueMap properties = resource.getValueMap();
        for (Object value : properties.values()) {
            if (value instanceof String) {
                total += countWordsInText((String) value);
            }
        }
        for (Resource child : resource.getChildren()) {
            total += countWords(child);
        }
        return total;
    }

    private int countWordsInText(String text) {
        if (StringUtils.isBlank(text)) {
            return 0;
        }
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }
        return trimmed.split("\\s+").length;
    }

    private String formatLastModified(ValueMap contentProperties) {
        Calendar lastModified = contentProperties.get("jcr:lastModified", Calendar.class);
        if (lastModified == null) {
            lastModified = contentProperties.get("cq:lastModified", Calendar.class);
        }
        if (lastModified == null) {
            return null;
        }
        return LAST_MODIFIED_FORMAT.format(
                Instant.ofEpochMilli(lastModified.getTimeInMillis()).atZone(ZoneId.systemDefault()));
    }

    private void resolveFeaturedImage(Resource contentResource) {
        String imagePath = getFeaturedImagePath(contentResource);
        if (StringUtils.isBlank(imagePath)) {
            return;
        }

        featuredImagePath = imagePath;
        Resource assetResource = resourceResolver.getResource(imagePath);
        if (assetResource == null) {
            LOG.debug("Featured image resource not found at {}", imagePath);
            return;
        }

        Asset asset = assetResource.adaptTo(Asset.class);
        if (asset == null) {
            LOG.debug("Could not adapt resource to Asset at {}", imagePath);
            return;
        }

        imageFormat = asset.getMetadataValue("dc:format");
        imageWidth = parseLongMetadata(asset, "tiff:ImageWidth", "exif:ExifImageWidth");
        imageHeight = parseLongMetadata(asset, "tiff:ImageHeight", "exif:ExifImageLength", "exif:ExifImageHeight");
    }

    private String getFeaturedImagePath(Resource contentResource) {
        Resource featuredImage = contentResource.getChild("cq:featuredimage");
        if (featuredImage != null) {
            String fileReference = featuredImage.getValueMap().get("fileReference", String.class);
            if (StringUtils.isNotBlank(fileReference)) {
                return fileReference;
            }
        }
        return contentResource.getValueMap().get("fileReference", String.class);
    }

    private Long parseLongMetadata(Asset asset, String... keys) {
        for (String key : keys) {
            String value = asset.getMetadataValue(key);
            if (StringUtils.isNotBlank(value)) {
                try {
                    return Long.parseLong(value.trim());
                } catch (NumberFormatException ex) {
                    LOG.debug("Unable to parse metadata {}='{}' as long", key, value);
                }
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public boolean isShowReadingTime() {
        return showReadingTime;
    }

    public int getReadingTimeMinutes() {
        return readingTimeMinutes;
    }

    public String getReadingTimeLabel() {
        return readingTimeLabel;
    }

    public String getFeaturedImagePath() {
        return featuredImagePath;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public Long getImageWidth() {
        return imageWidth;
    }

    public Long getImageHeight() {
        return imageHeight;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public boolean hasContent() {
        return StringUtils.isNotBlank(title);
    }
}
