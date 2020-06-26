package runner;

import org.junit.runner.RunWith;
import commonMethods.CommonFunctions;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = { ".\src\resources\features\WeatherShopper.feature" }, glue = {
		"definitions" }, tags = { "@WeatherShopper" }, monochrome = true, strict = true, plugin = { "pretty",
				"html:target/cucumber-html-reports", "rerun:rerun.txt" })

public class Runner extends CommonFunctions {
}
