package org.example;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

import java.io.PrintWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello world!");

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(CRUDTest.class))
                .build();

        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        while(true){
            launcher.execute(request, listener);

            TestExecutionSummary summary = listener.getSummary();
            summary.printTo(new PrintWriter(System.out, true));

            List<Failure> failureList = summary.getFailures();

            if(!failureList.isEmpty()){
                System.out.println("Failed tests:");

                for(Failure failure : failureList){
                    System.out.println("Test: " + failure.getTestIdentifier().getDisplayName());
                    System.out.println("Reason: " + failure.getException().getMessage());
                    failure.getException().printStackTrace();
                }
            }else {
                System.out.println("All tests passed!");
            }

            Thread.sleep(60*1000); // 1 минута
        }

    }
}
