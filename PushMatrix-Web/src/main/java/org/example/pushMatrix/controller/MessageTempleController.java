package org.example.pushMatrix.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.pushMatrix.common.enums.RespStatusEnum;
import org.example.pushMatrix.exception.CommonException;
import org.example.pushMatrix.serviceapi.domain.MessageParam;
import org.example.pushMatrix.serviceapi.domain.SendRequest;
import org.example.pushMatrix.serviceapi.domain.SendResponse;
import org.example.pushMatrix.serviceapi.enums.BusinessCode;
import org.example.pushMatrix.support.domain.MessageTemplate;
import org.example.pushMatrix.service.MessageTemplateService;
import org.example.pushMatrix.serviceapi.service.SendService;
import org.example.pushMatrix.common.vo.BasicResultVO;
import org.example.pushMatrix.utils.ConvertMap;
import org.example.pushMatrix.vo.MessageTemplateParam;
import org.example.pushMatrix.vo.MessageTemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author 泽
 * @Date 2024/8/12 21:03
 */
@RestController
@Slf4j
@RequestMapping("/messageTemplate")
@Tag(name = "发送消息")
public class MessageTempleController {
    private static final List<String> FLAT_FIELD_NAME = Arrays.asList("msgContent");

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private SendService sendService;

//    @Value("${pushMatrix.upload.crowd.path}")
//    private String dataPath;


    /**
     * 如果Id存在，则修改
     * 如果Id不存在，则保存
     */
    @PostMapping("/save")
    @Operation(summary = "/保存数据")
    public MessageTemplate saveOrUpdate(@RequestBody MessageTemplate messageTemplate) {
        return messageTemplateService.saveOrUpdate(messageTemplate);
    }

    /**
     * 列表数据
     */
    @GetMapping("/list")
    @Operation(summary ="/列表页")
    public List<MessageTemplate> queryList(MessageTemplateParam messageTemplateParam) {

        return  messageTemplateService.queryList(messageTemplateParam);
    }

    /**
     * 根据Id查找
     */
    @GetMapping("query/{id}")
    @Operation(summary ="/根据Id查找" )
    public MessageTemplate queryById(@PathVariable("id") Long id) {
        return messageTemplateService.queryById(id);
    }


    /**
     * 根据Id删除
     * id多个用逗号分隔开
     */
    @DeleteMapping("delete/{id}")
    @Operation(summary = "/根据Ids删除")
    public BasicResultVO deleteByIds(@PathVariable("id") String id) {
        if (StrUtil.isNotBlank(id)) {
            List<Long> idList = Arrays.stream(id.split(StrUtil.COMMA)).map(s -> Long.valueOf(s)).collect(Collectors.toList());
            messageTemplateService.deleteByIds(idList);
            return BasicResultVO.success();
        }
        return BasicResultVO.fail();
    }


    /**
     * 测试发送接口
     */
    @PostMapping("test")
    @Operation(summary = "/测试发送接口")
    public BasicResultVO test(@RequestBody MessageTemplateParam messageTemplateParam) {

        Map<String, String> variables = JSON.parseObject(messageTemplateParam.getMsgContent(), Map.class);
        MessageParam messageParam = MessageParam.builder().receiver(messageTemplateParam.getReceiver()).variables(variables).build();
        SendRequest sendRequest = SendRequest.builder().code(BusinessCode.COMMON_SEND.getCode()).messageTemplateId(messageTemplateParam.getId()).messageParam(messageParam).build();
        SendResponse response = sendService.send(sendRequest);
        if (response.getCode() != RespStatusEnum.SUCCESS.getCode()) {
            return BasicResultVO.fail(response.getMsg());
        }
        return BasicResultVO.success(response);
    }

    /**
     * 获取需要测试的模板占位符
     */
    @PostMapping("test/content")
    @Operation(summary = "/测试发送接口")
    public String test(Long id) {
        MessageTemplate messageTemplate = messageTemplateService.queryById(id);
        return messageTemplate.getMsgContent();
    }
    /**
     * 启动模板的定时任务
     */
    @PostMapping("start/{id}")
    @Operation(summary = "/启动模板的定时任务")
    public BasicResultVO start(@RequestBody @PathVariable("id") Long id) {
        return messageTemplateService.startCronTask(id);
    }

    /**
     * 暂停模板的定时任务
     */
    @PostMapping("stop/{id}")
    @Operation(summary = "/暂停模板的定时任务")
    public BasicResultVO stop(@RequestBody @PathVariable("id") Long id) {
        return messageTemplateService.stopCronTask(id);
    }

    /**
     * 上传人群文件
     */
//    @PostMapping("upload")
//    @Operation(summary = "/上传人群文件")
//    public BasicResultVO upload(@RequestParam("file") MultipartFile file) {
//        String filePath = new StringBuilder(dataPath)
//                .append(IdUtil.fastSimpleUUID())
//                .append(file.getOriginalFilename())
//                .toString();
//        try {
//            File localFile = new File(filePath);
//            if (!localFile.exists()) {
//                localFile.mkdirs();
//            }
//            file.transferTo(localFile);
//
//
//        } catch (Exception e) {
//            log.error("MessageTemplateController#upload fail! e:{},params{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(file));
//            return BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR);
//        }
//        return BasicResultVO.success(MapUtil.of(new String[][]{{"value", filePath}}));
//    }

}
