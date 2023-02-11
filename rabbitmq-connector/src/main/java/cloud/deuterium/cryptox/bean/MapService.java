package cloud.deuterium.cryptox.bean;

import cloud.deuterium.cryptox.models.Pair;
import io.quarkus.runtime.Startup;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Created by Milan Stojkovic 22-Jan-2023
 */
@Startup
@ApplicationScoped
public class MapService {

    public Map<String, Pair> markets = new ConcurrentHashMap<>();

    public MapService() {
        addMarkets();
    }

    private void addMarkets() {

        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("all.csv")){
            Stream<String> rows = new BufferedReader(new InputStreamReader(is)).lines();
            rows.filter(row -> !row.isEmpty())
                    .map(this::createEntry)
                    .forEach(entry -> markets.put(entry.getKey(), entry.getValue()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map.Entry<String, Pair> createEntry(String row) {
        String[] parts = row.split(",");
        return Map.entry(parts[0], new Pair(parts[1], parts[2]));
    }

}
