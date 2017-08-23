package com.exempel.martin.client;

import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.javascript.host.MouseEvent;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class ExempelProjekt implements EntryPoint {
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox operand1TextBox = new TextBox();
	private TextBox operand2TextBox = new TextBox();
	private Button calculateButton = new Button("Calculate");
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private SuggestBox operatorTextBox = new SuggestBox(oracle);
	private FocusPanel enterOperator = new FocusPanel();

	// Flextable variables
	private FlexTable resultTable = new FlexTable();
	private ArrayList<String> results = new ArrayList<String>();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {

		// Suggests the valid operators
		oracle.add("*");
		oracle.add("%");
		oracle.add("+");

		resultTable.setText(1, 1, "Results");


		addPanel.add(operand1TextBox);
		addPanel.add(operatorTextBox);
		addPanel.add(operand2TextBox);
		addPanel.add(calculateButton);

		enterOperator.add(addPanel);
		mainPanel.add(enterOperator);
		mainPanel.add(resultTable);

		RootPanel.get("calc").add(mainPanel);

		// Calculate button function
		calculateButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				if (event.getNativeButton() == 1) {
					calculate();
				}
			}
		});

		// Enter key function
		enterOperator.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					calculate();
				}
			}
		});
	}

	private void calculate() {

		final String operator = operatorTextBox.getText().trim();
		calculateButton.setFocus(true);

		if ((!operator.equals("*") && !operator.equals("+") && !operator.equals("%"))
				|| !isInteger(operand1TextBox.getText().trim()) || !isInteger(operand2TextBox.getText().trim())) {
			Window.alert("You have entered a non valid binary operator or one of the operands is not an integer");

			return;
		}

		int operand1 = Integer.parseInt(operand1TextBox.getText());
		int operand2 = Integer.parseInt(operand2TextBox.getText());
		int answer=0;
		
		
		// Multiplication
		if (operator.equals("*")) {
			answer=multiplication(operand1, operand2); 
		}
		// Modulo
		else if (operator.equals("%")) {
			answer=modulo(operand1, operand2);
		}
		// Addition
		else {
			answer=addition(operand1, operand2);
		}

		tableValues(operand1, operand2, answer, operator);
	}

	// Multiplication operator method
	private int multiplication(int operand1, int operand2) {
		int answer = operand1 * operand2;
		Window.alert("The answer is: " + answer);
		return answer;
	}

	// Modulo operator method
	private int modulo(int operand1, int operand2) {
		int answer = operand1 % operand2;
		Window.alert("The answer is: " + answer);
		return answer;
	}

	// Addition operator method
	private int addition(int operand1, int operand2) {
		int answer = operand1 + operand2;
		Window.alert("The answer is: " + answer);
		return answer;
	}

	// Flextable method
	private void tableValues(int operand1, int operand2, int answer, String operator) {

		String operand1InString = Integer.toString(operand1);
		String operand2InString = Integer.toString(operand2);
		String answerToString = Integer.toString(answer);

		final String symbol = operand1InString + " " + operator + " " + operand2InString + " = " + answerToString;

		if (results.contains(symbol))
			return;

		int row = resultTable.getRowCount();
		results.add(symbol);
		resultTable.setText(row, 0, symbol);
	}

	// Checks if a String could be seen as an integer
	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}