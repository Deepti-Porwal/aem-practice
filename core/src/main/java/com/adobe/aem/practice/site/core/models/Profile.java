package com.adobe.aem.practice.site.core.models;

import java.util.List;

public interface Profile {
    String getName();

    String getRole();

    String getGender();

    String getJoinDate();

    String getProfilePic();

    List<String> getSkills();
}
