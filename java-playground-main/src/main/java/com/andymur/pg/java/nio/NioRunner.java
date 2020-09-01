package com.andymur.pg.java.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class NioRunner {
	public static void main(String[] args) throws IOException {
		final Path filePath = FileSystems.getDefault().getPath("/home/amurashko/hello.txt");
		final ByteChannel channel = Files.newByteChannel(filePath, StandardOpenOption.READ);
		ByteBuffer byteBuffer = ByteBuffer.allocate(100);
		int read = channel.read(byteBuffer);
		byteBuffer.flip();
		while (read != -1) {
			while (byteBuffer.hasRemaining()) {
				System.out.println((char) byteBuffer.get());
			}
			byteBuffer.clear();
			read = channel.read(byteBuffer);
			byteBuffer.flip();
		}
		channel.close();
	}
}
