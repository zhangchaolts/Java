package github.zhangchaolts.Java.crawler.weibo_login;

public class UserInfo {

    private String uid;
    private String nickname;
    private String specialUid;
    private String gender;
    private String location;
    private int follow;
    private int fans;
    private boolean vipflag;
    private String vipname;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getSpecialUid() {
        return specialUid;
    }

    public void setSpecialUid(String specialUid) {
        this.specialUid = specialUid;
    }
 
    public String getGender() {
        return gender;
    }
 
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }
    

    public boolean isVipflag() {
        return vipflag;
    }

    public void setVipflag(boolean vipflag) {
        this.vipflag = vipflag;
    }

    public String getVipname() {
        return vipname;
    }
    
    public void setVipname(String vipname) {
        this.vipname = vipname;
    }

    
}
