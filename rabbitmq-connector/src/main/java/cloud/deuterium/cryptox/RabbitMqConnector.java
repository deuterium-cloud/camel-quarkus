package cloud.deuterium.cryptox;

import cloud.deuterium.cryptox.bean.BodyTransformer;
import cloud.deuterium.cryptox.bean.HeaderExtractor;
import cloud.deuterium.cryptox.bean.MarketFilter;
import cloud.deuterium.cryptox.redis.NewRedisService;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.rabbitmq;

/**
 * Created by Milan Stojkovic 18-Jan-2023
 */

@ApplicationScoped
public class RabbitMqConnector extends RouteBuilder {

    private final NewRedisService redisService;
    private final MarketFilter marketFilter;
    private final BodyTransformer bodyTransformer;

    public RabbitMqConnector(NewRedisService redisService, MarketFilter marketFilter, BodyTransformer bodyTransformer) {
        this.redisService = redisService;
        this.marketFilter = marketFilter;
        this.bodyTransformer = bodyTransformer;
    }

    @Override
    public void configure() throws Exception {

        from(rabbitmq("{{app.rabbit.exchange}}")
                .queue("{{app.rabbit.queue}}")
                .routingKey("{{app.rabbit.routing-key}}")
                .username("{{app.rabbit.username}}")
                .password("{{app.rabbit.password}}")
                .hostname("{{app.rabbit.hostname}}")
                .portNumber("{{app.rabbit.port-number}}")
                .autoDelete(false))
                .to("direct:extract-market");

        from("direct:extract-market")
                .setHeader("x-market").method(new HeaderExtractor())
                .filter().method(marketFilter)
                .to("direct:process-message");

        from("direct:process-message")
                .bean(bodyTransformer)
                .bean(redisService)
                .log("${body}");
    }
}
