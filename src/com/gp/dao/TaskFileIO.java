package com.gp.dao;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gp.entity.Task;

/**
 * Reading and writing to a file
 * 
 */
public class TaskFileIO {
	private static final String inputFileName = "expression.txt";
	private static final String outputFileName = "result.txt";
	private static final Logger log = LogManager.getLogger();
	
	/**
	 * read one expression from input file 
	 * @return expression
	 */
	public String readFromFile() {
		String taskBody = "";
		try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				taskBody = line;
			}
		} catch (IOException e) {
			log.error("ReadFromFile error");
		}
		return taskBody;
	}

	/**
	 * write result to output file
	 * @param task
	 * @throws IOException
	 */
	public void writeToFile(Task task) throws IOException {
		File file = new File(outputFileName);

		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			PrintWriter out = new PrintWriter(file.getAbsoluteFile());

			try {
				out.print(task.getExpression() + " = " + task.getResult());
			} finally {
				out.close();
			}
		} catch (IOException e) {
			log.error("writeToFile err");
			throw new RuntimeException(e);
		}
	}
}
