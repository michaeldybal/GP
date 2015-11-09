
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gp.dao.TaskDaoImplDerby;
import com.gp.dao.TaskFileIO;
import com.gp.entity.Task;

/**
 * Starter class run programm in console
 * 
 */
public class Starter {
	/**
	 * Flag input: 1-cmd, 2-db,3-file
	 */
	private String input = "";
	/**
	 * Flag output: 1-cmd, 2-db, 3-file
	 */
	private String output = "";
	/**
	 * Flag notation: 1-dec, 2-bit, 3-hex
	 */
	private String notation = "";
	/**
	 * Task object. Save expression and result
	 */
	private Task task = new Task();
	/**
	 * logger static object
	 */
	private static final Logger log = LogManager.getLogger();

	/**
	 * Input from cmd
	 */
	private void fromCMD() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please, enter your expression: ");
		try {
			task.setExpression(reader.readLine());
		} catch (IOException e) {
			log.error("Error read from CMD");
			e.printStackTrace();
		}
	}
	
	/**
	 * Input from db. read last record from db
	 */
	private void fromDB() {
		TaskDaoImplDerby tdi = new TaskDaoImplDerby();
		task=tdi.readLast();
	}

	/**
	 * Input from file. read one record
	 */
	private void fromFile() {
		TaskFileIO tf = new TaskFileIO();
		task.setExpression(tf.readFromFile());
	}

	
	/**
	 * Output to db. write one new record
	 */
	private void toDB() {
		TaskDaoImplDerby tdi = new TaskDaoImplDerby();
		tdi.create(task);		
	}

	/**
	 * Output to file. rewrite file
	 */
	private void toFile() {
		try {
			TaskFileIO tf = new TaskFileIO();
			tf.writeToFile(task);
		} catch (IOException e) {
			log.error("Error write task to File");
			e.printStackTrace();
		}
	}

	/**
	 * Start method. 
	 * Check input, notation and output. execute expression from task object
	 */
	public void start() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("Select input:\n1. console\n2. batabase\n3. file");
			input = reader.readLine();
			if ((input.equals("1")) || (input.equals("2")) || (input.equals("3"))) {
				break;
			}
		}

		while (true) {
			System.out.println("Select notation:\n1. decimal\n2. binary\n3. hex");
			notation = reader.readLine();
			if ((notation.equals("1")) || (notation.equals("2")) || (notation.equals("3"))) {
				break;
			}
		}
		while (true) {
			System.out.println("Select output:\n1. console\n2. batabase\n3. file.");
			output = reader.readLine();
			if ((output.equals("1")) || (output.equals("2")) || (output.equals("3"))) {
				break;
			}
		}

		switch (input) {
		case "1":
			fromCMD();
			break;
		case "2":
			fromDB();
			break;
		case "3":
			fromFile();
			break;
		default:
			fromCMD();
		}
		
		task.execute(Integer.parseInt(notation)-1);
		System.out.println(task.toString());
		
		switch (output) {
		case "2":
			toDB();
			break;
		case "3":
			toFile();
			break;
		}
	}

}
