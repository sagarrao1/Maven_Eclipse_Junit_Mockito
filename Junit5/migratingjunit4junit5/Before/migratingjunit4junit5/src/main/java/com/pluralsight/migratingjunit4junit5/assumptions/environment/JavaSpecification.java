package com.pluralsight.migratingjunit4junit5.assumptions.environment;

public class JavaSpecification {
    private String version;

    public JavaSpecification(String version) {
        this.version = version;
    }

    String getVersion() {
        return version;
    }
}
