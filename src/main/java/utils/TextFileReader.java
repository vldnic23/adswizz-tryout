package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.PodcastDownload;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFileReader {

    public static List<PodcastDownload> getDownloadsFromFile(String filePath) {
        List<PodcastDownload> downloads = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    PodcastDownload download = objectMapper.readValue(line, PodcastDownload.class);
                    downloads.add(download);
                } catch (IOException e) {
                    System.err.println("Exception: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Exception: " + e.getMessage());
        }

        return downloads;
    }
}

