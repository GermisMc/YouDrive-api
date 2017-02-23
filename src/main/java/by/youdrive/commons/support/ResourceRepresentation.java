package by.youdrive.commons.support;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class ResourceRepresentation extends Representation {

    private List<ResourceLink> links;

    public void setLinks(List<ResourceLink> links) {
        this.links = links;
    }

    public List<ResourceLink> getLinks() {
        if (links == null) {
            this.links = new ArrayList<>();
        }

        return links;
    }

    public void addLink(ResourceLink link) {
        getLinks().add(link);
    }

    @JsonIgnore
    public ResourceLink getLink(String rel) {
        List<ResourceLink> links = getLinks();

        if (links == null) {
            return null;
        }

        for (ResourceLink link : links) {
            if (rel.equalsIgnoreCase(link.getRel())) {
                return link;
            }
        }
        return null;
    }

    @JsonIgnore
    public ResourceLink getSelfLink() {
        return getLink("self");
    }
}
