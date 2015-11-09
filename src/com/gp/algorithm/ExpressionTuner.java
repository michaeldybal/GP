package com.gp.algorithm;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Prepares the line to the calculation. 
 */
public class ExpressionTuner {

	private static final String OPERATORS = "+-*/";
	private static final String SEPARATORS = "()";
	private static final String LITERALS_2 = "01";
	private static final String LITERALS_10 = "0123456789";
	private static final String LITERALS_16 = "0123456789abcdefABCDEF";
	
	/**
	 * Lsogger object
	 */
	private static final Logger log = LogManager.getLogger();


	/**
	 * The method of preparing a line to the calculation.
	 * Replace spaces and tabs
	 * add * before '(' and after ')'
	 * @param s - expression
	 * @param notation - flag
	 * @return prepared expression
	 */
	public static String prepareExpression(String s, int notation){
		s = s.replaceAll(" ", ""); 												// replace spaces
		s = s.replaceAll("	", ""); 											// replace tabs

		if (ExpressionTuner.CheckIllegalSymbols(s, notation) && ExpressionTuner.CheckBrackets(s))	// if expression not  has errors
				s = ExpressionTuner.SetSymbolsMultiply(s);								// add * after brackets
		return s;
	}
	
	/**
	 * Checks whether a character is a literal
	 * @param c - symbol
	 * @param notation - flag
	 * @return - bool result
	 */
	public static boolean CheckLiteral(char c, int notation) {
		String legalSymbols = "";
		switch (notation) {
		case 1: // binary
			legalSymbols = LITERALS_2;
			break;
		case 2: // hex
			legalSymbols = LITERALS_16;
			break;
		default: // decimal
			legalSymbols = LITERALS_10;
		}

		if ((legalSymbols.indexOf(c)) > -1)
			return true;
		
		return false;
	}
	
	/**
	 * Mathod add * before '(' and after ')'
	 * @param s - expression
	 * @return expression
	 */
	private static String SetSymbolsMultiply(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				if (i > 0) {
					if ((OPERATORS + '(').indexOf(s.charAt(i - 1)) == -1) {
						sb.append('*');
					}
				}
			}

			sb.append(c);
			if (c == ')') {
				if (i < s.length() - 1) {
					if ((OPERATORS + SEPARATORS).indexOf(s.charAt(i + 1)) == -1) {
						sb.append('*');
					}
				}
			}
		}
		s = sb.toString();
		return s;
	}

	/**
	 * Check Illegal Symbols
	 * @param s - expression
	 * @param notation - flag
	 * @return - expression
	 */
	private static boolean CheckIllegalSymbols(String s, int notation) {
		String legalSymbols = OPERATORS + SEPARATORS;
		switch (notation) {
		case 1: // binary
			legalSymbols += LITERALS_2;
			break;
		case 2: // hex
			legalSymbols += LITERALS_16;
			break;
		default: // decimal
			legalSymbols += LITERALS_10;
		}

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if ((legalSymbols.indexOf(c)) == -1) {
				log.error("Symbols is illegal");
				return false;
			}
		}
		return true;
	}

	/**
	 * Check Brackets
	 * @param s - expression
	 * @return - bool result
	 */
	private static boolean CheckBrackets(String s) {
		LinkedList<Character> someOpers = new LinkedList<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				someOpers.add('(');
			} else if (c == ')') {
				if (someOpers.size() > 0)
					someOpers.removeLast();
				else {
					log.error("Backets is illegal");
					return false;
				}
			}
		}
		return true;
	}	
}
