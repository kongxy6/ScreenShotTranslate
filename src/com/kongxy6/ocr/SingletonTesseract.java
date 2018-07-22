package com.kongxy6.ocr;

import java.io.File;

import net.sourceforge.tess4j.Tesseract1;

public class SingletonTesseract {

	private static final SingletonTesseract SINGLETON_TESSERACT = new SingletonTesseract();

	private Tesseract1 tesseract;

	private SingletonTesseract() {
		// TODO Auto-generated constructor stub
		tesseract = new Tesseract1();// �״μ�����Դ��Ϊ��ʱ
		tesseract.setDatapath(new File(".").getPath());
	}

	public static SingletonTesseract getInstance() {
		return SINGLETON_TESSERACT;
	}

	public Tesseract1 geTesseract1() {
		return tesseract;
	}
}
