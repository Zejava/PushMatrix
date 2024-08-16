package org.example.pushMatrix.controller;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.pushMatrix.common.constant.FunctionConstant;
import org.example.pushMatrix.common.constant.PushMatrixConstant;
import org.example.pushMatrix.common.enums.RespStatusEnum;
import org.example.pushMatrix.exception.CommonException;
import org.example.pushMatrix.service.ChannelAccountService;
import org.example.pushMatrix.support.domain.ChannelAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 泽
 * @Date 2024/8/16 8:32
 * 渠道账号管理接口
 */
@RestController
@RequestMapping("/account")
@Tag(name = "渠道账号管理接口")
public class ChannelAccountController {
    @Autowired
    private ChannelAccountService channelAccountService;
    /**
     * 如果Id存在，则修改
     * 如果Id不存在，则保存
     */
    @PostMapping("/save")
    @Operation(summary = "/保存数据")
    public ChannelAccount saveOrUpdate(@RequestBody ChannelAccount channelAccount) {
//        if (CharSequenceUtil.isBlank(channelAccount.getCreator())) {
//            throw new CommonException(RespStatusEnum.IS_CREATOR_NULL.getMsg());
//        }
        channelAccount.setCreator(CharSequenceUtil.isBlank(channelAccount.getCreator()) ? PushMatrixConstant.DEFAULT_CREATOR : channelAccount.getCreator());

        return channelAccountService.save(channelAccount);
    }

    /**
     * 根据渠道标识查询渠道账号相关的信息
     */
    @GetMapping("/queryByChannelType")
    @Operation(summary = "/根据渠道标识查询相关的记录")
    public List<ChannelAccount> query(Integer channelType, String creator) {
//        if ( CharSequenceUtil.isBlank(creator)) {
//            throw new CommonException(RespStatusEnum.IS_CREATOR_NULL.getMsg());
//        }
        creator = CharSequenceUtil.isBlank(creator) ? PushMatrixConstant.DEFAULT_CREATOR : creator;
        return channelAccountService.queryByChannelType(channelType, creator);
    }

    /**
     * 所有的渠道账号信息
     */
    @GetMapping("/list")
    @Operation(summary = "/渠道账号列表信息")
    public List<ChannelAccount> list(String creator) {
//        if (CharSequenceUtil.isBlank(creator)) {
//            throw new CommonException(RespStatusEnum.IS_CREATOR_NULL.getMsg());
//        }
        creator = CharSequenceUtil.isBlank(creator) ? PushMatrixConstant.DEFAULT_CREATOR : creator;

        return channelAccountService.list(creator);
    }

    /**
     * 根据Id删除
     * id多个用逗号分隔开
     */
    @DeleteMapping("delete/{id}")
    @Operation(summary = "/根据Ids删除")
    public void deleteByIds(@PathVariable("id") String id) {
        if (CharSequenceUtil.isNotBlank(id)) {
            List<Long> idList = Arrays.stream(id.split(StrPool.COMMA)).map(Long::valueOf).collect(Collectors.toList());
            channelAccountService.deleteByIds(idList);
        }
    }
}
