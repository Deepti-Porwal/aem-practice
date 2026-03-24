package com.adobe.aem.practice.site.core.models.impl;

import com.adobe.aem.practice.site.core.models.Profile;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;

@Model(
        adaptables = Resource.class,
        adapters = Profile.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ProfileImpl implements com.adobe.aem.practice.site.core.models.Profile {

private static final Logger LOG = LoggerFactory.getLogger(ProfileImpl.class);

    @ValueMapValue
    private String name;

    @ValueMapValue
    private String role;

    @ValueMapValue
    private String gender;

    @ValueMapValue
    private String joinDate;

    @ValueMapValue
    private String profilePic;

    @ValueMapValue
    private List<String> skills;

    @ChildResource (name = "prevExperiences")
    private List<Experience> experiences;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getJoinDate() {
        return joinDate;
    }

    @Override
    public String getProfilePic() {
        return profilePic;
    }

    @Override
    public List<String> getSkills() {
        return skills;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    @PostConstruct
    protected void init() {
    LOG.debug("image path:: {}",profilePic);
    LOG.debug("Child Resource:: {}",experiences.get(0));
    LOG.debug("Name:{}, Role :{}, Gender : {}, Join Date : {}, Skills :{}",name,role,gender,joinDate, skills.get(0));
    }
}
