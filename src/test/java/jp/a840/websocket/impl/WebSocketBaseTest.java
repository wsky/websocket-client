/*
 * The MIT License
 * 
 * Copyright (c) 2011 Takahiro Hashimoto
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jp.a840.websocket.impl;

import java.io.IOException;

import jp.a840.websocket.MockServer;
import jp.a840.websocket.WebSocket;
import jp.a840.websocket.WebSocketHandlerMock;
import jp.a840.websocket.WebSockets;
import jp.a840.websocket.exception.WebSocketException;
import jp.a840.websocket.frame.Frame;
import jp.a840.websocket.handler.WebSocketHandler;
import jp.a840.websocket.impl.WebSocketBase;
import jp.a840.websocket.impl.WebSocketImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * More tests
 * 
 * @author wsky
 */
public class WebSocketBaseTest {

	MockServer ms;

	int version = 13;

	@Before
	public void startMockServer() {
		ms = new MockServer(9999, this.version);
	}

	@After
	public void stopMockServer() throws Exception {
		ms.join(100000);
		Assert.assertFalse(ms.isAlive());
	}

	//@Test
	public void too_many_open_files_test() throws WebSocketException, IOException, InterruptedException {
		for (int i = 0; i <65536; i++) {
			connect("ws://localhost:1236/");
		}
	}

	@Test
	public void dispose_test() throws WebSocketException, IOException {
		WebSocketBase ws = (WebSocketBase) connect("ws://localhost:1236/");
		Assert.assertFalse(ws.isConnected());
		Assert.assertFalse(ws.selector.isOpen());
		Assert.assertFalse(ws.socket.isConnected());
		Assert.assertFalse(ws.socket.isConnectionPending());
		Assert.assertFalse(ws.socket.isOpen());
	}

	private WebSocket connect(String uri) throws WebSocketException {
		WebSocket startSocket = WebSockets.create(uri, new WebSocketHandlerMock(), new String[] {});
		startSocket.setBlockingMode(false);
		startSocket.setConnectionTimeout(2000);
		try {
			startSocket.connect();
		} catch (WebSocketException e) {
		} catch (IOException e) {
		}
		return startSocket;
	}
}
