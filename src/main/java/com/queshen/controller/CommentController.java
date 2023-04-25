package com.queshen.controller;

import com.queshen.pojo.bo.Result;
import com.queshen.pojo.dto.ReceiptCodeDTO;
import com.queshen.pojo.po.Comment;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author winston
 * @create 2023/4/20 20:38
 * @Description: Man can conquer nature
 **/
@Log4j2
@RequestMapping("/comment")
@RestController
public class CommentController {

    @PostMapping("/getComment")
    public Result getStoreCommentList(@RequestBody Comment receiptCode){

        return Result.ok();
    }

    @PostMapping("/addComment")
    public Result addComment(@RequestBody ReceiptCodeDTO receiptCode){

        return Result.ok();
    }

    @PostMapping("/deleteComment")
    public Result deleteComment(@RequestBody Comment receiptCode){

        return Result.ok();
    }

}
