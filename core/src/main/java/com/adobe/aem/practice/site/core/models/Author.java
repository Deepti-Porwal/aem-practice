package com.adobe.aem.practice.site.core.models;

import java.util.List;

public interface Author {
    String getFirstName();
    String getLastName();
    boolean getProfession();
    String getPageTitle();
    String getRequestAttribute();
    String getResourceName();
    String getLastModified();

    List<String> getBookDetails();
}
