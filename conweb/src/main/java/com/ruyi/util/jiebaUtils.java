package com.ruyi.util;

import com.huaban.analysis.jieba.JiebaSegmenter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class jiebaUtils {
    public static JiebaSegmenter segmenter = new JiebaSegmenter();


    public static List<String> getWordSplit(String word){
        return segmenter.sentenceProcess(word);
    }
}
