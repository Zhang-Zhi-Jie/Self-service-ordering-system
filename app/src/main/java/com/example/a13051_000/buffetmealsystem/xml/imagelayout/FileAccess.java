package com.example.a13051_000.buffetmealsystem.xml.imagelayout;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class FileAccess {

	public static InputStream String2InputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}
}
