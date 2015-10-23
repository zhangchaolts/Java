package github.zhangchaolts.Java.crawler.weibo_login;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Decoder {

    private static final String sep = "\\u";

    // Unicode转变成中文
    public static String unicode2Chinese(String u) {
        if (u == null) {
            return null;
        }
        int f = 0;
        StringBuffer b = new StringBuffer(u);
        while ((f = b.indexOf(sep, f)) < b.length() - 5 && f > -1) {
            String sub = b.substring(f += 2, f + 4);
            try {
                char c = (char) Integer.parseInt(sub, 16);
                b.delete(f - 2, f + 4);
                b.insert(f - 2, c);
                --f;
            } catch (Exception e) {}
        }
        return b.toString();
    }

    // 仅将中文转变成Unicode
    public static String chinese2Unicode(String ch) {
        String rv = null;
        if (ch != null) {
            StringBuffer b = new StringBuffer(ch);
            for (int i = 0; i < b.length(); i++) {
                char c = b.charAt(i);
                if (c > 0x4E00 && c < 0x9FA6) {
                    b.deleteCharAt(i);
                    String tmp = Integer.toHexString(c);
                    tmp = sep + tmp;
                    b.insert(i, tmp);
                    i += 5;
                }
            }
            rv = b.toString();
        }
        return rv;
    }

    static String decodeUTF8(String content) throws UnsupportedEncodingException{
        
        String result = "";
        for(int st=0; st<content.length(); st++) {
            if(st+6 <= content.length() && content.substring(st, st+6).matches("\\\\u[0-9a-f]{4}")) {
                result += unicode2Chinese(content.substring(st, st+6));
                st += 6-1;
                continue;
            }
            
            if(st+9 <= content.length() && content.substring(st, st+9).matches("(%[0-9A-F]{2}){3}")) {
                result += URLDecoder.decode(content.substring(st, st+9), "UTF-8");
                st += 9-1;
                continue;
            } 
            
            if(st+3 <= content.length() && content.substring(st, st+3).matches("%[0-9A-F]{2}")) {
                result += URLDecoder.decode(content.substring(st, st+3), "UTF-8");
                st += 3-1;
                continue;
            } 

            result += content.substring(st, st+1);
            
        }
        return result;
        
    }

}
