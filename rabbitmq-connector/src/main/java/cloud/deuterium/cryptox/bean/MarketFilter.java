package cloud.deuterium.cryptox.bean;

import cloud.deuterium.cryptox.models.Pair;
import org.apache.camel.Header;

import javax.enterprise.context.ApplicationScoped;

import static java.util.Objects.nonNull;

/**
 * Created by Milan Stojkovic 21-Jan-2023
 */

@ApplicationScoped
public class MarketFilter {

    private final MapService mapService;

    public MarketFilter(MapService mapService) {
        this.mapService = mapService;
    }

    public boolean isMarketExists(@Header("x-market") String header ) {
        Pair pair = mapService.markets.get(header);
        return nonNull(pair);
    }
}
