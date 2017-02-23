package by.youdrive.commons.support;

import com.google.common.base.Preconditions;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class LinkBuilder {

    private UriBuilder uriBuilder;
    private String rel;
    private Object[] values = new Object[0];

    public LinkBuilder(UriInfo info) {
        this.uriBuilder = info.getBaseUriBuilder();
    }

    public LinkBuilder linkTo(Class<?> resource, Object ...values) {
        this.uriBuilder = uriBuilder.path(resource);
        this.values = values;
        return this;
    }

    public LinkBuilder linkTo(Class<?> resource, String method, Object ...values) {
        this.uriBuilder = uriBuilder.path(resource).path(resource, method);
        this.values = values;
        return this;
    }

    public LinkBuilder withParam(String name, Object ...values) {
        this.uriBuilder = uriBuilder.queryParam(name, values);
        return this;
    }

    public LinkBuilder withRel(String rel) {
        this.rel = rel;
        return this;
    }

    public ResourceLink build() {
        Preconditions.checkNotNull(rel, "rel should be set");
        ResourceLink link = new ResourceLink(uriBuilder.build(this.values).toString(), rel);

        this.uriBuilder = null;
        return link;
    }
}
