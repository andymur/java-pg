package com.andymur.pg.java.yaml;

import java.io.InputStream;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.andymur.pg.java.yaml.model.dao.MoodDao;
import com.andymur.pg.java.yaml.model.dao.WordDescriptionDao;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class YamlRunner {
	public static void main(String[] args) {
		System.out.println("============================== STRUCTURED READ EXAMPLE ========================================== ");
		iterateAndPrint(typedRead(WordDescriptionDao.class, "german-words.yml"));
		System.out.println("============================== SIMPLE READ EXAMPLE ============================================== ");
		iterateAndPrint(simpleRead("german-verb.yml"));
		System.out.println("============================== STRUCTURED READ EXAMPLE (VERB ONLY) ============================== ");
		iterateAndPrint(typedRead(MoodDao.class,"german-verb.yml"));
	}

	private static Stream<Object> simpleRead(final String resourceName) {
		final Yaml yaml = new Yaml();
		final InputStream resourceAsStream = YamlRunner.class.getClassLoader()
				.getResourceAsStream(resourceName);
		return StreamSupport.stream(yaml.loadAll(resourceAsStream).spliterator(), false);
	}

	@SuppressWarnings("unchecked")
	private static <T> Stream<T> typedRead(final Class<T> clazz, final String resourceName) {
		final Yaml typedYaml = new Yaml(new Constructor(clazz));

		final InputStream resourceAsStream = YamlRunner.class.getClassLoader()
				.getResourceAsStream(resourceName);

		return StreamSupport.stream((Spliterator<T>) typedYaml.loadAll(resourceAsStream).spliterator(), false);
	}

	private static <T> void iterateAndPrint(Stream<T> stream) {
		stream.forEach(
				System.out::println
		);
	}
}
