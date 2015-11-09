import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

/**
 * Main class. 
 * Starts method "start" of the class Starter
 * 
 */
	private static final Logger log = LogManager.getLogger();

	public static void main(String[] args) {
		Starter starter = new Starter();
		try {
			starter.start();
		} catch (IOException e) {
			log.error("Please, check Maven settings");
			e.printStackTrace();
		}
	}
}
