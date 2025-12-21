package com.example.config;

public abstract class AIProviderConfigProp {

    private String provider;
    private String apiKey;
    private String model;

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProvider() {
        return provider;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getModel() {
        return model;
    }
}
