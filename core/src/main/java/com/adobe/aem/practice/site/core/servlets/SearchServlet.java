package com.adobe.aem.practice.site.core.servlets;

import com.adobe.aem.practice.site.core.models.HeaderNavImpl;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/practice/search",
                "sling.servlet.methods=GET"
        })
public class SearchServlet extends SlingSafeMethodsServlet {

    @Reference
    private QueryBuilder queryBuilder;

    private static final Logger LOG= LoggerFactory.getLogger(SearchServlet.class);
    @Override
    protected void doGet(
            SlingHttpServletRequest request,
            SlingHttpServletResponse response)
            throws IOException {
        LOG.debug("Inside Search Servlet");

        String keyword = request.getParameter("keyword");
        String root = request.getParameter("root");
        String limit = request.getParameter("limit");
        String sort = request.getParameter("sort");
        String offset = request.getParameter("offset");

        ResourceResolver resolver = request.getResourceResolver();
        Session session = resolver.adaptTo(Session.class);

        Map<String,String> map = new HashMap<>();

// FEATURE — search root
        map.put("path", root);

// FEATURE — page search
        map.put("type","cq:Page");

// FEATURE — fulltext search
        map.put("fulltext", keyword);

// FEATURE — pagination
        map.put("p.limit", limit);
        map.put("p.offset", offset);

// FEATURE — sorting
        if("modified".equals(sort)){
            map.put("orderby","@jcr:content/cq:lastModified");
            map.put("orderby.sort","desc");
        }

// FEATURE — highlight
        map.put("p.hits","full");
        map.put("p.highlight","true");

        Query query = queryBuilder.createQuery(
                PredicateGroup.create(map),
                session);

        SearchResult result = query.getResult();

        JSONArray array = new JSONArray();

        for(Hit hit : result.getHits()){

            JSONObject obj = new JSONObject();

            try {
                obj.put("title", hit.getTitle());
                obj.put("path", hit.getPath());
                obj.put("description",
                        hit.getExcerpt());

                LOG.debug("Map items:: Title : {}, Path: {}, Description:{}",hit.getTitle(),hit.getPath(),hit.getExcerpt());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }


            array.put(obj);
        }

        response.setContentType("application/json");
        response.getWriter().write(array.toString());
    }
}