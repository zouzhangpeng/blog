package com.blog.biz.controller.admin;

import com.blog.biz.model.request.SearchPostRequest;
import com.blog.biz.model.request.UpdateCoverPictureRequest;
import com.blog.biz.model.response.PostResponse;
import com.blog.biz.service.manager.BlogSyncService;
import com.blog.biz.service.manager.PostManagerService;
import com.blog.common.base.response.PageResponse;
import com.blog.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Tag(name = "文章管理")
@RestController
@RequestMapping("/admin/posts")
public class PostController {

    private final PostManagerService postManagerService;

    private final BlogSyncService blogSyncService;

    @Operation(summary = "查询文章列表")
    @GetMapping
    public Result<PageResponse<PostResponse>> adminSearch(@ParameterObject @Validated SearchPostRequest request) {
        return Result.success(postManagerService.adminSearch(request));
    }

    @Operation(summary = "查询文章内容")
    @GetMapping("/{postId}/content")
    public Result<String> getPostContent(@Parameter(description = "文章Id") @PathVariable Long postId) {
        return Result.success(postManagerService.getContent(postId));
    }

    @Operation(summary = "发布文章")
    @PatchMapping("/{postId}/publish")
    public Result<Void> publish(@Parameter(description = "文章Id") @PathVariable Long postId) {
        postManagerService.publish(postId);
        return Result.success();
    }

    @Operation(summary = "下架文章")
    @PatchMapping("/{postId}/remove")
    public Result<Void> remove(@Parameter(description = "文章Id") @PathVariable Long postId) {
        postManagerService.remove(postId);
        return Result.success();
    }

    @Operation(summary = "设置封面图片")
    @PatchMapping("/{postId}/updateCoverPicture")
    public Result<Void> updateCoverPicture(@Parameter(description = "文章Id") @PathVariable Long postId,
                                           @RequestBody UpdateCoverPictureRequest request) {
        postManagerService.updateCoverPicture(postId, request.getCoverPictureUrl());
        return Result.success();
    }

    @Operation(summary = "同步文章")
    @GetMapping("/sync")
    public Result<Void> sync() {
        blogSyncService.sync();
        return Result.success();
    }
}
