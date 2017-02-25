package com.vocabulary.model;

import java.util.List;

public class VocabularyService {
	private VocabularyDAO_interface dao;

	public VocabularyService() {
		dao = new VocabularyDAO();
	}

	public VocabularyVO addVocabulary(String voc_name, String voc_translate, String voc_desc,
			String voc_sentence, byte[] voc_pic, String cate_no) {

		VocabularyVO vocabularyVO = new VocabularyVO();

		vocabularyVO.setVoc_name(voc_name);
		vocabularyVO.setVoc_translate(voc_translate);
		vocabularyVO.setVoc_desc(voc_desc);
		vocabularyVO.setVoc_sentence(voc_sentence);
		vocabularyVO.setVoc_pic(voc_pic);
		vocabularyVO.setCate_no(cate_no);
		dao.insert(vocabularyVO);

		return vocabularyVO;
	}

	public VocabularyVO updateVocabulary(String voc_no, String voc_name, String voc_translate, String voc_desc,
			String voc_sentence, byte[] voc_pic, String cate_no) {

		VocabularyVO vocabularyVO = new VocabularyVO();

		vocabularyVO.setVoc_no(voc_no);
		vocabularyVO.setVoc_name(voc_name);
		vocabularyVO.setVoc_translate(voc_translate);
		vocabularyVO.setVoc_desc(voc_desc);
		vocabularyVO.setVoc_sentence(voc_sentence);
		vocabularyVO.setVoc_pic(voc_pic);
		vocabularyVO.setCate_no(cate_no);
		dao.update(vocabularyVO);

		return vocabularyVO;
	}

	public void deleteVocabulary(String voc_no) {
		dao.delete(voc_no);
	}

	public VocabularyVO getOneVocabulary(String voc_no) {
		return dao.findByPrimaryKey(voc_no);
	}

	public List<VocabularyVO> getAll() {
		return dao.getAll();
	}
	
	public List<VocabularyVO> getAllByCate(String cate_no) {
		return dao.getAllByCate(cate_no);
	}
}
