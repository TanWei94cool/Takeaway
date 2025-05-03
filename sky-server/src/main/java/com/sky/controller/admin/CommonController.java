package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {

    /**
     * 文件上传
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}", file);

        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //  生成随机文件名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 拼接文件名
        String fileName = UUID.randomUUID().toString() + suffix;
        try {
            // 文件保存目录
            String basePath = "D:/Project/IDEA_PROJECT/sky-take-out/sky-server/src/main/resources/static/img/";
            // 创建文件对象
            File dir = new File(basePath);
            // 判断文件目录是否存在
            if (!dir.exists()){
                dir.mkdirs();
            }
            // 文件上传
            File name = new File(basePath + fileName);
            file.transferTo(name);
            return Result.success(name.toString());
        } catch (Exception e) {
            log.error("文件上传失败");
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);

    }
}
