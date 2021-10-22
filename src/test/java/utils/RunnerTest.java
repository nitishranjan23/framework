package utils;


import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * Runner class
 * 
 * @author Nitish
 * */

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/"}, 
					glue = {"steps"},
					plugin = { "pretty", "json:target/cucumber.json", "html:target/cucumber-html-reports"}
					,snippets = SnippetType.UNDERSCORE, dryRun = false, monochrome = true
					//,tags = { "~@ignore" }
					)
public class RunnerTest {

	
}
