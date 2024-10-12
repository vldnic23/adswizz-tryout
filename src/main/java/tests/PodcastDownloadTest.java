package tests;

import models.PodcastDownload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PodcastService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TextFileReader.getDownloadsFromFile;

public class PodcastDownloadTest {
    private static final String FILE_LOCATION = "src/main/resources/downloads.txt";
    private PodcastService podcastService;
    private String expectedMostPopularShow = "Who Trolled Amber";
    private String cityName = "San Francisco";
    private String expectedMostPopularDevice = "mobiles & tablets";
    private String expectedFormat = "preroll";

    @BeforeEach
    public void loadDownloadFile() {
        List<PodcastDownload> downloads = getDownloadsFromFile(FILE_LOCATION);
        podcastService = new PodcastService(downloads);
    }

    @Test
    public void testMostPopularShowInSanFrancisco() {
        String mostPopularShow = podcastService.displayMostListenedPodcastFrom(cityName);
        long downloadCount = podcastService.getNumberOfDownloadsForShowInCity(expectedMostPopularShow, cityName);

        assertEquals(expectedMostPopularShow, mostPopularShow);
        assertEquals(24, downloadCount);
    }

    @Test
    public void testMostPopularDevice() {
        String mostPopularDevice = podcastService.displayMostUsedDeviceAndDownloadsAmount();
        long downloadCount = podcastService.getNumberOfDownloadsForDevice(expectedMostPopularDevice);

        assertEquals(expectedMostPopularDevice, mostPopularDevice);
        assertEquals(60, downloadCount);
    }

    @Test
    public void testPrerollOpportunities() {
        List<Map.Entry<String, Long>> prerollResults = podcastService.displayOpportunitiesToInsertAds(expectedFormat);

        assertEquals(40, prerollResults.stream().filter(entry -> "Stuff You Should Know".equals(entry.getKey()))
                .findFirst()
                .get()
                .getValue());
        assertEquals(40, prerollResults.stream().filter(entry -> "Who Trolled Amber".equals(entry.getKey()))
                .findFirst()
                .get()
                .getValue());
        assertEquals(30, prerollResults.stream().filter(entry -> "Crime Junkie".equals(entry.getKey()))
                .findFirst()
                .get()
                .getValue());
        assertEquals(10, prerollResults.stream().filter(entry -> "The Joe Rogan Experience".equals(entry.getKey()))
                .findFirst()
                .get()
                .getValue());
    }
}
