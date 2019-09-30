package com.frame.fast.cms.user;

import com.frame.fast.model.MonthProduct;
import com.frame.fast.model.PersonInfo;
import com.frame.fast.model.ProductSort;
import com.frame.fast.model.SingleProduct;
import com.frame.fast.service.custom.IMonthProductService;
import com.frame.fast.service.custom.ISingleProductService;
import com.frame.fast.service.person.PersonInfoService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/user")
@Controller
public class CmsUserController {

    private Gson gson = new Gson();
    @Resource
    private PersonInfoService personInfoService;
    @Resource
    private IMonthProductService monthProductService;
    @Resource
    private ISingleProductService singleProductService;
    @GetMapping("/toList")
    public String toUserList(){
        return "user_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public String list(){
        List<PersonInfo> list = personInfoService.list();
        return gson.toJson(UserInfoVo.tranFromUser(list));
    }

    @GetMapping("/toMonthInfoList")
    public String toMonthInfoList(){
        return "month_list";
    }
    @GetMapping("/month/list")
    @ResponseBody
    public String getMonthInfolist(Model model,Long custId){
        List<MonthProduct> list = monthProductService.list(custId);
        return gson.toJson(MonthProductVo.transFromOrigin(list));
    }

    @GetMapping("/toSingleInfoList")
    public String toSingleInfoList(){
        return "single_list";
    }

    @GetMapping("/single/list")
    @ResponseBody
    public String getSingleInfolist(){
        List<SingleProduct> list = singleProductService.list();
        return gson.toJson(SingleProductVo.transFromOrigin(list));
    }
}
