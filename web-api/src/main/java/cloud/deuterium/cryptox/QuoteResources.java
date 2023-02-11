package cloud.deuterium.cryptox;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static java.util.Objects.isNull;

/**
 * Created by Milan Stojkovic 11-Feb-2023
 */

@Path("/quote")
public class QuoteResources {

    @Inject
    QuoteService service;

    @GET
    @Path("/{pair}")
    @Produces(MediaType.APPLICATION_JSON)
    public String quote(@PathParam("pair") String pair, @DefaultValue("bid") @QueryParam("side") String side) {

        String quote = service.getQuote(pair, side);

        if(isNull(quote) || quote.isBlank()) {
            quote = "Invalid pair or side!";
        }

        return """
                {
                    "pair": %s,
                    "side": %s,
                    "quote": %s,
                    "timestamp": %d
                }
                """.formatted(pair, side, quote, System.currentTimeMillis());
    }
}
