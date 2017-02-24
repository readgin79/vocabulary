package com.category.model;

import java.util.List;
import java.util.Set;

import com.vocabulary.model.VocabularyVO;

public interface CategoryDAO_interface {
    public void insert(CategoryVO categoryVO);
    public void update(CategoryVO categoryVO);
    public void delete(String cate_no);
    public CategoryVO findByPrimaryKey(String cate_no);
    public List<CategoryVO> getAll();
      //�d�߬Y���������u(�@��h)(�^�� Set)
    public Set<VocabularyVO> getVocsByCateno(String cate_no);
}
