package com.blog.biz.service.crud.impl;

import com.blog.biz.model.context.PagePostContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.blog.biz.mapper.PostMapper;
import com.blog.biz.model.entity.PostEntity;
import com.blog.biz.service.crud.PostCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@Service
public class PostCrudServiceImpl extends BaseCrudServiceImpl<PostMapper, PostEntity> implements PostCrudService {

    @Override
    public IPage<PostEntity> page(PagePostContext context) {
        LambdaQueryWrapper<PostEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(context.getTitle()), PostEntity::getTitle, context.getTitle())
            .eq(context.getStatus() != null, PostEntity::getStatus, context.getStatus())
            .eq(context.getSource() != null, PostEntity::getSource, context.getSource())
            .eq(context.getCategoryId() != null, PostEntity::getCategoryId, context.getCategoryId())
            .ge(context.getPublishStartTime() != null, PostEntity::getPublishTime, context.getPublishStartTime())
            .le(context.getPublishEndTime() != null, PostEntity::getPublishTime, context.getPublishEndTime())
            .ge(context.getCreateStartTime() != null, PostEntity::getCreateTime, context.getCreateStartTime())
            .le(context.getCreateEndTime() != null, PostEntity::getCreateTime, context.getCreateEndTime())
            .isNotNull(context.getEncrypt() != null && context.getEncrypt(), PostEntity::getPassword)
            .eq(context.getTop() != null, PostEntity::getTop, context.getTop())
            .eq(context.getEnableComment() != null, PostEntity::getEnableComment, context.getEnableComment())
            .orderByDesc(PostEntity::getCreateTime);
        return baseMapper.selectPage(context.getPageable(), queryWrapper);
    }

    @Override
    public boolean categoryUsed(Long categoryId) {
        LambdaQueryWrapper<PostEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PostEntity::getCategoryId, categoryId);
        return baseMapper.selectCount(queryWrapper) > 0;
    }

}
