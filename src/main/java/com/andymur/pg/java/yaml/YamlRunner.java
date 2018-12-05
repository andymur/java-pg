package com.andymur.pg.java.yaml;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class YamlRunner {
	public static void main(String[] args) {
		final InputStream resourceStream = YamlRunner.class.getClassLoader()
				.getResourceAsStream("german-words.yml");

		final Yaml typedYaml = new Yaml(new Constructor(WordDescription.class));

		for (final Object object: typedYaml.loadAll(resourceStream)) {
			System.out.println(object);
		}
	}
}
