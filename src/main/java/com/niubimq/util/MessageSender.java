package com.niubimq.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.niubimq.pojo.Message;

public class MessageSender {
    
    /**
     * 发送消息给消费者
     * @throws Exception 
     * 
     * */
    public static Socket sendMessageBySocket(Message msg) throws Exception{
        
        InetAddress inetAddress = InetAddress.getByName(msg.getCallBackUrl().substring(msg.getCallBackUrl().indexOf("://")+3, msg.getCallBackUrl().indexOf("/", msg.getCallBackUrl().indexOf("://")+3)));
        String IP = inetAddress.getHostAddress();
        String hostName = inetAddress.getHostName();
        int port = 80;
        String path = msg.getCallBackUrl().substring(msg.getCallBackUrl().indexOf(hostName)+hostName.length());
        
        Socket socket = new Socket(IP, port);
        PrintWriter out=new PrintWriter(socket.getOutputStream()); 
        
        JSONObject parmJ = JSONObject.fromObject(msg.getMessageContent());
        Iterator<String> iterator = parmJ.keys();
        StringBuilder params = new StringBuilder();
        while(iterator.hasNext()){
            params.append("&");
            String key =  iterator.next();
            params.append(key + "=" + parmJ.getString(key));
        }
        if(msg.getRequestType() == Message.REQUEST_TYPE_HTTP_GET){
            
            out.print("GET "+path+params.toString().replaceFirst("&", "?")+" HTTP/1.1\r\n"); 
            out.print("Host: "+hostName+":"+port+"\r\n"); 
            out.print("User-Agent: Java/1.7.0_15\r\n"); 
            out.print("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");
            out.print("Connection: keep-alive\r\n");
            out.print("\r\n"); 
            out.flush();
        } else if(msg.getRequestType() == Message.REQUEST_TYPE_HTTP_POST){
            out.print("POST "+path+" HTTP/1.1\r\n");
            out.print("Host: "+hostName+":"+port+"\r\n"); 
            out.print("User-Agent: Java/1.7.0_15\r\n"); 
            out.print("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");
            out.print("Connection: keep-alive\r\n");
            out.print("Content-type: application/x-www-form-urlencoded\r\n");
            out.print("Content-Length: "+params.length()+"\r\n");
            out.print("\r\n");
            out.println(params.toString().replaceFirst("&", ""));
            out.flush();
        }
        
        return socket;
    }
    
    /**
     * 解析响应数据为hashmap
     *    
     * @return resultMap consumeResult :success-消费成功；failed-消费失败
     * @throws IOException 
     * 
     * */
    public static Map<String, String> parseResonse(BufferedReader reader) throws IOException{
        
        String success = "false";
        String failedReason = "";
        int count = 0;

        String tempStr;
        while(count < 2){
            if(!reader.ready()) {
                failedReason = "响应内容不合法";
                break;
            }
            tempStr = reader.readLine();
            if(StringUtils.isEmpty(tempStr)) continue;
            if(tempStr.indexOf("success") != -1){
                success = tempStr.substring(tempStr.indexOf("success")+8);
                count++;
                if("true".equals(success)) count++;
            }
            if(tempStr.indexOf("failedReason") != -1){
                failedReason = tempStr.substring(tempStr.indexOf("failedReason")+13);
                count++;
            }
        }
        
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("success", success);
        resultMap.put("failedReason", failedReason);
        return resultMap;
    }
}
