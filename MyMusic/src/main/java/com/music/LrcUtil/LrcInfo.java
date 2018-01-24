package com.music.LrcUtil;

import java.util.List;

/**
 * Created by 501000704 on 2017/12/18.
 */

public class LrcInfo {

    private List<LyricContent> lrcLists;

    public LrcInfo(List<LyricContent> lrcLists) {
        this.lrcLists = lrcLists;
    }

    public List<LyricContent> getLrcLists() {
        return lrcLists;
    }

    public static class LyricContent{
        private int lyricTime ;
        private String lyricContent;
        private int currentID;
        public void setLyric(String string) {
            this.lyricContent=string;

        }

        public void setLyric(int lyricTime) {
            this.lyricTime=lyricTime;

        }

        public int getLyricTime() {
            return lyricTime;
        }

        public String getContent() {
            return lyricContent;
        }


    }
}
