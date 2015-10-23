package github.zhangchaolts.Java.crawler.weibo_login;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractFollowPageInfo {

    static String extractOneInfo(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
    
    static ArrayList<String> extractMoreInfo(String content, String regex) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()) {
            arrayList.add(matcher.group(1));
        }
        return arrayList;
    }
    
    static FollowPageInfo extract(String content) { 
        FollowPageInfo followPageInfo = new FollowPageInfo();
        followPageInfo.setUsers((Integer.parseInt(extractOneInfo(content, "��ע��(.*)��"))));
        followPageInfo.setPages((int)Math.ceil((Double.parseDouble(extractOneInfo(content, "��ע��(.*)��"))/20.0)));
        followPageInfo.setUids(extractMoreInfo(content, "namecard=\".*\" uid=\"(.*)\" imgtype=\".*\" src="));
        return followPageInfo;
    }

}
