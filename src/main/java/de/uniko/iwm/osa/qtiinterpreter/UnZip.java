package de.uniko.iwm.osa.qtiinterpreter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip {

	public static String unzipFile(InputStream is) throws IOException {

		ZipInputStream zipIs = null;

		zipIs = new ZipInputStream(new BufferedInputStream(is));
		ZipEntry zEntry; // = zipIs.getNextEntry();

		String outPath = Files.createTempDirectory("QtiInterp_").toString();
		while ((zEntry = zipIs.getNextEntry()) != null) {

			String opFilePath = outPath + System.getProperty("file.separator") + zEntry.getName();
			// System.out.println("Extracting file to " + opFilePath);

			if (zEntry.isDirectory()) continue;
			
			File parent = new File(opFilePath).getParentFile();
			if (!parent.exists())
				parent.mkdirs();

			byte[] tmp = new byte[4 * 1024];
			FileOutputStream fos = new FileOutputStream(opFilePath);
			int size = 0;
			while ((size = zipIs.read(tmp)) != -1) {
				fos.write(tmp, 0, size);
			}
			fos.flush();
			fos.close();

		}
		zipIs.close();

		return outPath;
	}
}
