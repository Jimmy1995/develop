package com.lonema.test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;


public class ReadQRCode {

	public static void main(String[] args) throws Exception {
		//File file=new File("C:/lonema/alipay.png");//https://468bc676.ngrok.io/bppay/x2xpay/jspay.action?brCode=6000000041562&money=2&reqNo=232323
		//File file=new File("C:/lonema/alipay01.png");//有效
		File file=new File("C:/lonema/alipay02.png");//https://bp.timesdata.net/payserver/h5pay/1000000051906
		
		BufferedImage bufferedImage = ImageIO.read(file);
		
		QRCodeDecoder codeDecoder=new QRCodeDecoder();
		
		String result=new String(codeDecoder.decode(new MyQRCodeImage(bufferedImage)),"gb2312");
	
		System.out.println(result);
	}
}
