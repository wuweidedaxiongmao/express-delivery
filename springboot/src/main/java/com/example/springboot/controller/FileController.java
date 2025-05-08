package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.springboot.common.Result;
import com.example.springboot.exception.CustomException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {
	//获取当前项目根路径
	private static final String filePath=System.getProperty("user.dir")+"/files/";
	//存储student id card的路径
	private static final String stuCardPath=System.getProperty("user.dir")+"/files/stu-cards/";

	@PostMapping("/upload")
	public Result upload(MultipartFile file){ //文件流的形式
		//获取文件名
		final String originalFilename = file.getOriginalFilename();
		if(!FileUtil.isDirectory(filePath)){
			FileUtil.mkdir(filePath);
		}
		//提供文件完整的路径，给文件名加一个唯一的标识
		String fileName=System.currentTimeMillis()+"-"+originalFilename;
		String realPath=filePath+fileName; //完整的文件上传路径
		try {
			//file.getInputStream().transferTo(realPath);
			FileUtil.writeBytes(file.getBytes(),realPath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("500","文件上传失败");
		}

		//返回一个网络路径
		String url = "http://localhost:9091/files/download/"+fileName;
		return Result.success(url);

	}

	@GetMapping("/download/{filename}")
	public void download(@PathVariable String filename, HttpServletResponse response){
		try {
			//设置编码等格式
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
			response.setContentType("application/octet-stream");
			final ServletOutputStream outputStream = response.getOutputStream();
			String realPath=filePath+filename;
			final byte[] bytes = FileUtil.readBytes(realPath);
			outputStream.write(bytes);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("500","文件下载失败");
		}
	}

	@PostMapping("/uploadStuCard")
	public Result uploadStuCard(MultipartFile file) {
		final String originalFilename = file.getOriginalFilename();
		if (!FileUtil.isDirectory(stuCardPath)) {
			FileUtil.mkdir(stuCardPath);
		}
		String fileName = System.currentTimeMillis() + "-" + originalFilename;
		String realPath = stuCardPath + fileName;
		try {
			FileUtil.writeBytes(file.getBytes(), realPath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("500", "学生证上传失败");
		}
		String url = "http://localhost:9091/files/downloadStuCard/"+fileName;
		return Result.success(url);
	}
	@GetMapping("/downloadStuCard/{filename}")
	public void downloadStuCard(@PathVariable String filename, HttpServletResponse response){
		try {
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
			response.setContentType("application/octet-stream");
			final ServletOutputStream outputStream = response.getOutputStream();
			String realPath=stuCardPath+filename;
			final byte[] bytes = FileUtil.readBytes(realPath);
			outputStream.write(bytes);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("500","学生证下载失败");
		}
	}

	@PostMapping("/wang/upload")
	public Map<String,Object> wangEditorUpload(MultipartFile file){
		//获取文件名
		final String originalFilename = file.getOriginalFilename();
		if(!FileUtil.isDirectory(filePath)){
			FileUtil.mkdir(filePath);
		}
		//提供文件完整的路径，给文件名加一个唯一的标识
		String fileName=System.currentTimeMillis()+"-"+originalFilename;
		String realPath=filePath+fileName; //完整的文件上传路径
		try {
			//file.getInputStream().transferTo(realPath);
			FileUtil.writeBytes(file.getBytes(),realPath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("500","文件上传失败");
		}

		//返回一个网络路径
		String url = "http://localhost:9091/files/download/"+fileName;
		//wangEditor上传图片成功后，返回的参数
		HashMap<String, Object> resMap = new HashMap<>();
		List<Map<String,Object>> list=new ArrayList<>();
		Map<String,Object> urlMap=new HashMap<>();
		urlMap.put("url",url);
		list.add(urlMap);
		resMap.put("errno",0);
		resMap.put("data",list);
		return resMap;
	}
}
