/**
 *
 */
package com.appabc.http.utils;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @Description : 缩略图生成
 * @Copyright   : GL. All Rights Reserved
 * @Company     : 江苏国立网络技术有限公司
 * @author      : 杨跃红
 * @version     : 1.0
 * Create Date  : 2014年10月31日 下午3:35:36
 */
public class PicThumbnailUtil {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private String inputDir; // 输入图路径
	private String outputDir; // 输出图路径
	private String inputFileName; // 输入图文件名
	private String outputFileName; // 输出图文件名
	private int outputWidth = 100; // 默认输出图片宽
	private int outputHeight = 100; // 默认输出图片高
	private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)

	private boolean createThumbnail() {
		// 建立输出文件对象
		File file = new File(outputDir + outputFileName);
		FileOutputStream tempout = null;
		try {
			tempout = new FileOutputStream(file);
		} catch (Exception ex) {
			logger.info(ex.toString());
		}
		Image img = null;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Applet app = new Applet();
		MediaTracker mt = new MediaTracker(app);
		try {
			img = tk.getImage(inputDir + inputFileName);
			mt.addImage(img, 0);
			mt.waitForID(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (img.getWidth(null) == -1) {
			logger.info(" can't read,retry!" + "<BR>");
			return false;
		} else {
			int new_w;
			int new_h;
			if (this.proportion == true) { // 判断是否是等比缩放.
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) img.getWidth(null))
						/ (double) outputWidth + 0.1;
				double rate2 = ((double) img.getHeight(null))
						/ (double) outputHeight + 0.1;
				double rate = rate1 > rate2 ? rate1 : rate2;
				new_w = (int) (((double) img.getWidth(null)) / rate);
				new_h = (int) (((double) img.getHeight(null)) / rate);
			} else {
				new_w = outputWidth; // 输出的图片宽度
				new_h = outputHeight; // 输出的图片高度
			}
			BufferedImage buffImg = new BufferedImage(new_w, new_h,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = buffImg.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, new_w, new_h);
			g.drawImage(img, 0, 0, new_w, new_h, null);
			g.dispose();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(tempout);
			try {
				encoder.encode(buffImg);
				tempout.close();
			} catch (IOException ex) {
				logger.info(ex.toString());
			}
		}
		return true;
	}

	/**
	 * 大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放
	 * 
	 * @param inputDir 输入图路径
	 * @param outputDir 输出图路径
	 * @param inputFileName 输入图文件名
	 * @param outputFileName 输出图文件名
	 * @param width 输出图片宽
	 * @param height 输出图片高
	 * @param proportion 是否等比缩放
	 * @return
	 */

	public boolean generateThumbnail(String inputDir, String outputDir,
			String inputFileName, String outputFileName, int width, int height,
			boolean proportion) {
		this.inputDir = inputDir;
		this.outputDir = outputDir;
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		this.outputWidth = width;
		this.outputHeight = height;
		this.proportion = proportion;
		return createThumbnail();
	}

	
	public static void main(String[] args) {
		PicThumbnailUtil dp = new PicThumbnailUtil();
		dp.generateThumbnail("D:/photo/", "D:/photo/100x100/", "165738dstx06ux619tszaw.jpg", "165738dstx06ux619tszaw_500x500.jpg", 500, 500, true);
	}
}