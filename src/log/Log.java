package log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class Log {
	private  LogManager logManager;
	private  Logger mainLogger;
	private  SimpleDateFormat sdf;
	
	public Log() {
		initLogManager();
		initMainLogger();
		sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
	}
	private String getNow() {
		return sdf.format(new Date() + "  :");
	}
	public void mainLog(String msg) {
		mainLogger.info(getNow() + msg);
	}
	public  void errorLog(Throwable e) {
		
	}
	private  void initLogManager() {
		logManager = LogManager.getLogManager();
	}
	private  void initMainLogger(){
		mainLogger = logManager.getLogger("mainLogger");
		File mainLogFile = new File("mainLog");
		if(!mainLogFile.exists()){
			try {
				mainLogFile.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			System.out.println(mainLogger);
			mainLogger.addHandler(new StreamHandler(new FileOutputStream(mainLogFile, true), new SimpleFormatter()));
		} catch (SecurityException | FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
