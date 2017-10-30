package cn.edu.swufe.lawschool.internship.web.controller;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.file.service.FileService;
import cn.edu.swufe.lawschool.internship.web.model.ImageResponse;
import cn.edu.swufe.lawschool.internship.web.exception.UploadError;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

/**
 * Created on 2015年11月15
 * <p>Title:       图片controller</p>
 * <p>Description: 图片</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/image")
public class ImageController {
    @Logger
    private Log log;

    @Autowired
    FileService fileService;

    @RequestMapping(value = "/upload.json", method = RequestMethod.POST)
    @ResponseBody
    public ImageResponse upload (@RequestParam("file") MultipartFile file) {
        ImageResponse response = new ImageResponse();
        if (file == null) {
            throw new InternshipException(UploadError.UPLOAD_FILE_EMPTY);
        }
        String fileName = file.getOriginalFilename();
        String[] suffixArr = fileName.split("\\.");
        String suffix;
        if (suffixArr.length > 0) {
            suffix = suffixArr[suffixArr.length - 1];
        } else {
            suffix = suffixArr[0];
        }
        suffix = suffix.toLowerCase();
        if (!Arrays.asList(new String[] { "jpg", "png" }).contains(suffix)) {
            throw new InternshipException(UploadError.UPLOAD_IMAGE_FORMAT);
        }
        try{
            String saveFileName = fileService.saveFile(file.getBytes(), suffix);
            response.setUrl("/image/show/" + saveFileName);
            response.setState("SUCCESS");
            response.setTitle(suffix);
        } catch (IOException e) {
            throw new InternshipException(UploadError.UPLOAD_FILE_FAILED);
        }
        return response;
    }

    @RequestMapping(value = "/show/{fileName}", method = RequestMethod.GET)
    public void showImage (
            HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName) {
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        try{
            String suffix;
            String uri[] = request.getRequestURI().split("\\.");
            if (uri.length > 0) {
                suffix = uri[uri.length - 1];
            } else {
                suffix = "jpg";
            }
            byte[] bytes = fileService.getFile(fileName + "." + suffix);
            InputStream in = new ByteArrayInputStream(bytes);
            BufferedImage bi = ImageIO.read(in);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bi, suffix, out);
        } catch (IOException e) {
            log.error("show image failed", e);
        }
    }
}
