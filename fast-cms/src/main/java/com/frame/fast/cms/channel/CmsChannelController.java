package com.frame.fast.cms.channel;


import com.frame.fast.model.Channel;
import com.frame.fast.model.ResponseVo;
import com.frame.fast.service.channel.IChannelService;
import com.frame.fast.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/channel")
public class CmsChannelController {

    @Resource
    private IChannelService channelService;

    @GetMapping("/toList")
    public String toList(){
        return "channel_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public String list(){
        List<Channel> list = channelService.list();
        return JsonUtils.toJson(list);
    }

    @PostMapping("/doEdit")
    @ResponseBody
    public ResponseVo doEdit(Channel channel){
        channelService.saveOrUpdate(channel);
        return ResponseVo.successVo();
    }
}
