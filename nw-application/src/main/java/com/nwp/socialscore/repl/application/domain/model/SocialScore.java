package com.nwp.socialscore.repl.application.domain.model;

import java.math.BigDecimal;
import java.net.URL;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SocialScore {
    private final URL url;
    private final BigDecimal score;
}
