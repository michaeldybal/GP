package com.gp.entity;
import org.apache.logging.log4j.*;

import com.gp.algorithm.Calculator;

/**
 * The class contains the value of the expression
 * and result
 * 
 */
public class Task {
	private int id;
	private String expression;
	private String result;
	private static final Logger log = LogManager.getLogger();
	
	public Task(){
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if(id==0)log.warn("Task id is 0");
		this.id = id;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", expression=" + expression + ", result=" + result + "]";
	}
	
	/**
	 * It calculates the value of the expression
	 * Save result in field 'Result'
	 * @param notation - flag
	 */
	public void execute(int notation){
		if((this.getExpression()!=null) && (this.getExpression()!="")){
			try{
				this.setResult(Calculator.calculateIt(this.getExpression(), notation));
			}
			catch(Exception e){
				log.error("Task Not Executed");
			}
		}
		else
			log.warn("Task expression is null");
	}
}
