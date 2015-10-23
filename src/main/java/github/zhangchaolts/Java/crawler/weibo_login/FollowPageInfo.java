package github.zhangchaolts.Java.crawler.weibo_login;

import java.util.ArrayList;

public class FollowPageInfo {

    private int users;

    private int pages;
    
    private ArrayList<String> uids;

    public int getUsers() {
        return users;
    }
    
    public void setUsers(int users) {
        this.users = users;
    }
    
    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
    
    public ArrayList<String> getUids() {
        return uids;
    }

    public void setUids(ArrayList<String> uids) {
        this.uids = uids;
    }

}
