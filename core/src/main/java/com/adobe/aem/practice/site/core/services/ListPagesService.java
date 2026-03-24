package com.adobe.aem.practice.site.core.services;

import com.day.cq.wcm.api.Page;

import java.util.Iterator;

public interface ListPagesService {
    public Iterator<Page> getPages();
}
