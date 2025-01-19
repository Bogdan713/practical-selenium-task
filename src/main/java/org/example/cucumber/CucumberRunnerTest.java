package org.example.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/main/resources/features",
    glue = {"org.example.cucumber.steps", "org.example.cucumber.hooks"},
    plugin = {"pretty", "html:target/cucumber-reports.html"},
    monochrome = true
)
public class CucumberRunnerTest extends AbstractTestNGCucumberTests {
}