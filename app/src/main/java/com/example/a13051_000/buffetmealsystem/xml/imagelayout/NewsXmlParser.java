package com.example.a13051_000.buffetmealsystem.xml.imagelayout;

import android.util.Xml;

import com.example.a13051_000.buffetmealsystem.R;


import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;


public class NewsXmlParser {

	private List<HashMap<String, News>> newsList = null;
	

	private int[] slideImages = {
			R.drawable.c2,
			R.drawable.c1,
			R.drawable.c3,
			R.drawable.c4,
			R.drawable.c5};
	
	// ��������ļ���
	private int[] slideTitles = {
			R.string.title1,
			R.string.title2,
			R.string.title3,
			R.string.title4,
			R.string.title5,
	};
	

	
	public int[] getSlideImages(){
		return slideImages;
	}
	
	public int[] getSlideTitles(){
		return slideTitles;
	}

	
	/**
	 * ��ȡXmlPullParser����
	 * @param result
	 * @return
	 */
	private XmlPullParser getXmlPullParser(String result){
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = FileAccess.String2InputStream(result);
		
		try {
			parser.setInput(inputStream, "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return parser;
	}
	
	public int getNewsListCount(String result){
		int count = -1;
		
		try {
			XmlPullParser parser = getXmlPullParser(result);
	        int event = parser.getEventType();
	        
	        while(event != XmlPullParser.END_DOCUMENT){
	        	switch(event){
	        	case XmlPullParser.START_DOCUMENT:
	        		break;
	        	case XmlPullParser.START_TAG:
	        		if("count".equals(parser.getName())){
	        			count = Integer.parseInt(parser.nextText());
	                }
	        		
	        		break;
	        	case XmlPullParser.END_TAG:
	        		
	        		break;
	        	}
            
	        	event = parser.next();
	        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		return count;
	}
}
