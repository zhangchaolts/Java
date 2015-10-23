package github.zhangchaolts.Java.study;


public class ThreadDemo extends Thread {
	 
    public ThreadDemo() {
 
    }
 
    public ThreadDemo(String name) {
        this.name = name;
    }
 
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "运行     " + i);
        }
    }
 
    public static void main(String[] args) {
    	ThreadDemo h1=new ThreadDemo("A");
    	ThreadDemo h2=new ThreadDemo("B");
        h1.start();
        h2.start();
    }
 
    private String name;
}
