package com.xunlei.game.velocity;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import com.xunlei.game.velocity.util.CommandLineArgsUtil;
import com.xunlei.game.velocity.util.ObjectUtil;
import com.xunlei.game.velocity.util.PropertyUtil;

public class CreatePackageMain {
	public static final String separator = File.separator;
	public static String codedir;
	public static Properties properties = PropertyUtil.getPropertiesFromResource("config.properties");
	public static String packageName;
	public static String[] tables;
	public static String[] packages;

	public static void main(String[] args) {
		Map<String, String> map = CommandLineArgsUtil.resolveArgs(args);
		tables = properties.getProperty("tables").trim().split(",");
		packages = properties.getProperty("packages").trim().split(",");
		packageName = properties.getProperty("package").trim();
		if (ObjectUtil.isEmpty(packageName)) {
			throw new IllegalArgumentException("no package name.");
		}
		if (ObjectUtil.isEmpty(map.get("-codeSrcDir"))) {
			codedir = System.getProperty("user.dir") + separator + "src" + separator + ObjectUtil.getPathFromPackage(packageName);
		} else {
			codedir = map.get("-codeSrcDir").trim() + separator + ObjectUtil.getPathFromPackage(packageName);
		}

		createPackage(codedir);
		createPackages(codedir, separator, packages);
	}

	public static void createPackage(String f) {
		File file = new File(f);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				throw new IllegalArgumentException(f);
			}
		}
	}

	public static void createPackages(String root, String separator, String[] f) {
		for (int i = 0; i < f.length; i++) {
			File file = new File(root + separator + f[i]);
			if (!file.exists()) {
				if (!file.mkdirs()) {
					throw new IllegalArgumentException("createPackages failed.");
				}
			}
		}
	}
}
