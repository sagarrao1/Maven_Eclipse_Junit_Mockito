package com.wiredbraincoffee.reward.extensiontests;

import org.junit.jupiter.api.extension.ExtensionContext;

public class ExtensionUtils {

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(
                    "A", "custom", "namespace", "for", "my", "extension");

    public static final String EXCEPTION_KEY = "EXCEPTION";

    public static ExtensionContext getEngineContext(ExtensionContext contextParam) {
        return contextParam.getRoot();
    }
}
