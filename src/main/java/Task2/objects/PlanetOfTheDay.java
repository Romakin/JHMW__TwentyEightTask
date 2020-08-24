package Task2.objects;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class PlanetOfTheDay extends NasaDataObject {

    String copyright;
    String explanation;
    String hdurl;
    String date;
    String media_type;
    String service_version;
    String title;
    String url;

    public PlanetOfTheDay(String copyright, String explanation, String hdurl, String date, String media_type,
                          String service_version, String title, String url) {
        this.copyright = copyright;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.date = date;
        this.media_type = media_type;
        this.service_version = service_version;
        this.title = title;
        this.url = url;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getDate() {
        return date;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getService_version() {
        return service_version;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String toHTML() {
        String cpRight = getCopyright();
        return String.format("<!DOCTYPE html><html>" +
                "<head>" +
                "<script type=\"text/javascript\" src=\"https://fomantic-ui.com/dist/semantic.js\"></script>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"https://fomantic-ui.com/dist/semantic.css\">" +
                "</head>" +
                "<body><div class=\"ui container\">\n" +
                "<div class=\"ui segment\">\n" +
                "<h2 class=\"ui dividing header\">%s</h2>\n" +
                "<img class=\"ui rounded image\" src=\"%s\">\n" +
                "<div class=\"ui horizontal statistic\">\n" +
                "  <div class=\"value\">%s</div>\n" +
                "  <div class=\"label\">%s</div>\n" +
                "</div>\n" +
                "<p>%s</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body></html>",
                getTitle(),
                encodeFileToBase64Binary(getHdurl()),
                cpRight != null ? cpRight : "NASA",
                getDate(),
                getExplanation());
    }

    private static String encodeFileToBase64Binary(String urlStr){
        String encodedfile = null;
        String fileName = "./PlanetOfTheDay/eftb" + + System.currentTimeMillis() + ".jpg";
        try (BufferedInputStream in = new BufferedInputStream(new URL(urlStr).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            byte[] fileContent = Files.readAllBytes(new File(fileName).toPath());
            encodedfile = new String(Base64.encodeBase64(fileContent), "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "data:image/png;base64, " + encodedfile;
    }
}