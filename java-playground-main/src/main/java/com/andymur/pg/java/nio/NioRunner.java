package com.andymur.pg.java.nio;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NioRunner {

	private static final int BUF_SIZE = 100;

	public static void main(String[] args) {
		writeFile("/tmp/hello.txt", generateContent("hello"));
		System.out.println(readFile("/tmp/hello.txt"));
	}

	static void writeFile(final String fileName, String content) {
		Path filePath = FileSystems.getDefault().getPath(fileName);
		try (final ByteChannel channel = Files.newByteChannel(filePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
			final byte[] contentBytes = content.getBytes();
			ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);
			int ind = 0;
			while (ind < contentBytes.length) {
				if (ind + BUF_SIZE > contentBytes.length) {
					byteBuffer.put(contentBytes, ind, contentBytes.length - ind);
				} else {
					byteBuffer.put(contentBytes, ind, BUF_SIZE);
				}
				ind += BUF_SIZE;
				byteBuffer.flip();
				channel.write(byteBuffer);
				byteBuffer.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static String generateContent(final String word) {
		List<String> words = new ArrayList<>(50);
		for (int i = 0; i < 50; i++) {
			words.add(word);
		}
		return words.stream().collect(Collectors.joining(" "));
	}

	static String readFile(final String fileName) {
		StringBuilder content = new StringBuilder();
		final Path filePath = FileSystems.getDefault().getPath(fileName);
		try (final ByteChannel channel = Files.newByteChannel(filePath, StandardOpenOption.READ)) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);
			int read = channel.read(byteBuffer);
			byteBuffer.flip();
			while (read != -1) {
				while (byteBuffer.hasRemaining()) {
					content.append((char) byteBuffer.get());
				}
				byteBuffer.clear();
				read = channel.read(byteBuffer);
				byteBuffer.flip();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}
}
