package com.nw.socialscore.repl.application.service;

import com.nw.socialscore.repl.application.domain.model.SocialScore;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SocialScoreOperationService {

    private static final String INVALID_URL_ERROR_MESSAGE = "Invalid URL passed when performing ADD/REMOVE operation, " +
                                                            "please verify the protocol is present in your url.";
    private static final String INVALID_NUMERIC_ADD_VALUE_ERROR_MESSAGE = "The value passed is not a valid numeric type [integer/decimal].";
    private static final String ITEM_NOT_FOUND_FOR_REMOVAL_ERROR = "URL cannot be removed from store as it does not exist - please try adding one first.";

    private static final Map<String, List<SocialScore>> domainToScoreMap = new HashMap<>();

    public void addUrlWithSocialScore(String inputUrl, String inputScore) {
        URL url = createUrlFromArgument(inputUrl);
        BigDecimal score = createScoreFromArgument(inputScore);
        String domainName = getDomainNameFromUrl(url);
        List<SocialScore> scores = domainToScoreMap.computeIfAbsent(domainName, (domain -> new ArrayList<>()));
        scores.add(new SocialScore(url, score));
    }

    public void removeUrlFromSocialScoreContainer(String inputUrl) {
        URL url = createUrlFromArgument(inputUrl);
        String domainName = getDomainNameFromUrl(url);
        if (!domainToScoreMap.containsKey(domainName)) {
            throw new IllegalStateException(ITEM_NOT_FOUND_FOR_REMOVAL_ERROR);
        } else {
            List<SocialScore> updatedSocialScores = domainToScoreMap.get(domainName).stream()
                    .filter(socialScore -> !socialScore.getUrl().equals(url))
                    .collect(Collectors.toList());
            if (updatedSocialScores.isEmpty()) {
                domainToScoreMap.remove(domainName);
            } else {
                domainToScoreMap.put(domainName, updatedSocialScores);
            }
        }
    }

    public void exportUrlSocialScores() {
        System.out.println("domain;urls;social_score");
        domainToScoreMap.forEach((domainName, urlScoreList) -> {
            BigDecimal socialScoreForDomain = urlScoreList.stream()
                    .map(SocialScore::getScore)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            String domainResult = String.format("%s;%d;%.2f", domainName, urlScoreList.size(), socialScoreForDomain.setScale(2, RoundingMode.HALF_EVEN));
            System.out.println(domainResult);
        });
    }

    private BigDecimal createScoreFromArgument(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(INVALID_NUMERIC_ADD_VALUE_ERROR_MESSAGE, e);
        }
    }

    private URL createUrlFromArgument(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(INVALID_URL_ERROR_MESSAGE, e);
        }
    }

    private String getDomainNameFromUrl(URL url) {
        return url.getHost().startsWith("www.") ? url.getHost().substring(4) : url.getHost();
    }

}
