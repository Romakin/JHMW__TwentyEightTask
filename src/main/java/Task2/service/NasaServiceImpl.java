package Task2.service;

import Task2.objects.NasaDataObject;
import Task2.objects.PlanetOfTheDay;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.Scanner;

public class NasaServiceImpl implements NasaService {

    NasaDataObject data = null;
    private String url = "https://api.nasa.gov/planetary/apod?api_key=";
    private String key = null;

    @Override
    public NasaService loadData() {
        try {
            if (key == null) askForKey();
            CloseableHttpClient httpClient = HttpClientBuilder.create()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                            .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                            .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                            .build())
                    .build();
            HttpGet request = new HttpGet(url + key);

            CloseableHttpResponse response = httpClient.execute(request);
            prepareResponseToData(response);
            response.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return this;
    }

    @Override
    public NasaService saveData() {
        if (data != null)
            saveToFile("./PlanetOfTheDay/" + System.currentTimeMillis() + ".html", data.toHTML());
        else System.out.println("No data getted");
        return this;
    }

    private void prepareResponseToData(CloseableHttpResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            PlanetOfTheDay potd = new Gson().fromJson(
                    sb.toString(),
                    new TypeToken<PlanetOfTheDay>() {}.getType()
            );
            this.data = potd;
        }

    }

    public void saveToFile(String path, String content) {
        File lf = new File(path);
        if (!(lf.exists() && lf.canRead() && lf.canWrite())) {
            try {
                new File(lf.getParent()).mkdirs();
                if (lf.createNewFile()) {
                    System.out.println("File created");
                } else {
                    return;
                }
            } catch (IOException e) {
                System.out.println("saveToFile: " + e.getMessage());
                return;
            }
        }
        try(FileOutputStream fos = new FileOutputStream(lf)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            System.out.println("saveToFile: " + e.getMessage());
        }
    }

    private void askForKey() throws InvalidArgumentException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please, enter your personal key for access:");
        key = sc.nextLine();
        if (key.length()==0) throw new InvalidArgumentException(new String[]{"Wrong presonal key"});
    }
}
