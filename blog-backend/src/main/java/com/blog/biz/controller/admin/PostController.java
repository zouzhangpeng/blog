package com.blog.biz.controller.admin;

import com.blog.biz.model.request.CreatePostRequest;
import com.blog.biz.model.request.PagePostRequest;
import com.blog.biz.model.request.UpdatePostRequest;
import com.blog.biz.model.response.CreatePostResponse;
import com.blog.biz.model.response.PagePostResponse;
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
@RequestMapping("/admin/post")
public class PostController {

    private final PostManagerService postManagerService;

    @Operation(summary = "新增文章")
    @PostMapping
    public Result<CreatePostResponse> create(@Validated @RequestBody CreatePostRequest request) {
        return Result.success(postManagerService.create(request));
    }

    @Operation(summary = "查询文章列表")
    @GetMapping
    public Result<PageResponse<PagePostResponse>> page(@ParameterObject @Validated PagePostRequest request) {
        return Result.success(postManagerService.page(request));
    }

    @Operation(summary = "更新文章")
    @PutMapping("/{postId}")
    public Result<Void> update(@Parameter(description = "文章Id") @PathVariable Long postId,
        @Validated @RequestBody UpdatePostRequest request) {
        postManagerService.update(postId, request);
        return Result.success();
    }

    @Operation(summary = "删除文章")
    @DeleteMapping("/{postId}")
    public Result<Void> delete(@Parameter(description = "文章Id") @PathVariable Long postId) {
        postManagerService.delete(postId);
        return Result.success();
    }

    @Operation(summary = "移到回收站")
    @PutMapping("/{postId}/moveRecycleBin")
    public Result<Void> moveRecycleBin(@Parameter(description = "文章Id") @PathVariable Long postId) {
        postManagerService.moveRecycleBin(postId);
        return Result.success();
    }

    @Operation(summary = "发布文章")
    @PutMapping("/{postId}/publish")
    public Result<Void> publish(@Parameter(description = "文章Id") @PathVariable Long postId) {
        postManagerService.publish(postId);
        return Result.success();
    }

    @Operation(summary = "取消发布文章")
    @PutMapping("/{postId}/unpublished")
    public Result<Void> unpublished(@Parameter(description = "文章Id") @PathVariable Long postId) {
        postManagerService.unpublished(postId);
        return Result.success();
    }
}
