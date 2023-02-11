package cloud.deuterium.cryptox.redis;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import org.apache.camel.Body;
import org.apache.camel.Header;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Milan Stojkovic 18-Jan-2023
 */

@ApplicationScoped
public class RedisService {

    private final Redis redis;

    public RedisService(Redis redis) {
        this.redis = redis;
    }

    public String setQuote(@Header("x-market") String header, @Body String body) {
        if (!header.isBlank()) {
            redis.send(Request.cmd(Command.SET).arg(header).arg(body));
        }
        return body;
    }
}
