package com.example.hy.wanandroid.entity;

/**
 * 用户积分
 * Created by 陈健宇 at 2019/9/20
 */
public class UserCoin {

    /**
     * coinCount : 46
     * rank : 1278
     * userId : 12884
     * username : j**nyu
     */

    private int coinCount;
    private int rank;
    private int userId;
    private String username;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
