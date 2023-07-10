package com.queshen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queshen.mapper.TCommentMapper;
import com.queshen.pojo.bo.Result;
import com.queshen.pojo.dto.CommentInfoDTO;
import com.queshen.pojo.dto.CommentLikeDTO;
import com.queshen.pojo.po.Comment;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author winston
 * @create 2023/4/20 20:38
 * @Description: Man can conquer nature
 * 评论系统的接口
 **/
@Log4j2
@RequestMapping("/comment")
@RestController
public class CommentController {

    @Autowired
    private TCommentMapper commentMapper;

    // 获取评论
    List<Comment> commentList = new ArrayList<>(); // 最终展现给前端的数据集

    @PostMapping("/getComment")
    public Result getStoreCommentList(@RequestParam() String storeId,
                                      @RequestParam() Integer roomId){
        // 先查出头结点，也就是parentId为-1的数据
        QueryWrapper<Comment> queryWrapper = new QueryWrapper();
        queryWrapper.eq("room_id", roomId);
        queryWrapper.eq("top_comment_id", -1);
        DFS(commentMapper.selectList(queryWrapper));
        System.out.println(commentList);    // 将前端的数据打印出来
        return Result.ok(commentList);
    }
    // 深度优先遍历

    public void DFS(List<Comment> children) {
        int index = -1;
        for (Comment comment : children) {
            // 查出该节点的子节点
            List<Comment> childrenList = commentMapper.getChildren(comment.getId());
            index = index + 1;
            if (childrenList.size() == 0){
                continue;
            }
            // 设置当前节点的子节点数据
            children.get(index).setChildren(childrenList);
            DFS(childrenList);
        }
        // 将最终的数据放回tests中
        commentList = children;
    }

    /**
     * 发布评论
     * @param commentInfo
     * @return
     */
    @PostMapping("/publishComment")
    public Result publishComment(@RequestBody CommentInfoDTO commentInfo){

        return Result.ok();
    }

    // 删除用户自己的评论
    @DeleteMapping("/deleteUserComment")
    public Result deleteUserComment(@RequestParam("commentId") String commentId, @RequestParam(required = false) Integer secondIndex) {

        return Result.ok();
    }

    // 判断这条评论是否为自己的
    @GetMapping("/judgeCommentItself")
    public Result judgeCommentItself(@RequestParam("commentId") String commentId, @RequestParam(required = false) Integer secondIndex) {

        return Result.ok();
    }

    // 取消点赞
    @DeleteMapping("/comment/like")
    public Result subLikeToComment(@RequestParam String parenId,
                              @RequestParam(required = false) String childId) {
        return Result.ok();
    }

    //点赞评论
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/comment/like")
    public Result addLikeToComment(@RequestBody CommentLikeDTO dto) {
        return Result.ok();
    }

    // 获取被回复的评论列表
    @GetMapping("/commentList")
    public Result getCommentList() {
        return Result.ok();
    }

    // 获取某个房间下的评论
    @GetMapping("/pageComment")
    public Result getCommentPage(@RequestParam String roomId,
                            @RequestParam int page,
                            @RequestParam int size){
        return Result.ok();
    }

    // 获取门店自己在某个房间下的点赞列表
    @GetMapping("/commentLike")
    public Result getSelfCommentLikeList(@RequestParam("storeId") String storeId){
        return Result.ok();
    }

    // 把评论标为已读
    @PostMapping("/readComment")
    public Result readComment(@RequestBody Integer commentId) {
        return Result.ok();
    }

    // 把消息标为已读
    @PostMapping("/readMessage")
    public Result readMessage(@RequestBody Integer messageId) {
        // todo 用户会话表里未读消息按实际减少，消息表里的对应消息标记为已读
        return Result.ok();
    }

    // 获取消息列表
    @GetMapping("/conversationList")
    public Result getConversationList(@RequestParam Integer type){
        return Result.ok();
    }

    // 门店管理人获取未读消息总数
    @GetMapping("/shopUnreadCount")
    public Result getUnreadCount(@RequestParam Integer storeId){
        return Result.ok();
    }

    // 获取历史聊天记录
    @GetMapping("/history")
    public Result getHistoryMsg(
                           @RequestParam(required = false) String conversationId,
                           @RequestParam(required = false) String otherId,
                           @RequestParam int page,
                           @RequestParam int size){
        return Result.ok();
    }

}
