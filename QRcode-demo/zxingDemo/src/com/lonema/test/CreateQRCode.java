package com.lonema.test;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

//生成二维码图片
//https://github.com/zxing/zxing/releases  ZXing 3.2.1  Source code (zip) 
public class CreateQRCode {

	public static void main(String[] args) {
		int width=300;
		int height=300;
		String format="jpg";
		//String content="http://user.qzone.qq.com/393538042/infocenter?ptsig=yTEe33nzBrI7Sb47QU8ObMVj0F*6-LgDwPEr8hZzoOg_";
		String content="http://a1.qpic.cn/psb?/V131N2Tt32AmsT/.GFVoIJxSCjQZec5Y8yu4eFego6.sbtS6SljUY1oYFg!/b/dHEBAAAAAAAA&bo=7gLpAwAAAAAFByI!&rf=viewer_4";
		//设置参数
		HashMap hints=new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		
		//生成二维码
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			Path file=new File("e:/touxiang.png").toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
