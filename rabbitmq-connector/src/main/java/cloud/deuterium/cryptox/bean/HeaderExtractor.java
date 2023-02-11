package cloud.deuterium.cryptox.bean;

import org.json.JSONException;
import org.json.JSONObject;

import static java.util.Objects.isNull;

/**
 * Created by Milan Stojkovic 18-Jan-2023
 */
public class HeaderExtractor {

    public String extract(String bodyString) {

        if (isNull(bodyString)) return "";

        try {
            JSONObject json = new JSONObject(bodyString);
            JSONObject data = json.getJSONObject("data");
            if (isNull(data)) return "";
            return data.keySet()
                    .stream()
                    .findFirst()
                    .orElse("");
        } catch (JSONException e) {
            return "";
        }
    }
}
