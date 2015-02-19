package it.xpeppers.templateEngine;

import it.xpeppers.templateEngine.exception.MissingValueException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateEngine {

	private final static String PATTERN_TO_SEARCH = "\\{\\$(\\w*)\\}";

	public String evaluate(String stringToEvaluate,
			Map<String, String> mapOfVariables) throws MissingValueException {

		String resultString = stringToEvaluate;
		
		for (String variable : extractVariables(stringToEvaluate)){
			isVariableInMap(mapOfVariables, variable);
			resultString = resultString.replaceAll(stringToSubstitute(variable), mapOfVariables.get(variable));
		}

		return resultString;
	}

	private String stringToSubstitute(String variable) {
		return "\\{\\$" + variable
				+ "\\}";
	}

	private void isVariableInMap(Map<String, String> mapOfVariables,
			String variable) throws MissingValueException {
		if(!mapOfVariables.containsKey(variable))
			throw new MissingValueException();
	}

	private List<String> extractVariables(String stringToEvaluate) {
		List<String> variablesList = new ArrayList<String>();

		Matcher matcher = Pattern.compile(PATTERN_TO_SEARCH).matcher(
				stringToEvaluate);

		while (matcher.find()) {
			variablesList.add(matcher.group(1));
		}
		return variablesList;
	}

}
