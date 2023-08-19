package com.ruyi.util;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;


import java.util.List;


public class Ahocorasick {
   /*
   *输入：词典，待匹配语句
   * 输出：命中的单词及位置
   * 构造words词典树，判断字符串中包含词典中的哪些词
   */
    //判断疾病实体
    public static List<Emit> dismatch(List<String> words,String s2){
        Trie trie = Trie.builder().ignoreCase().addKeywords(words).build();
        return (List<Emit>) trie.parseText(s2);
    }
   //判断设备实体
    public static List<Emit> eqpmatch(List<String> words,String s2){
        Trie trie = Trie.builder().ignoreCase().addKeywords(words).build();
        return (List<Emit>) trie.parseText(s2);
    }
    //判断症状实体
    public static List<Emit> symmatch(List<String> words,String s2){
        Trie trie = Trie.builder().ignoreCase().addKeywords(words).build();
        return (List<Emit>) trie.parseText(s2);
    }
}
