package com.blog.biz.service.manager.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.blog.biz.constant.BizConstant;
import com.blog.biz.convert.CategoryConverter;
import com.blog.biz.model.entity.CategoryEntity;
import com.blog.biz.model.request.CreateCategoryRequest;
import com.blog.biz.model.request.UpdateCategoryRequest;
import com.blog.biz.model.response.CategoryResponse;
import com.blog.biz.model.response.CreateCategoryResponse;
import com.blog.biz.service.crud.CategoryCrudService;
import com.blog.biz.service.manager.CategoryManagerService;
import com.blog.common.base.response.NodeResponse;
import com.blog.common.exception.BusinessException;
import com.blog.common.util.TreeUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryManagerServiceImpl implements CategoryManagerService {

    private final CategoryCrudService categoryCrudService;

    @Transactional(rollbackOn = RuntimeException.class)
    @Override
    public CreateCategoryResponse create(CreateCategoryRequest request) {
        // 校验名称重复
        CategoryEntity existsEntity = categoryCrudService.findByCategoryName(request.getCategoryName()).orElse(null);
        if (Objects.nonNull(existsEntity)) {
            throw new BusinessException("分类名称已存在");
        }

        CategoryEntity entity = CategoryConverter.INSTANCE.toEntity(request);

        // 上级分类
        CategoryEntity parentEntity = null;
        if (Objects.nonNull(request.getParentId())) {
            parentEntity = categoryCrudService.findOneById(request.getParentId())
                .orElseThrow(() -> new BusinessException("上级分类信息不存在"));
            entity.setParentId(parentEntity.getCategoryId());
            entity.setLevel(parentEntity.getLevel() + 1);
        } else {
            entity.setParentId(BizConstant.ROOT_ID);
            entity.setLevel(BizConstant.FIRST_LEVEL);
        }

        // 查询同一层级下最新的分类
        CategoryEntity latestEntity = categoryCrudService.findLatest(entity.getParentId()).orElse(null);
        // 设置顺序号
        entity.setOrderNo(Objects.nonNull(latestEntity) ? latestEntity.getOrderNo() + 1 : 1);

        categoryCrudService.save(entity);

        // todo: 如何在新增时就设置fullId？
        entity.setFullId(Objects.nonNull(parentEntity) ? parentEntity.getFullId() + "-" + entity.getCategoryId()
            : String.valueOf(entity.getCategoryId()));
        categoryCrudService.save(entity);
        return new CreateCategoryResponse(entity.getCategoryId());
    }

    @Override
    public List<NodeResponse<CategoryResponse>> tree() {
        List<CategoryResponse> data = categoryCrudService.findAll().stream().map(CategoryConverter.INSTANCE::toResponse)
            .collect(Collectors.toList());
        return TreeUtil.build(data, BizConstant.ROOT_ID, CategoryResponse::getCategoryId,
            CategoryResponse::getCategoryName, CategoryResponse::getParentId);
    }

    @Override
    public void update(Long categoryId, UpdateCategoryRequest request) {
        CategoryEntity entity = categoryCrudService.findOneByIdOrThrow(categoryId);
        if (!StringUtils.equals(request.getCategoryName(), entity.getCategoryName())) {
            // 校验名称重复
            CategoryEntity existsEntity =
                categoryCrudService.findByCategoryName(request.getCategoryName()).orElse(null);
            if (Objects.nonNull(existsEntity)) {
                throw new BusinessException("分类名称已存在");
            }
        }
        entity.setCategoryName(request.getCategoryName());
        categoryCrudService.save(entity);
    }

}