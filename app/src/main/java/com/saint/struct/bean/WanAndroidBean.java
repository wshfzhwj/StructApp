package com.saint.struct.bean;

import java.util.ArrayList;

public class WanAndroidBean {
    public WanBean data;
    public int errorCode;
    public String errorMsg;

    public class WanBean {
        public int curPage;
        public int size;
        public int total;
        public int pageCount;
        public ArrayList<WanListBean> datas = new ArrayList<>();

        public class WanListBean {
            public String id;
            public String desc;
            public String niceDate;
            public String title;
            public String author;
        }
    }
}
