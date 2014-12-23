/**
 *
 */
package com.appabc.http.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @Description : 图片大小调整工具类
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年11月26日 上午11:46:21
 */
public class ResizeImageUtil {

	/**
	 * @param im 原始图像
	 * @param resizeTimes 倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @return 返回处理后的图像
	 */
	public BufferedImage zoomImage(BufferedImage im, float resizeTimes) {
		/* 原始图像的宽度和高度 */
		int width = im.getWidth();
		int height = im.getHeight();

		/* 调整后的图片的宽度和高度 */
		int toWidth = (int) (Float.parseFloat(String.valueOf(width)) * resizeTimes);
		int toHeight = (int) (Float.parseFloat(String.valueOf(height)) * resizeTimes);

		/* 新生成结果图片 */
		BufferedImage result = new BufferedImage(toWidth, toHeight,
				BufferedImage.TYPE_INT_RGB);

		result.getGraphics().drawImage(
				im.getScaledInstance(toWidth, toHeight,
						java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return result;
	}

	/**
	 * 固定大小的图片处理
	 * @param im 原始图像
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage fixedSizeImage(BufferedImage im, int width, int height) {
		/* 新生成结果图片 */
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		result.getGraphics()
				.drawImage(
						im.getScaledInstance(width, height,
								java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return result;
	}

	/**
	 * 把图片写到磁盘上
	 * 
	 * @param im
	 * @param path 图片写入的文件夹地址
	 * @param fileName 写入图片的名字
	 * @return
	 */
	public long writeToDisk(BufferedImage im, String path, String fileName) {
		File f = new File(path + fileName);
		String fileType = getExtension(fileName);
		if (fileType == null)
			return 0;
		try {
			ImageIO.write(im, fileType, f);
			im.flush();
			return f.length();
		} catch (IOException e) {
			return 0;
		}
	}

	public boolean writeHighQuality(BufferedImage im, String fileFullPath) {
		try {
			/* 输出到文件流 */
			FileOutputStream newimage = new FileOutputStream(fileFullPath
					+ System.currentTimeMillis() + ".jpg");
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
			/* 压缩质量 */
			jep.setQuality(1f, true);
			encoder.encode(im, jep);
			/* 近JPEG编码 */
			newimage.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 返回文件的文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public String getExtension(String fileName) {
		try {
			return fileName.split("\\.")[fileName.split("\\.").length - 1];
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 生成指定宽、高的略图
	 * @param inputDir
	 * @param outputDir
	 * @param inputFileName
	 * @param outputFileName
	 * @param width
	 * @param height
	 * @return 返回图片的大小
	 */
	public long thum(String inputDir, String outputDir, String inputFileName,
			String outputFileName, int width, int height) {

		BufferedImage buf = null;
		try {
			buf = ImageIO.read(new File(inputDir + inputFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(buf != null){
			BufferedImage bufThum = fixedSizeImage(buf, width, height);
			return writeToDisk(bufThum, outputDir, outputFileName);
		}else{
			return 0;
		}

	}
	
	public static void main(String[] args) throws Exception {

		ResizeImageUtil ri = new ResizeImageUtil();

		ri.thum("D:/photo/", "D:/photo/100x100/", "165738dstx06ux619tszaw.jpg", "165738dstx06ux619tszaw_100x100_002.jpg", 100, 100);

	}
}