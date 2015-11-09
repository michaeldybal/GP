package com.gp.algorithm;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Calculator makes math
 *
 */
public class Calculator {
	/**
	 * logger object
	 */
	private static final Logger log = LogManager.getLogger();
	
	/**
	 * Check symbol is operator
	 */
	private static boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/';
	}

	/**
	 * Get priority flag.
	 * @param oper - simbol from expression
	 * @return flag priority
	 */
	private static int priority(char oper) {

		if (oper == '*' || oper == '/') {
			return 1;
		}

		else if (oper == '+' || oper == '-') {
			return 0;
		}

		else {
			return -1;
		}
	}

	/**
	 * It produces a mathematical operation
	 * @param list - float literals list
	 * @param operator - symbol operator
	 * @param notation - flag notation
	 */
	private static void oneAction(LinkedList<Float> list, char operator, int notation) {
		float right = list.removeLast();
		float left = list.removeLast();
		switch (operator) {
		case '+':
			list.add(left + right);
			break;
		case '-':
			list.add(left - right);
			break;
		case '*':
			list.add(left * right);
			break;
		case '/':
			list.add(left / right);
			break;
		default:
			log.error("Unidentified Operator");
		}
	}
	
	/**
	 * Public method calculate expression
	 * @param s - expression
	 * @param notation - flag notation
	 * @return - result string (float value)
	 */
	public static String calculateIt(String s, int notation) {
		s= ExpressionTuner.prepareExpression(s, notation);
		LinkedList<Float> someInts = new LinkedList<>();
		LinkedList<Character> someOpers = new LinkedList<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				someOpers.add('(');
			} else if (c == ')') {
				while (someOpers.getLast() != '(') {
					oneAction(someInts, someOpers.removeLast(),notation);
				}
				someOpers.removeLast();
			} else if (isOperator(c)) {
				while (!someOpers.isEmpty() && priority(someOpers.getLast()) >= priority(c)) {
					oneAction(someInts, someOpers.removeLast(),notation);
				}
				someOpers.add(c);
			} else {
				String operand = "";
				while (i < s.length() && ExpressionTuner.CheckLiteral(s.charAt(i), notation)) {
					operand += s.charAt(i++);
				}

				switch (notation){
				case 1:
					operand = Integer.toString(Integer.parseInt(operand, 2));
					break;
				case 2:
					operand = Integer.toString(Integer.valueOf(String.valueOf(operand), 16));
					break;
				}
				
				--i;
				someInts.add(Float.parseFloat(operand));
			}
		}
		while (!someOpers.isEmpty()) {
			oneAction(someInts, someOpers.removeLast(),notation);
		}
		s = Float.toString(someInts.get(0));	// decimal result
		switch (notation){
		case 1:
			s=Integer.toBinaryString(Float.floatToIntBits(someInts.get(0)));		// bitary result
			break;
		case 2:
			s=Integer.toHexString(Float.floatToIntBits(someInts.get(0)));			// hex result
			break;
		}
		return s;
	}
}
