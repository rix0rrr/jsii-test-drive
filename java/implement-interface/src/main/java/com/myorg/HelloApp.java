package com.myorg;

import software.amazon.awscdk.core.App;

public class HelloApp {
    public static void main(final String[] argv) {
        App app = new App();

        new HelloStack(app, "hello-cdk-1");

        app.synth();
    }
}
