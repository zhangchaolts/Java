package github.zhangchaolts.Java.crawler.weibo_login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Login {

    private static final HttpClient client = Main.client;       //引用Main类的client

    private static String getServertime(String content) {
        String regex = "\"servertime\":(\\d*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        String location = null;
        if (matcher.find()) {
            location = matcher.group(1);
        }
        return location;
    }

    private static String getNonce(String content) {
        String regex = "\"nonce\":\"(.*)\"\\}\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        String location = null;
        if (matcher.find()) {
            location = matcher.group(1);
        }
        return location;
    }

    public static String getSha1(byte[] src) {
        StringBuffer sb = new StringBuffer();
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("sha-1");
            md.update(src);
            for (byte b: md.digest())
                sb.append(Integer.toString(b >>> 4 & 0xF, 16)).append(Integer.toString(b & 0xF, 16));
        } catch (NoSuchAlgorithmException e) {}
        return sb.toString();
    }

    public static String getSinaSha1(String password, String servertime, String nonce) {
        String result1 = getSha1(password.getBytes());
        String result2 = getSha1(result1.getBytes());
        String result3 = getSha1((result2 + servertime + nonce).getBytes());
        return result3;
    }

    private static String getRedirectLocation(String content) {
        String regex = "location\\.replace\\('(.*?)'\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        String location = null;
        if (matcher.find()) {
            location = matcher.group(1);
        }
        return location;
    }

    private static String getURLContent(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        //String result = new String(EntityUtils.toString(entity).getBytes("ISO-8859-1"), "utf-8"); //wrong
        String result = EntityUtils.toString(entity);
        get.abort();
        return result;
    }

    static void login(String user, String pwd) throws IOException {

        HttpGet get = new HttpGet(
                "http://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&su=eW91ZGFvX2VhZF9idCU0MDE2My5jb20=&client=ssologin.js(v1.3.16)&_=1320499935178");
        get.setHeader("Host", "login.sina.com.cn");
        get.setHeader("User-Agent",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.23) Gecko/20110920 Firefox/3.6.23");
        get.setHeader("Referer", "http://weibo.com/");

        HttpResponse response = client.execute(get);
        get.abort();

        HttpEntity entity = response.getEntity();
        String preloginResult = EntityUtils.toString(entity);
        String servertime = getServertime(preloginResult);
        String nonce = getNonce(preloginResult);

        HttpPost post = new HttpPost("http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.3.16)");
        post.setHeader("User-Agent",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.23) Gecko/20110920 Firefox/3.6.23");
        post.setHeader("Referer", "http://weibo.com/");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("entry", "weibo"));
        qparams.add(new BasicNameValuePair("gateway", "1"));
        qparams.add(new BasicNameValuePair("from", ""));
        qparams.add(new BasicNameValuePair("savestate", "7"));
        qparams.add(new BasicNameValuePair("useticket", "1"));
        qparams.add(new BasicNameValuePair("ssosimplelogin", "1"));
        qparams.add(new BasicNameValuePair("vsnf", "1"));
        qparams.add(new BasicNameValuePair("vsnval", ""));
        qparams.add(new BasicNameValuePair("su", "eW91ZGFvX2VhZF9idCU0MDE2My5jb20="));
        qparams.add(new BasicNameValuePair("service", "miniblog"));
        qparams.add(new BasicNameValuePair("servertime", servertime));
        qparams.add(new BasicNameValuePair("nonce", nonce));
        qparams.add(new BasicNameValuePair("pwencode", "wsse"));
        qparams.add(new BasicNameValuePair("sp", getSinaSha1(pwd, servertime, nonce)));
        qparams.add(new BasicNameValuePair("encoding", "UTF-8"));
        qparams.add(new BasicNameValuePair("url",
                "http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack"));
        qparams.add(new BasicNameValuePair("returntype", "META"));

        UrlEncodedFormEntity params = new UrlEncodedFormEntity(qparams, "UTF-8");
        post.setEntity(params);

        response = client.execute(post);
        post.abort();

        entity = response.getEntity();

        String LoginSuccessContent = EntityUtils.toString(entity);

        String location = getRedirectLocation(LoginSuccessContent);
        if (location != null) {
            getURLContent(location);
            System.out.println("Login Success!");
        } else {
            System.out.println("Login Fail!");
        }
    }
}
