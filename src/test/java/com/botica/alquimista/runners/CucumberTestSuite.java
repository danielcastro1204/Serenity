package com.botica.alquimista.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Suite JUnit 5 que ejecuta todos los .feature con Cucumber y reporta en Serenity.
 * Para filtrar por tags: ./gradlew clean test -Dcucumber.filter.tags="@Issue-5"
 * El archivo cucumber.json lo usa el pipeline para actualizar los Issues de GitHub.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.botica.alquimista")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel,pretty,"
                + "json:target/cucumber-reports/cucumber.json")
class CucumberTestSuite {
}
