package com.kernel.common.global.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListJsonConverter {

    private final Gson gson = new Gson();

    /**
     * Json 문자열을 List<String>으로 변환합니다.
     * @param json
     * @return List<String> 변환된 리스트, json이 null 또는 비어있으면 빈 리스트 반환
     */
    public List<String> parseJsonToList(String json) {
        if (json == null || json.isBlank()) return new ArrayList<>();

        return gson.fromJson(json, new TypeToken<List<String>>() {}.getType());
    }

    /**
     * List<String>을 Json 문자열로 변환합니다.
     * @param list
     * @return String 변환된 Json 문자열, 리스트가 null 또는 비어있으면 빈 문자열 반환
     */
    public String convertListToJson(List<String> list) {
        if (list == null || list.isEmpty()) return "";

        return gson.toJson(list);
    }
}
