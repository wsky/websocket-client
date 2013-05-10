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
package com.taobao.top.link.embedded.websocket.jetty;

import java.net.InetSocketAddress;

import com.taobao.top.link.embedded.websocket.WebSocket;
import com.taobao.top.link.embedded.websocket.WebSockets;
import com.taobao.top.link.embedded.websocket.auth.Credentials;
import com.taobao.top.link.embedded.websocket.auth.DefaultAuthenticator;
import com.taobao.top.link.embedded.websocket.exception.WebSocketException;
import com.taobao.top.link.embedded.websocket.frame.Frame;
import com.taobao.top.link.embedded.websocket.handler.WebSocketHandler;
import com.taobao.top.link.embedded.websocket.proxy.Proxy;
import com.taobao.top.link.embedded.websocket.util.PacketDumpUtil;



/**
 * The Class WebSocketChatServletProxyTest.
 *
 * @author Takahiro Hashimoto
 */
public class WebSocketChatServletProxyTest {
	
	/**
	 * The main method.
	 *
	 * @param argv the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] argv) throws Exception {
		System.setProperty("websocket.packatdump", String.valueOf(
				PacketDumpUtil.ALL
		));
//		System.setProperty("javax.net.debug", "all");
		System.setProperty("java.util.logging.config.file", "logging.properties");
//		Proxy proxy = new Proxy(new Credentials("test", "test"), new BasicAuthenticator());
        //SpengoAuthenticator.setDefaultMechanism(Mechanism.NTLM);
		Proxy proxy = new Proxy(new InetSocketAddress("192.168.100.230", 8080),new Credentials("test", "test"), new DefaultAuthenticator());
//		Proxy proxy = new Proxy(new Credentials("test", "test"), new DigestAuthenticator());
		WebSocket socket = WebSockets.createDraft06("wss://echo.websocket.org", null, proxy, new WebSocketHandler() {

			public void onOpen(WebSocket socket) {
				System.err.println("Open");
				try {
					socket.send(socket.createFrame(System.getenv("USER") + ":has joined!"));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			public void onMessage(WebSocket socket, Frame frame) {
				if(!frame.toString().startsWith(System.getenv("USER"))){
					try {
						socket.send(socket.createFrame(System.getenv("USER") + ":(echo)" + frame.toString()));
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				System.out.println(frame);
			}
			
			public void onError(WebSocket socket, WebSocketException e) {
				e.printStackTrace();
			}
			
			public void onClose(WebSocket socket) {
				System.err.println("Closed");
			}

			public void onCloseFrame(WebSocket socket, int statusCode, String reason) {
				// TODO Auto-generated method stub
				
			}
		}, "chat");
		socket.setBlockingMode(true);
		socket.connect();
	}
}
