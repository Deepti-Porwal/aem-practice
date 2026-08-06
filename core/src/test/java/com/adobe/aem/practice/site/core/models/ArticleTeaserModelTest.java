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

import com.adobe.aem.practice.site.core.testcontext.AppAemContext;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
class ArticleTeaserModelTest {

    private static final String ARTICLE_PAGE = "/content/practice/article";
    private static final String COMPONENT_PATH = "/content/practice/page/jcr:content/article_teaser";

    private final AemContext context = AppAemContext.newAemContext();

    private Page hostPage;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(ArticleTeaserModel.class);
        hostPage = context.create().page("/content/practice/page");

        Calendar lastModified = new GregorianCalendar(2026, Calendar.AUGUST, 4);
        context.create().page(ARTICLE_PAGE, "/conf/practice/settings/wcm/templates/page",
                "jcr:title", "Sample Article Title",
                "jcr:lastModified", lastModified,
                "articleBody", "word one two three four five six seven eight nine ten "
                        + "eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen twenty "
                        + "twentyone twentytwo twentythree twentyfour twentyfive twentysix twentyseven twentyeight "
                        + "twentynine thirty thirtyone thirtytwo thirtythree thirtyfour thirtyfive thirtysix "
                        + "thirtyseven thirtyeight thirtynine forty fortyone fortytwo fortythree fortyfour "
                        + "fortyfive fortysix fortyseven fortyeight fortynine fifty");
        context.create().resource(ARTICLE_PAGE + "/jcr:content/cq:featuredimage",
                "fileReference", "/content/dam/practice/article-image.jpg");

        context.create().resource("/content/dam/practice/article-image.jpg",
                "jcr:primaryType", "dam:Asset");
        context.create().resource("/content/dam/practice/article-image.jpg/jcr:content",
                "jcr:primaryType", "dam:AssetContent");
        context.create().resource("/content/dam/practice/article-image.jpg/jcr:content/metadata",
                "jcr:primaryType", "nt:unstructured",
                "dc:format", "image/jpeg",
                "tiff:ImageWidth", 940L,
                "tiff:ImageHeight", 51L);
    }

    @Test
    void usesCurrentPageWhenArticlePathIsEmpty() {
        context.currentPage(hostPage);
        context.create().resource(hostPage.getContentResource(), "article_teaser",
                "sling:resourceType", "practice/components/content/article-teaser",
                "showReadingTime", true);

        ArticleTeaserModel model = context.request().adaptTo(ArticleTeaserModel.class);

        assertNotNull(model);
        assertTrue(model.hasContent());
        assertEquals("page", model.getTitle());
    }

    @Test
    void resolvesReferencedArticlePage() {
        context.create().resource(hostPage.getContentResource(), "article_teaser",
                "sling:resourceType", "practice/components/content/article-teaser",
                "articlePath", ARTICLE_PAGE,
                "showReadingTime", true);

        ArticleTeaserModel model = context.resourceResolver()
                .getResource(COMPONENT_PATH)
                .adaptTo(ArticleTeaserModel.class);

        assertNotNull(model);
        assertTrue(model.hasContent());
        assertEquals("Sample Article Title", model.getTitle());
        assertEquals(ARTICLE_PAGE + ".html", model.getArticleUrl());
    }

    @Test
    void usesOverrideTitleWhenProvided() {
        context.create().resource(hostPage.getContentResource(), "article_teaser",
                "sling:resourceType", "practice/components/content/article-teaser",
                "articlePath", ARTICLE_PAGE,
                "overrideTitle", "Custom Teaser Title");

        ArticleTeaserModel model = context.resourceResolver()
                .getResource(COMPONENT_PATH)
                .adaptTo(ArticleTeaserModel.class);

        assertNotNull(model);
        assertEquals("Custom Teaser Title", model.getTitle());
    }

    @Test
    void calculatesReadingTimeAndFormatsMetadata() {
        context.create().resource(hostPage.getContentResource(), "article_teaser",
                "sling:resourceType", "practice/components/content/article-teaser",
                "articlePath", ARTICLE_PAGE,
                "showReadingTime", true);

        ArticleTeaserModel model = context.resourceResolver()
                .getResource(COMPONENT_PATH)
                .adaptTo(ArticleTeaserModel.class);

        assertNotNull(model);
        assertTrue(model.isShowReadingTime());
        assertEquals(1, model.getReadingTimeMinutes());
        assertEquals("1 min read", model.getReadingTimeLabel());
        assertEquals("/content/dam/practice/article-image.jpg", model.getFeaturedImagePath());
        assertEquals("image/jpeg", model.getImageFormat());
        assertEquals(940L, model.getImageWidth());
        assertEquals(51L, model.getImageHeight());
        assertNotNull(model.getLastModifiedDate());
    }

    @Test
    void hidesReadingTimeWhenDisabled() {
        context.create().resource(hostPage.getContentResource(), "article_teaser",
                "sling:resourceType", "practice/components/content/article-teaser",
                "articlePath", ARTICLE_PAGE,
                "showReadingTime", false);

        ArticleTeaserModel model = context.resourceResolver()
                .getResource(COMPONENT_PATH)
                .adaptTo(ArticleTeaserModel.class);

        assertNotNull(model);
        assertFalse(model.isShowReadingTime());
        assertEquals(1, model.getReadingTimeMinutes());
        assertEquals(null, model.getReadingTimeLabel());
    }
}
