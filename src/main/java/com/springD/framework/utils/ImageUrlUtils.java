package com.springD.framework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageUrlUtils {

	// 获取img标签正则  
    private static final String IMGURL_REG = "<img *src=(.*?) *>";  
    // 获取src路径的正则  
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";  
	// 去掉所有标签
    private static final String HTML_REG="<.+?>";
    
    private static  String content = "<p><img src=\"http://7te8dq.com1.z0.glb.clouddn.com/@/upload/1/image/20150104/1420361630818069686.jpg\" width=\"301\" height=\"400\" title=\"1420361630818069686.jpg\" alt=\"颈椎操.jpg\"/><br/>请编辑新闻内容凡事都<br/><img src=\"http://7te8dq.com1.z0.glb.clouddn.com/@/upload/1/image/20141231/1419992968910956195.jpg\" alt=\"1419992968910956195.jpg\"/><strong>凡事都</strong></p>";
    private static  String content2 = "2312312";
	
    public static void main(String[] args) {
    	System.err.println("拿到的src是"+getOneImageSrc(content2));
	}
    
    public static String getOneImageSrc(String HTML){
    	
    	 List<String> imgUrl = getImageUrl(HTML);
    	 List<String> imgSrc = getImageSrc(imgUrl);
    	 if(imgSrc.size()>0){
    		 return imgSrc.get(0);
    	 }
    	return null;
    }
	 /*** 
     * 获取ImageUrl地址 
     *  
     * @param HTML 
     * @return 
     */  
    private static List<String> getImageUrl(String HTML) {  
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);  
        List<String> listImgUrl = new ArrayList<String>();  
        while (matcher.find()) {  
            listImgUrl.add(matcher.group());  
        }  
        return listImgUrl;  
    }  
  
    /*** 
     * 获取ImageSrc地址 
     *  
     * @param listImageUrl 
     * @return 
     */  
    private static List<String> getImageSrc(List<String> listImageUrl) {  
        List<String> listImgSrc = new ArrayList<String>();  
        for (String image : listImageUrl) {  
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);  
            while (matcher.find()) {  
                listImgSrc.add(matcher.group().substring(0, matcher.group().length() - 1));  
            }  
        }  
        return listImgSrc;  
    }  

}
