package com.moxie.cloud.services.server.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 */
public class PatternExtend {
    private Pattern regExp;
    private static final Pattern REPLACE_PATTERN = Pattern
            .compile("\\(\\?<\\w+>");
    private static final Pattern NAMED_PATTERN = Pattern
            .compile("\\(\\?<(\\w+)>");
    private Map<Integer, String> groupNameMap = new MatchOutMap<Integer, String>();

    public static PatternExtend compile(String regex) {
        PatternExtend patternExtend = new PatternExtend();
        patternExtend.regExp = Pattern.compile(regex.replaceAll(
                REPLACE_PATTERN.toString(), "("));
        Matcher matcher = NAMED_PATTERN.matcher(regex);
        int idx = 0;
        while (matcher.find()) {
            idx++;
            patternExtend.groupNameMap.put(idx, matcher.group(1));
        
        }
        return patternExtend;
    }
    

    /**
     * 正则匹配结果

     * @param input
     * @return
     */
    public Map<String, String> matcher(CharSequence input) {
        Matcher matcher = regExp.matcher(input);
        Map<String, String> resultMap = null;
        if (matcher.find()) {
            resultMap = new HashMap<String, String>();
            int groupCount=0;
            if(getGroupCount()<=0)
            {
                groupCount=matcher.groupCount();
            }
            else
            {
                groupCount=getGroupCount();
            }
            for (int i = 1; i <= groupCount; i++) {
                resultMap.put(this.getGroupName(i), getGroupValue(StringEscapeUtils.unescapeHtml4(matcher.group(i))));
            }
            return resultMap;
        } else {
            return null;
        }
    }
    
    /**
     * 正则匹配结果
     *
     * @param input
     * @return
     */
    public Matcher matcherO(CharSequence input) {
        return regExp.matcher(input);
    }

    /**
     * 正则匹配结果 返回类型为

     * @param input
     * @return
     */
    public NameValuePair matcherPair(CharSequence input) {
        Matcher matcher = regExp.matcher(input);
        if (matcher.find()) {
            return new BasicNameValuePair(this.getGroupName(1), getGroupValue(matcher.group(1)));
        } else {
            return null;
        }
    }

    /**
     * 正则匹配结果

     * @param input
     * @return
     */
    public List<NameValuePair> matcherList(CharSequence input) {
        Matcher matcher = regExp.matcher(input);
        NameValuePair nameValuePair = null;
        List<NameValuePair> resultEnity = new ArrayList<NameValuePair>();
        if (matcher.find()) {
            int groupCount=0;
            if(getGroupCount()<=0)
            {
                groupCount=matcher.groupCount();
            }
            else
            {
                groupCount=getGroupCount();
            }
            for (int i = 1; i <= groupCount; i++) {
                nameValuePair = new BasicNameValuePair(this.getGroupName(i),getGroupValue(matcher.group(i)));
                resultEnity.add(nameValuePair);
            }
            return resultEnity;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public String matcherV(CharSequence input) {
        Matcher matcher = regExp.matcher(input);
        if (matcher.find()) {
            return getGroupValue(matcher.group(1));
        } else {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public String matcherV(CharSequence input,int groupIndex) {
        Matcher matcher = regExp.matcher(input);
        if (matcher.find()) {
            return getGroupValue(matcher.group(groupIndex));
        } else {
            return null;
        }
    }

    /**
     * 返回匹配结果集合
     * 
     * zwl: 从input字符串中构造符合正则表达式regExp列表, key为正则表达式组名(即中括号中的字符串), value为组名配置的值
     * 
     * @param input
     * @return
     */
    public List<Map<String, String>> matcherMapList(CharSequence input) {
        Matcher matcher = regExp.matcher(input);
        Map<String, String> resultMap = null;
        List<Map<String, String>> resutMapList = new ArrayList<Map<String, String>>();
        while (matcher.find()) {
            resultMap = new HashMap<String, String>();
            int groupCount=0;
            if(getGroupCount()<=0)
            {
                groupCount=matcher.groupCount();
            }
            else
            {
                groupCount=getGroupCount();
            }
            for (int i = 1; i <= groupCount; i++) {
                resultMap.put(this.getGroupName(i),getGroupValue(matcher.group(i)));
            }
            resutMapList.add(resultMap);
        }
        if (resutMapList.size() > 0) {
            return resutMapList;
        } else {
            return null;
        }
    }

    /**
     * 是否匹配
     * 
     * @param input
     * @return
     */
    public boolean isMatch(CharSequence input) {
        Matcher matcher = regExp.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 获取匹配组的名称
     * 
     * @param groupIndex
     * @return
     */
    private String getGroupName(int groupIndex) {
        String groupName = this.groupNameMap.get(groupIndex);
        return groupName == null ? String.valueOf(groupIndex) : groupName;
    }

    /**
     * 获取匹配组的值
     * @return
     */
    private String getGroupValue(String groupValue)
    {
        return groupValue == null ? groupValue : groupValue.trim();
    }
    
    /**
     * 获取组的数量
     * 
     * @return
     */
    public int getGroupCount() {
        return this.groupNameMap.size();
    }

    @Override
    public String toString() {
        return regExp.toString();
    }

}
