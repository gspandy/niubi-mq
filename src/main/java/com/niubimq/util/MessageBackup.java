/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.niubimq.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.niubimq.pojo.Message;

/**
 * @Description: 异常信息写入文件与读取
 * @author junjin4838
 * @version 1.0
 */
public class MessageBackup {
	
	private static final Logger log = LoggerFactory.getLogger(MessageBackup.class);
	
	private static PropertiesReader reader = PropertiesReader.getInstance();
	
	/**
	 * 将消息写到文件里面，写成json格式
	 * 每条消息占一行
	 * 每天一个文件
	 */
	public static void msgList2File(List<Message> msgList){
		
		String filePath = reader.getProperty("message.backup.filePath");
		File msgFile = new File(filePath + "messageBackup-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".txt");
		
		if(!msgFile.exists()){
			try {
				msgFile.createNewFile();
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		
		try {
			OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(msgFile,true));
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for(Message msg : msgList){
				bufferedWriter.write(JSONObject.fromObject(msg).toString());
				bufferedWriter.newLine();
			}
			fileWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 从文件中恢复保存的消息
	 */
	public static List<Message> file2MsgList(){
		
		List<Message> msgList = new ArrayList<Message>();
		
		String filePath = reader.getProperty("message.backup.filePath");
		File msgFile = new File(filePath);
		
		if(msgFile.isDirectory()){
			File[] files = msgFile.listFiles();
			for (File file : files) {
				if(file.getName().indexOf("messageBackup") == -1) continue;
				FileReader fileReader = null;
				BufferedReader bufferReader = null;
				try {
					fileReader = new FileReader(file);
					bufferReader = new BufferedReader(fileReader);
					String tempString = null;
					// 一次读入一行，直到读入null为文件结束  
					while((tempString = bufferReader.readLine()) != null) {
						msgList.add((Message) JSONObject.toBean(JSONObject.fromObject(tempString), Message.class));
					}
				} catch (IOException e) {
                    log.error(e.getMessage());					
 					e.printStackTrace();
				}finally{
					try {
						bufferReader.close();
						fileReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					file.delete();
				}
			}
		}
		return msgList;
	}

}
