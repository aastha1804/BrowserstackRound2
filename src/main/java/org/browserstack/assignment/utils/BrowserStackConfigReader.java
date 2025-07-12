package org.browserstack.assignment.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BrowserStackConfigReader {
    public static JsonNode readConfig() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(new File("browserstack-config.json"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load browserstack-config.json", e);
        }
    }
    public static List<String> getAllEnvironmentNames() throws IOException {
        JsonNode environments = readConfig().get("environments");
        List<String> envList = new ArrayList<>();
        int index = 0;
        for (JsonNode env : environments) {
            if (env.has("browser")) {
                envList.add("browser_" + index);
            } else if (env.has("device")) {
                envList.add(env.get("device").asText());
            }
            index++;
        }
        return envList;
    }
}
