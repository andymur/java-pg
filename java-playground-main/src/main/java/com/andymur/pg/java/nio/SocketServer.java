package com.andymur.pg.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketServer {
	ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();


	public static void main(String[] args) throws IOException, InterruptedException {
		final SocketServer server = new SocketServer("localhost", 9997);

		server.run();
	}

	public SocketServer(String hostname, int port) throws IOException {
		serverSocketChannel.bind(new InetSocketAddress(hostname, port));
	}

	public void run() throws IOException, InterruptedException {
		try {
			while (true) {
				System.out.println("Accepting...");
				final SocketChannel accept = serverSocketChannel.accept();
				ByteBuffer buffer = ByteBuffer.allocate(100);
				buffer.put("Hey hey hey".getBytes());
				buffer.flip();
				accept.write(buffer);
			}
		} finally {
			serverSocketChannel.close();
		}
	}
}
