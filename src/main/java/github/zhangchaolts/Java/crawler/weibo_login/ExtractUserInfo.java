package github.zhangchaolts.Java.crawler.weibo_login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractUserInfo {

    static String extractOneInfo(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
    
    static String extractGender(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group().replaceAll("[^\u4e00-\u9fa5]", "");
        } else {
            return "";
        }
    }
    
    static String extractLocation(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            String info = matcher.group(1);
            int end = info.indexOf("<\\/p>");
            return info.substring(0,end);
        } else {
            return "";
        }
    }
    
    static UserInfo extract(String content) { 
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(extractOneInfo(content, "CONFIG\\['oid'\\] = '(.*)'"));
        userInfo.setNickname(extractOneInfo(content, "CONFIG\\['onick'\\] = '(.*)'"));
        userInfo.setSpecialUid(extractOneInfo(content, "javascript:window\\.open.*http://weibo.com/(.*)', 'newwindow'"));
        userInfo.setGender(extractGender(content, "title=\\\\\"[男|女]\\\\\"\\>&nbsp;"));
        userInfo.setLocation(extractLocation(content, "title=\\\\\"[男|女]\\\\\"\\>&nbsp;(.*)\\<\\\\/p\\>.*concern clearfix"));
        userInfo.setFollow(Integer.valueOf(extractOneInfo(content, "\"follow\\\\\"\\>(\\d*)\\<\\\\/strong\\>")));
        userInfo.setFans(Integer.valueOf(extractOneInfo(content, "\"fans\\\\\"\\>(\\d*)\\<\\\\/strong\\>")));
        if(content.indexOf("W_sina_vip") != -1) {
            userInfo.setVipflag(true);
            userInfo.setVipname(extractOneInfo(content, "\\<div class=\\\\\"W_sina_vip.*span title=\\\\\"(.*)\\\\\"\\>\\<\\\\/span.*W_textb"));
        } else {
            userInfo.setVipflag(false);
        }
        return userInfo;
    }

}
