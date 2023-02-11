package cloud.deuterium.cryptox.bean;

import cloud.deuterium.cryptox.models.Market;
import cloud.deuterium.cryptox.models.Pair;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Milan Stojkovic 21-Jan-2023
 */

@ApplicationScoped
public class BodyTransformer {

    private final MapService mapService;

    public BodyTransformer(MapService mapService) {
        this.mapService = mapService;
    }

    public Market transform(@Header("x-market") String header, @Body String bodyString) {

        JSONObject json = new JSONObject(bodyString);
        JSONObject data = json.getJSONObject("data");
        JSONObject odds = data.getJSONObject(header);

        String askPrice = odds.has("a") ? odds.getString("a") : null;
        String bidPrice = odds.has("b") ? odds.getString("b") : null;

        Pair pair = mapService.markets.get(header);

        return new Market(bodyString, pair.ask(), askPrice, pair.bid(), bidPrice);
    }
}
