package com.nw.socialscore.repl.application.domain.model;

import java.math.BigDecimal;
import java.net.URL;

// Note: Lombok would be nice here to blueprint the class more succinctly.
public class SocialScore {
    private final URL url;
    private final BigDecimal score;

    public SocialScore(URL url, BigDecimal score) {
        this.url = url;
        this.score = score;
    }

    public URL getUrl() {
        return url;
    }

    public BigDecimal getScore() {
        return score;
    }
}
