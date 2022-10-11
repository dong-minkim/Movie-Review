package com.dutmdcjf.moviereviewproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO {

    private String fileName;
    private String uuid;
    private String folderPath;

    //전체 경로 (나중에 쓰일 수도)
    public String getImageURL() {
        try {
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    //섬네일 이미지 화면 처리
    public String getThumbnailURL(){
        try {
            return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
}

