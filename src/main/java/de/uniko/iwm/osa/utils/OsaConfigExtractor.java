package de.uniko.iwm.osa.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

public class OsaConfigExtractor {

	private String db_server = null;
	private String db_user = null;
	private String db_password = null;

	private String start = "$wspvars['dbcon'] =";
	private Pattern p = Pattern.compile("'([^']*)'");

	// -----------------------------------------------------------

	private String osaBase;
	// private String osaName = null;
	private String cyquestConfig;
	List<String> osaNames;

	public OsaConfigExtractor(String osaBase, String cyquestConfig) {
		this.osaBase = osaBase;
		this.cyquestConfig = cyquestConfig;
		
		osaNames = generateOsaList(osaBase);
	}

	public static void main(String args[]) {
		// String base = "/var/www/psychosa/data/include/dbaccess.inc.php";
		// String test =
		// "$wspvars['dbcon'] = @mysql_connect('localhost','dbusr_osa','iQAq8KWW');";

		OsaConfigExtractor dbce = new OsaConfigExtractor("/var/www/",
				"data/include/dbaccess.inc.php");
		if (dbce.extract("psychosa"))
			System.out.println("Success: [(" + dbce.getDb_server() + ")("
					+ dbce.getDb_user() + ")(" + dbce.getDb_password() + ")]");
		else
			System.out.println("Fail");
	}

	public boolean extract(String osaName) {

		String[] basePathParts = { osaBase, osaName, cyquestConfig };
		String path = generateBasePath(basePathParts);

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

	public List<String> generateOsaList(String base) {
		File baseDir = new File(base);
		File[] fileList = baseDir.listFiles();

		List<String> names = new ArrayList<String>();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				String name = fileList[i].getName();
				names.add(name);
			}
		}
		
		return names;
	}

	// ----------------------------------------------------------------------

	private String generateBasePath(String[] args) {
		String dummy = StringUtils.join(args, "/");

		return FilenameUtils.normalize(dummy);
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

	public List<String> getOsaNames() {
		return osaNames;
	}
	
	public boolean hasOsa(String name) {
		return osaNames.contains(name);
	}
}
