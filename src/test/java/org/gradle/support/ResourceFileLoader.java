package org.gradle.support;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class ResourceFileLoader {
	private static final String RESOURCE_DIR = "src/test/resources/testfiles/";

	public static String readResourceContent(String filename) throws IOException {
		File file = new File(RESOURCE_DIR + filename);
		return FileUtils.readFileToString(file, "UTF-8");
	}
}
