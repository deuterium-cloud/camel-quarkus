package cloud.deuterium.cryptox;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Milan Stojkovic 11-Feb-2023
 */

@ApplicationScoped
public class QuoteService {

    private static final Logger LOG = LoggerFactory.getLogger(QuoteService.class);
    private static final String ASK_PATTERN = "market-ask-%s";
    private static final String BID_PATTERN = "market-bid-%s";

    private final ValueCommands<String, String> commands;

    public QuoteService(RedisDataSource ds) {
        this.commands = ds.value(String.class);
    }

    public String getQuote( String pair, String side){

        String pairName = "bid".equals(side) ? BID_PATTERN.formatted(pair) : ASK_PATTERN.formatted(pair);
        String result = commands.get(pairName);
        LOG.info("Query redis for {} and got the result: {}", pairName, result);
        return result;
    }
}
