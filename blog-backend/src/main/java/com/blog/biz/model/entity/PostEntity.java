package com.blog.biz.model.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import com.blog.biz.enums.PostSource;
import com.blog.biz.enums.PostStatus;
import com.blog.common.base.entity.BaseEntity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("t_post")
public class PostEntity extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long postId;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 摘要
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String summary;

    /**
     * 封面图片链接
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String coverPictureUrl;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 状态
     */
    private PostStatus status;

    /**
     * 文章来源
     */
    private PostSource source;

    /**
     * 所属分类Id
     */
    private Long categoryId;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 是否开启评论
     */
    private Boolean enableComment;

    /**
     * 是否加密访问
     */
    private Boolean encrypt;

    /**
     * 访问密码
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String password;

}
