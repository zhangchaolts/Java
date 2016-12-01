package github.zhangchaolts.Java.crawler.jimubox_monitor;

import java.awt.Frame;
import java.awt.Toolkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class JimuboxMonitor {

	public static void bee() {
		Frame f = new Frame();
		Toolkit kit = f.getToolkit();
		int beep_times = 7;
		while (beep_times > 0) {
			kit.beep();
			beep_times--;
			try {
				Thread.sleep(175);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {

		double money_threshold = 5000;
		double rate_threshold = 9.53;
		double day_threshold = 60;
		
		if(args.length == 3) {
			money_threshold = Double.valueOf(args[0]);
			rate_threshold = Double.valueOf(args[1]);
			day_threshold = Double.valueOf(args[2]);
		} else if(args.length != 0) {
			System.out.println("usage: money_threshold rate_threshold day_threshold");
		}

		HttpClient httpclient = new DefaultHttpClient();

		while (true) {

			HttpGet httpgets = new HttpGet("https://box.jimu.com/CreditAssign/List?amount=5&repaymentCalcType=2&orderIndex=1");
			HttpResponse response = httpclient.execute(httpgets);
			HttpEntity entity = response.getEntity();
			String buf = EntityUtils.toString(entity);
			//System.out.println(buf);

			while (buf.indexOf("\"invest-item-title\"") != -1) {	// start label
				buf = buf.substring(buf.indexOf("\"invest-item-title\""));	// start label
				// System.out.println(buf);

				if(buf.indexOf("class=\"span3\"") == -1) {
					break;
				}
				String item_buf = buf.substring(0, buf.indexOf("class=\"span3\""));	// end label
				// System.out.println(item_buf);

				String gift_rate = "0";
				Pattern p0 = Pattern.compile("’€»√±»¿˝\\(%\\)£∫(.*)</div>");
				Matcher m0 = p0.matcher(item_buf);
				if (m0.find()) {
					gift_rate = m0.group(1);
					//System.out.println(gift_rate);
				}
				
				
				String project_current_money = "0";
				String project_sum_money = "0";
				String orignal_rate = "0";
				String left_days = "0";

				if (item_buf.indexOf("\"project-info\"") != -1) {
					//System.out.println("has1");
					String part_str = item_buf.substring(item_buf.indexOf("\"project-info\""));
					part_str = part_str.substring(0, part_str.indexOf("</p>"));
					//System.out.println("part_str:\n" + part_str);
					
					String line_str1 = part_str.substring(part_str.indexOf("decimal"));
					line_str1 = line_str1.substring(0, line_str1.indexOf("span>"));
					//System.out.println("line_str1:\n" + line_str1);	
					
					Pattern p1 = Pattern.compile(">(.*)<");
					Matcher m1 = p1.matcher(line_str1);
					if (m1.find()) {
						project_current_money = m1.group(1);
						//System.out.println(project_current_money);
					}
					
					part_str = part_str.substring(part_str.indexOf("<span>/</span>"));
					
					String line_str2 = part_str.substring(part_str.indexOf("decimal"));
					line_str2 = line_str2.substring(0, line_str2.indexOf("span>"));
					//System.out.println("line_str2:\n" + line_str2);
					
					Pattern p2 = Pattern.compile(">(.*)<");
					Matcher m2 = p2.matcher(line_str2);
					if (m2.find()) {
						project_sum_money = m2.group(1);
						//System.out.println(project_sum_money);
					}
					
				}

				if (item_buf.indexOf("invest-item-features creditassign-item-features") != -1) {
					//System.out.println("has2");
					String part_str = item_buf.substring(item_buf.indexOf("invest-item-features creditassign-item-features"));
					part_str = part_str.substring(0, part_str.indexOf("clearfix"));
					//System.out.println(part_str);

					String line_str1 = part_str.substring(part_str.indexOf("invest-item-profit"));
					line_str1 = line_str1.substring(0, line_str1.indexOf("span>"));
					//System.out.println("line_str1:\n" + line_str1);
					
					Pattern p1 = Pattern.compile("(.*)<");
					Matcher m1 = p1.matcher(line_str1);
					if (m1.find()) {
						orignal_rate = m1.group(1).trim();
						//System.out.println(orignal_rate);
					}
					
					part_str = part_str.substring(part_str.indexOf("split") + "split".length());
					
					String line_str3 = part_str.substring(part_str.indexOf("invest-item-profit"));
					line_str3 = line_str3.substring(0, line_str3.indexOf("span>"));
					//System.out.println("line_str3:\n" + line_str3);
					
					Pattern p3 = Pattern.compile("(.*)<");
					Matcher m3 = p3.matcher(line_str3);
					if (m3.find()) {
						left_days = m3.group(1).trim();
						//System.out.println(left_days);
					}
				}

	
				double project_current_money_double = Double.valueOf(project_current_money.replaceAll(",", ""));
				double project_sum_money_double = Double.valueOf(project_sum_money.replaceAll(",", ""));
				double gift_rate_double = Double.valueOf(gift_rate);
				double orignal_rate_double = Double.valueOf(orignal_rate);
				double left_days_double = Double.valueOf(left_days);

				double valid_money = project_sum_money_double - project_current_money_double;
				double valid_rate = gift_rate_double + orignal_rate_double;
				
				System.out.println(valid_money + ", " + valid_rate + ", " + left_days_double);
				
				if ((valid_money >= money_threshold) && (valid_rate >= rate_threshold) && (left_days_double >= day_threshold)) {
					bee();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				buf = buf.substring(buf.indexOf("class=\"span3\""));	//end label
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("-----------------------------------------------------------");
		}
	}
}
