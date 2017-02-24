package com.category.model;

import java.util.List;

public class CategoryService {

	private CategoryDAO_interface dao;

	public CategoryService() {
		dao = new CategoryDAO();
	}

	public CategoryVO addCategory(String cate_name) {

		CategoryVO categoryVO = new CategoryVO();

		categoryVO.setCate_name(cate_name);
		dao.insert(categoryVO);

		return categoryVO;
	}

	public CategoryVO updateCategory(String cate_no, String cate_name) {

		CategoryVO categoryVO = new CategoryVO();

		categoryVO.setCate_no(cate_no);
		categoryVO.setCate_name(cate_name);
		dao.update(categoryVO);

		return categoryVO;
	}

	public void deleteCategory(String cate_no) {
		dao.delete(cate_no);
	}

	public CategoryVO getOneCategory(String cate_no) {
		return dao.findByPrimaryKey(cate_no);
	}

	public List<CategoryVO> getAll() {
		return dao.getAll();
	}
}
