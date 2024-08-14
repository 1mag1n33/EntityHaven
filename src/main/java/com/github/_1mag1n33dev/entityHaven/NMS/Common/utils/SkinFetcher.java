package com.github._1mag1n33dev.entityHaven.NMS.Common.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SkinFetcher {

    public static SkinData getSkinUrl(String username) throws Exception {
        String uuid = getPlayerUUID(username);
        if (uuid == null) return null;
        String profileJson = getProfileJson(uuid);
        return getSkinData(profileJson);
    }

    private static String getPlayerUUID(String username) throws Exception {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        if (response.length() == 0) {
            throw new RuntimeException("Empty response from Mojang API");
        }

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(response.toString());
        return jsonObject.has("id") ? jsonObject.get("id").getAsString() : null;
    }

    private static String getProfileJson(String uuid) throws Exception {
        URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        if (response.length() == 0) {
            throw new RuntimeException("Empty response from Mojang session server");
        }

        return response.toString();
    }

    public static SkinData getSkinData(String profileJson) {
        JsonParser parser = new JsonParser();
        try {
            JsonObject jsonObject = (JsonObject) parser.parse(profileJson);
            if (jsonObject.has("properties")) {
                JsonObject properties = jsonObject.getAsJsonArray("properties").get(0).getAsJsonObject();
                String value = properties.has("value") ? properties.get("value").getAsString() : null;
                String signature = properties.has("signature") ? properties.get("signature").getAsString() : null;

                return new SkinData(value, signature);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class SkinData {
        private final String value;
        private final String signature;

        public SkinData(String value, String signature) {
            this.value = value;
            this.signature = signature;
        }

        public String getValue() {
            return value;
        }

        public String getSignature() {
            return signature;
        }
    }


}

