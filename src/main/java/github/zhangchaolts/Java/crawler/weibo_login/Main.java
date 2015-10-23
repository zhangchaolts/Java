package github.zhangchaolts.Java.crawler.weibo_login;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Main {

    static HttpClient client = new DefaultHttpClient();

    static String getURLContent(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        //String content = EntityUtils.toString(entity);
        String content = EntityUtils.toString(entity, "utf-8");
        get.abort();
        //content = Decoder.decodeUTF8(content);  //这一步耗时比较久
        return content;
    }

    public static void main(String[] args) throws IOException {

        ArrayList<UserInfo> userList = new ArrayList<UserInfo>();

        Login.login("youdao_ead_bt@163.com", "509509");
        String content = getURLContent("http://weibo.com/u/2059856394");//http://weibo.com/kaifulee //http://weibo.com/zhangyaqin  //http://weibo.com/quanjingmm
        System.out.println(content);
        UserInfo userInfo = ExtractUserInfo.extract(content);  
        System.out.println(userInfo.getUid());
        System.out.println(userInfo.getNickname());
        System.out.println(userInfo.getSpecialUid());
        System.out.println(userInfo.getGender());
        System.out.println(userInfo.getLocation());
        System.out.println(userInfo.getFollow());
        System.out.println(userInfo.getFans());
        System.out.println(userInfo.isVipflag());
        System.out.println(userInfo.getVipname());
        
        
        userList.add(userInfo);
        content = getURLContent("http://weibo.com/" + userInfo.getUid() + "/follow");
        FollowPageInfo followPageInfo = ExtractFollowPageInfo.extract(content);

        for (int i = 0; i < followPageInfo.getPages(); i++) {
            content = getURLContent("http://weibo.com/" + userInfo.getUid() + "/follow?&uid=" + userInfo.getUid()
                    + "&tag=&page=" + String.valueOf(i + 1));
            followPageInfo = ExtractFollowPageInfo.extract(content);
            ArrayList<String> pageUidList = followPageInfo.getUids();
            for (int j = 0; j < pageUidList.size(); j++) {
                System.out.println((i * 20 + (j + 1)) + " : " + pageUidList.get(j));
            }
        }

    }
}
