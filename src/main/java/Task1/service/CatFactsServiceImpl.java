package Task1.service;

import Task1.objects.CatFact;
import Task1.objects.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CatFactsServiceImpl implements CatFactsService {

    private List<CatFact> currentList = new ArrayList<>();
    private String url = "https://cat-fact.herokuapp.com/facts";

    public CatFactsServiceImpl() {}

    public CatFactsServiceImpl(List<CatFact> oldList) {
        this.currentList = oldList;
    }

    public CatFactsService loadCatFacts() {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
            HttpGet request = new HttpGet(url);

            CloseableHttpResponse response = httpClient.execute(request);
            prepareResponseToList(response);
            response.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return this;
    }

    public CatFactsService filterCatFacts() {
        if (currentList.size() > 0) {
            currentList = currentList.stream()
                    .filter(value -> value.getUpvotes() != null && value.getUpvotes().intValue() > 0)
                    .collect(Collectors.toList());
        }
        return this;
    }

    public void print() {
        if (currentList.size()>0)
            currentList.stream().forEach(System.out::println);
        System.out.println("Total: " + currentList.size());
    }

    private void prepareResponseToList(CloseableHttpResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            HashMap<String, ArrayList<CatFact>> hm = new Gson().fromJson(
                    sb.toString(),
                    new TypeToken<HashMap<
                            String,
                            ArrayList<
                                    CatFact<
                                        String, Integer,
                                        User<HashMap<String, String>, String>
                                    >
                            >
                    >>() {}.getType()
            );
            this.currentList = hm.get("all");
        }

    }
}
