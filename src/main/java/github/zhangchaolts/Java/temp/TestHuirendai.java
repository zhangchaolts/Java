package github.zhangchaolts.Java.temp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class TestHuirendai {

	public static void main(String[] args) throws Exception {

		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpgets = new HttpGet(
				"http://www.huirendai.com/invest/list?mc=&tl=&rs=&page=2");
		HttpResponse response = httpclient.execute(httpgets);
		HttpEntity entity = response.getEntity();
		String buf = EntityUtils.toString(entity);
		System.out.println(buf);

	}
}
