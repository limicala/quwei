package com.limicala.util;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public final class QrcodeUtil {
	
	
	public static BitMatrix getQrcode(String url){
		int width = 400;   
        int height = 400;   
        Hashtable hints= new Hashtable();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   
        BitMatrix bitMatrix = null;
		try {
//			bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
			bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height,hints);
			//File outputFile = new File("D://quwei.png");
//	        try {
//				MatrixToImageWriter.writeToFile(bitMatrix, "png", outputFile);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitMatrix;
		
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args){
		 
		String text = "http://192.168.191.1:8080/quwei/";   
        int width = 500;   
        int height = 500;   
        String format = "png";   
        Hashtable hints= new Hashtable();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   
        BitMatrix bitMatrix;
		try {
//			bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
			bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
			File outputFile = new File("D://quwei.png");
	        try {
				MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}
	
	
}
