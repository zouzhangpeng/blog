package com.blog.biz.service.crud.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import com.blog.biz.mapper.PostContentMapper;
import com.blog.biz.model.entity.PostContentEntity;
import com.blog.biz.service.crud.PostContentCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class PostContentCrudServiceImpl extends BaseCrudServiceImpl<PostContentMapper, PostContentEntity> implements PostContentCrudService {

    @Override
    public void updateContentByPostId(Long postId, String content) {
        LambdaUpdateWrapper<PostContentEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(PostContentEntity::getContent, content).eq(PostContentEntity::getPostId, postId);
        baseMapper.update(updateWrapper);
    }

    @Override
    public Long saveContent(Long postId, String content) {
        PostContentEntity postContentEntity = new PostContentEntity();
        postContentEntity.setPostId(postId)
                .setContent(content);
        baseMapper.insert(postContentEntity);
        return postContentEntity.getPostContentId();
    }
}
