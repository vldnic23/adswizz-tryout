package service;

import models.Opportunity;
import models.PodcastDownload;

import java.util.*;
import java.util.stream.Collectors;

public class PodcastService {

    private final List<PodcastDownload> podcasts;

    public PodcastService(List<PodcastDownload> podcasts) {
        this.podcasts = podcasts;
    }

    public String displayMostListenedPodcastFrom(String cityName) {
        Map<String, Long> showDownload = podcasts.stream()
                .filter(download -> cityName.equalsIgnoreCase(download.getCity()))
                .collect(Collectors.groupingBy(download -> download.getDownloadIdentifier().getShowId(), Collectors.counting()));

        Map.Entry<String, Long> mostPopularShow = showDownload.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new NoSuchElementException("No downloads found for the given city"));

        System.out.println("Most popular show is: " + mostPopularShow.getKey());
        System.out.println("Number of downloads is: " + mostPopularShow.getValue());
        return mostPopularShow.getKey();
    }

    public long getNumberOfDownloadsForShowInCity(String showId, String cityName) {
        return podcasts.stream()
                .filter(podcast -> showId.equals(podcast.getDownloadIdentifier().getShowId()) &&
                        cityName.equalsIgnoreCase(podcast.getCity()))
                .count();
    }

    public String displayMostUsedDeviceAndDownloadsAmount() {
        Map<String, Long> deviceDownload = podcasts.stream()
                .collect(Collectors.groupingBy(PodcastDownload::getDeviceType, Collectors.counting()));

        Map.Entry<String, Long> mostPopularDevice = deviceDownload.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new NoSuchElementException("No downloads found for any device"));

        System.out.println("Most popular device is: " + mostPopularDevice.getKey());
        System.out.println("Number of downloads is: " + mostPopularDevice.getValue());

        return mostPopularDevice.getKey();
    }

    public long getNumberOfDownloadsForDevice(String deviceType) {
        return podcasts.stream()
                .filter(podcast -> deviceType.equals(podcast.getDeviceType()))
                .count();
    }

    public List<Map.Entry<String, Long>> displayOpportunitiesToInsertAds(String format) {
        Map<String, Long> opportunities = podcasts.stream()
                .flatMap(podcast -> podcast.getOpportunities().stream()
                        .filter(opportunity -> opportunity.getPositionUrlSegments().containsKey("aw_0_ais.adBreakIndex")
                                && opportunity.getPositionUrlSegments().get("aw_0_ais.adBreakIndex").contains(format))
                        .map(opportunity -> Map.entry(podcast.getDownloadIdentifier().getShowId(), opportunity)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting()));

        List<Map.Entry<String, Long>> sortedOpportunities = opportunities.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        sortedOpportunities.forEach(entry ->
                System.out.println("Show Id: " + entry.getKey() + ", " + format + " Opportunity Number: " + entry.getValue()));

        return sortedOpportunities;
    }
}
