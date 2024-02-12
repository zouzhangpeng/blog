package com.blog.biz.convert;

import com.blog.biz.model.request.CreateCategoryRequest;
import com.blog.biz.model.response.TreeCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.blog.biz.model.entity.CategoryEntity;

/**
 * @author zouzhangpeng
 * @desc
 */
@Mapper
public interface CategoryConverter {

    CategoryConverter INSTANCE = Mappers.getMapper(CategoryConverter.class);

    CategoryEntity toEntity(CreateCategoryRequest request);

    TreeCategoryResponse toResponse(CategoryEntity entity);
}
