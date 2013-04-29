package de.uniko.iwm.osa.qtiinterpreter;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class DbConfigExtractor {

	private String db_server = null;
	private String db_user = null;
	private String db_password = null;

	private String start = "$wspvars['dbcon'] =";
	private Pattern p = Pattern.compile("'([^']*)'");

	public static void main(String args[]) {
		String base = "/var/www/psychosa/data/include/dbaccess.inc.php";
		// String test =
		// "$wspvars['dbcon'] = @mysql_connect('localhost','dbusr_osa','iQAq8KWW');";

		DbConfigExtractor dbce = new DbConfigExtractor();
		if (dbce.extract(base))
			System.out.println("Success: [(" + dbce.getDb_server() + ")("
					+ dbce.getDb_user() + ")(" + dbce.getDb_password() + ")]");
		else
			System.out.println("Fail");
	}

	public boolean extract(String path) {

		try {
			LineIterator it = FileUtils.lineIterator(new File(path), "UTF-8");
			try {

				while (it.hasNext()) {
					String line = it.nextLine();

					if (line.startsWith(start)) {
						return extractValues(line);
					}
				}
			} finally {
				LineIterator.closeQuietly(it);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	private boolean extractValues(String test) {
		Matcher m = p.matcher(test);
		int count = 0;

		while (m.find()) {
			// System.out.println("-> " + m.group(1));
			switch (count) {

			case 0:
				break;
			case 1:
				db_server = m.group(1);
				break;
			case 2:
				db_user = m.group(1);
				break;
			case 3:
				db_password = m.group(1);
				break;
			default:
				break;
			}

			count++;
		}

		return count == 4;
	}

	// ----------------------------------------------------------------------

	public String getDb_server() {
		return db_server;
	}

	public String getDb_user() {
		return db_user;
	}

	public String getDb_password() {
		return db_password;
	}
}
