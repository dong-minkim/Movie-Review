package com.dutmdcjf.moviereviewproject.controller;

import com.dutmdcjf.moviereviewproject.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${com.dutmdcjf.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){

        //실행 결과를 브라우저에 전달해주기 위해
        List<UploadResultDTO> uploadResultDTOList = new ArrayList<>();

        for(MultipartFile uploadFile : uploadFiles){

            //이미지 파일 check
            if(uploadFile.getContentType().startsWith("image") == false){
                log.warn("this file is not image");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            log.info("originalName: " + originalName);
            log.info("fileName: " + fileName);

            //날짜 폴더 생성, UUID, 저장할 파일이름
            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveFileName = uploadPath + File.separator + folderPath + File.separator
                                + uuid + "_" + fileName;

            //저장할 파일 경로
            Path saveFilePath = Paths.get(saveFileName);

            try{
                //원본 파일 저장
                uploadFile.transferTo(saveFilePath);

                //섬네일
                String thumbnailFileName = uploadPath + File.separator + folderPath + File.separator
                                        + "s_" + uuid + "_" + fileName;
                File thumbnailFile = new File(thumbnailFileName);
                Thumbnailator.createThumbnail(saveFilePath.toFile(), thumbnailFile, 200, 200);

                //브라우저에 전달하기 위해 담음
                uploadResultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(uploadResultDTOList, HttpStatus.OK);
    }

    private String makeFolder(){
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/",File.separator);

        //폴더 생성
        File uploadFilePath = new File(uploadPath, folderPath);
        if(uploadFilePath.exists()==false){
            uploadFilePath.mkdirs();
        }

        return folderPath;
    }


    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String origin){
        ResponseEntity<byte[]> result = null;

        try{
            String srcFileName = URLDecoder.decode(fileName,"UTF-8");

            log.info("srcFileName: " + srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);

            log.info("thumbnailFile: " + file);

            if(origin != null ){
                //s_를 제거함으로 섬네일 파일이 아닌 원본 파일 불러옴
                file = new File(file.getParent(), file.getName().substring(2));
            }

            log.info("originFile: " + file);


            HttpHeaders header = new HttpHeaders();
            //MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터 처리 (Spring에서 제공)
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        String srcFileName = null;

        try{
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator +srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(), "s_" + file.getName());

            result = thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
