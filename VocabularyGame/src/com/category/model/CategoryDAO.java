package com.category.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import com.vocabulary.model.VocabularyVO;


public class CategoryDAO implements CategoryDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO category (cate_no,cate_name) VALUES (category_seq.NEXTVAL, ?)";
	private static final String GET_ALL_STMT = "SELECT cate_no , cate_name FROM category";
	private static final String GET_ONE_STMT = "SELECT cate_no , cate_name FROM category where cate_no = ?";
	private static final String GET_Vocs_ByCateno_STMT = "SELECT voc_no,voc_name,voc_translate,voc_desc,voc_sentence,voc_pic,cate_no FROM vocabulary where cate_no = ? order by voc_no";
	private static final String DELETE_VOCS = "DELETE FROM vocabulary where category = ?";
	private static final String DELETE_CATE = "DELETE FROM category where cate_no = ?";	
	private static final String UPDATE = "UPDATE category set cate_name=? where cate_no = ?";

	@Override
	public void insert(CategoryVO categoryVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, categoryVO.getCate_name());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void update(CategoryVO categoryVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, categoryVO.getCate_name());
			pstmt.setString(2, categoryVO.getCate_no());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		
	}

	@Override
	public void delete(String cate_no) {
		int updateCount_VOCs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先刪除員工
			pstmt = con.prepareStatement(DELETE_VOCS);
			pstmt.setString(1, cate_no);
			updateCount_VOCs = pstmt.executeUpdate();
			// 再刪除部門
			pstmt = con.prepareStatement(DELETE_CATE);
			pstmt.setString(1, cate_no);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除分類編號" + cate_no + "時,共有單字" + updateCount_VOCs
					+ "個同時被刪除");
			
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public CategoryVO findByPrimaryKey(String cate_no) {
		CategoryVO categoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, cate_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				categoryVO = new CategoryVO();
				categoryVO.setCate_no(rs.getString("cate_no"));
				categoryVO.setCate_name(rs.getString("cate_name"));
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return categoryVO;
	}

	@Override
	public List<CategoryVO> getAll() {
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		CategoryVO categoryVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				categoryVO = new CategoryVO();
				categoryVO.setCate_no(rs.getString("cate_no"));
				categoryVO.setCate_name(rs.getString("cate_name"));
				list.add(categoryVO); // Store the row in the list
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public Set<VocabularyVO> getVocsByCateno(String cate_no) {
		Set<VocabularyVO> set = new LinkedHashSet<VocabularyVO>();
		VocabularyVO vocabularyVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Vocs_ByCateno_STMT);
			pstmt.setString(1, cate_no);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				vocabularyVO = new VocabularyVO();
				vocabularyVO.setVoc_no(rs.getString("voc_no"));
				vocabularyVO.setVoc_name(rs.getString("voc_name"));
				vocabularyVO.setVoc_translate(rs.getString("voc_translate"));
				vocabularyVO.setVoc_desc(rs.getString("voc_desc"));
				vocabularyVO.setVoc_sentence(rs.getString("voc_sentence"));
				vocabularyVO.setVoc_pic(rs.getBytes("voc_pic"));
				vocabularyVO.setCate_no(rs.getString("cate_no"));
				set.add(vocabularyVO); // Store the row in the vector
			}
	
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return set;
	}

}
