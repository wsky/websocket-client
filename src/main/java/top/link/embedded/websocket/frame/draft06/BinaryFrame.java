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
package top.link.embedded.websocket.frame.draft06;

import top.link.embedded.websocket.frame.FrameHeader;
import top.link.embedded.websocket.frame.draft06.FrameBuilderDraft06.Opcode;

/**
 * The Class BinaryFrame.
 *
 * @author Takahiro Hashimoto
 */
public class BinaryFrame extends FrameDraft06 {

	/**
	 * Instantiates a new binary frame.
	 *
	 * @param bodyData the contents data
	 */
	public BinaryFrame(byte[] bodyData){
		FrameHeader header = FrameBuilderDraft06.createFrameHeader(bodyData, false, Opcode.BINARY_FRAME);
		setHeader(header);
		setContents(bodyData);
	}
	
	/**
	 * Instantiates a new binary frame.
	 *
	 * @param header the header
	 * @param bodyData the contents data
	 */
	protected BinaryFrame(FrameHeader header, byte[] bodyData) {
		super(header, bodyData);
	}
}