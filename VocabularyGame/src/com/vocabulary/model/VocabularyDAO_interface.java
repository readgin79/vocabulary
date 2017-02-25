package com.vocabulary.model;

import java.util.List;

public interface VocabularyDAO_interface {
	public void insert(VocabularyVO vocabularyVO);
    public void update(VocabularyVO vocabularyVO);
    public void delete(String voc_no);
    public VocabularyVO findByPrimaryKey(String voc_no);
    public List<VocabularyVO> getAll();
    public List<VocabularyVO> getAllByCate(String cate_no);
}
