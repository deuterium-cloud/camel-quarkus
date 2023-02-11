package cloud.deuterium.cryptox;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * Created by Milan Stojkovic 21-Jun-2022
 */

@ApplicationScoped
public class ClientBean {

    @Named("asyncHttpClient")
    @Produces
    public AsyncHttpClient asyncHttpClient() {
        return new DefaultAsyncHttpClient(new DefaultAsyncHttpClientConfig.Builder()
                .setMaxRequestRetry(0)
                .setWebSocketMaxBufferSize(1024000)
                .setWebSocketMaxFrameSize(1024000)
                .build());
    }
}
