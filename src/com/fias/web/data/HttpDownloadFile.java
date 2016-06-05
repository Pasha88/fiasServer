package com.fias.web.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpDownloadFile {
	private static final int BUFFER_SIZE = 8192;//4096

	public static void downloadFile(String fileURL, String saveDir) throws IOException {
		
//		 try {
//                System.out.println("DDDDDDOOOOOWNLLOADING");
//	            URL url = new URL(fileURL);
//	 
//	            URLConnection urlCon = url.openConnection();
//	 
//	            System.out.println(urlCon.getContentType());
//	 
//	            InputStream is = urlCon.getInputStream();
//	            FileOutputStream fos = new FileOutputStream(saveDir);
//	 
//	            byte[] buffer = new byte[1000];         
//	            int bytesRead = is.read(buffer);
//	             
//	            while (bytesRead > 0) {
//	                 
//	                fos.write(buffer, 0, bytesRead);
//	                bytesRead = is.read(buffer);
//	                 
//	            }
//	 
//	            is.close();
//	            fos.close();
//	             
//	        } catch (Exception e) {
//	             
//	            e.printStackTrace();
//	             
//	        }
		
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

		     if (disposition != null) {
	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 10,
	                            disposition.length() - 1);
	                }
	            } else {
	                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
	                        fileURL.length());
	            }

			//System.out.println("Content-Type = " + contentType);
			//System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;

			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			
			
			 byte[] buffer = new byte[1000];         
	            int bytesRead = inputStream.read(buffer);
	             
	            while (bytesRead > 0) {
	                 
	            	outputStream.write(buffer, 0, bytesRead);
	                bytesRead = inputStream.read(buffer);
	                 
	            }
	 
	            inputStream.close();
	            outputStream.close();
//			int bytesRead = -1;
//			byte[] buffer = new byte[BUFFER_SIZE];
//			while ((bytesRead = inputStream.read(buffer)) != -1) {
//				outputStream.write(buffer, 0, bytesRead);
//			}
//			System.out.println("File downloaded");
//			// outputStream.close();
//			inputStream.close();
//			outputStream.flush(); //
//			outputStream.close();
//			outputStream = null;
			System.gc();
			System.out.println("File downloaded");
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		//httpConn.disconnect();//*/
}
}