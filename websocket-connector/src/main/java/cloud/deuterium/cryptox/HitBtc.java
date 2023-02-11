package cloud.deuterium.cryptox;

import org.apache.camel.builder.RouteBuilder;
import javax.enterprise.context.ApplicationScoped;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.rabbitmq;

@ApplicationScoped
public class HitBtc extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        String body = """
                {
                    "method": "subscribe",
                    "ch": "orderbook/top/1000ms",
                    "params": {
                        "symbols": ["*"]
                    },
                    "id": 123
                }
                """;

        // For initiation of subscription -> use custom client bean
        from("timer:test-timer?repeatCount=1")
                .log("${body}")
                .setBody(simple(body))
                .to("ahc-wss://{{app.websocket.url}}?client=#asyncHttpClient")
                .log("${body}");


        // For listening the incoming messages -> use custom client bean
        from("ahc-wss://{{app.websocket.url}}?client=#asyncHttpClient")
                .log("${body}")
                .to(rabbitmq("{{app.rabbit.exchange}}")
                        .queue("{{app.rabbit.queue}}")
                        .routingKey("{{app.rabbit.routing-key}}")
                        .username("{{app.rabbit.username}}")
                        .password("{{app.rabbit.password}}")
                        .hostname("{{app.rabbit.hostname}}")
                        .portNumber("{{app.rabbit.port-number}}")
                        .autoDelete(false));
    }
}
