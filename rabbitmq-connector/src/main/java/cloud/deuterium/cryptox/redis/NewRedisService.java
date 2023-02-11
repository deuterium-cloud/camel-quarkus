package cloud.deuterium.cryptox.redis;

import cloud.deuterium.cryptox.models.Market;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;

import javax.enterprise.context.ApplicationScoped;

import static java.util.Objects.nonNull;

/**
 * Created by Milan Stojkovic 18-Jan-2023
 */

@ApplicationScoped
public class NewRedisService {

    private static final String ASK_PATTERN = "market-ask-%s";
    private static final String BID_PATTERN = "market-bid-%s";

    private final ValueCommands<String, String> commands;

    public NewRedisService(RedisDataSource ds) {
        this.commands = ds.value(String.class);
    }

    public String setQuote(Market market) {

        if (nonNull(market.askPrice())) {
            commands.set(ASK_PATTERN.formatted(market.ask()), market.askPrice());
        }

        if (nonNull(market.bidPrice())) {
            commands.set(BID_PATTERN.formatted(market.bid()), market.bidPrice());
        }

        return market.body();
    }
}
