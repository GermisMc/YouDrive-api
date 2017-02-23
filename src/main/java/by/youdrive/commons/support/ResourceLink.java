package by.youdrive.commons.support;

import java.net.URI;
import java.net.URISyntaxException;

public class ResourceLink extends Representation {

    private String href;
    private String rel;

    public ResourceLink() {
    }

    public ResourceLink(String href, String rel) {
        this.href = href;
        this.rel = rel;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {

        return href;
    }

    public String getRel() {
        return rel;
    }

    public URI toURI() {
        try {
            return new URI(href);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
