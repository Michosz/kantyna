package com.mLukasik.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import com.mLukasik.model.Potrawa;

public class Formatowanie 
{
	public static List<Potrawa> zmianaFormatu(List<Potrawa> potrawy)
	{
		for(Potrawa pot: potrawy)
		{
			byte[] encodeBase64 = Base64.encodeBase64(pot.getZdjecie());
			String base64Encoded;
			try
			{
				//imageBlob is storing the base64 representation of your image data. For storing that onto your disk you need to
				//decode that base64 representation into the original binary format representation.
				//https://stackoverflow.com/questions/50427495/java-blob-to-image-file
				base64Encoded = new String(encodeBase64, "UTF-8");
				pot.setBase64(base64Encoded);
			} 
			catch (UnsupportedEncodingException e) 
			{
			}
		}
		return potrawy;
	}
}