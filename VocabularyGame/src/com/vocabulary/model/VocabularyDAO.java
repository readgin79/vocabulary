package com.vocabulary.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class VocabularyDAO implements VocabularyDAO_interface{
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		private static final String INSERT_STMT = 
			"INSERT INTO vocabulary (voc_no,voc_name,voc_translate,voc_desc,voc_sentence,voc_pic,cate_no) VALUES (vocabulary_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT voc_no,voc_name,voc_translate,voc_desc,voc_sentence,voc_pic,cate_no FROM vocabulary order by voc_no";
		private static final String GET_ONE_STMT = 
			"SELECT voc_no,voc_name,voc_translate,voc_desc,voc_sentence,voc_pic,cate_no FROM vocabulary where voc_no = ?";
		private static final String DELETE = 
			"DELETE FROM vocabulary where voc_no = ?";
		private static final String UPDATE = 
			"UPDATE vocabulary set voc_name=?, voc_translate=?, voc_desc=?, voc_sentence=?, voc_pic=?, cate_no=? where voc_no = ?";
		
	@Override
	public void insert(VocabularyVO vocabularyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, vocabularyVO.getVoc_name());
			pstmt.setString(2, vocabularyVO.getVoc_translate());
			pstmt.setString(3, vocabularyVO.getVoc_desc());
			pstmt.setString(4, vocabularyVO.getVoc_sentence());
			pstmt.setBytes(5, vocabularyVO.getVoc_pic());
			pstmt.setString(6, vocabularyVO.getCate_no());

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
	public void update(VocabularyVO vocabularyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, vocabularyVO.getVoc_name());
			pstmt.setString(2, vocabularyVO.getVoc_translate());
			pstmt.setString(3, vocabularyVO.getVoc_desc());
			pstmt.setString(4, vocabularyVO.getVoc_sentence());
			pstmt.setBytes(5, vocabularyVO.getVoc_pic());
			pstmt.setString(6, vocabularyVO.getCate_no());
			pstmt.setString(7, vocabularyVO.getVoc_no());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String voc_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, voc_no);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public VocabularyVO findByPrimaryKey(String voc_no) {
		VocabularyVO vocabularyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, voc_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				vocabularyVO = new VocabularyVO();
				vocabularyVO.setVoc_no(rs.getString("voc_no"));
				vocabularyVO.setVoc_name(rs.getString("voc_name"));
				vocabularyVO.setVoc_translate(rs.getString("voc_translate"));
				vocabularyVO.setVoc_desc(rs.getString("voc_desc"));
				vocabularyVO.setVoc_sentence(rs.getString("voc_sentence"));
				vocabularyVO.setVoc_pic(rs.getBytes("voc_pic"));
				vocabularyVO.setCate_no(rs.getString("cate_no"));
			}

			// Handle any driver errors
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
		return vocabularyVO;
	}

	@Override
	public List<VocabularyVO> getAll() {
		List<VocabularyVO> list = new ArrayList<VocabularyVO>();
		VocabularyVO vocabularyVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				vocabularyVO = new VocabularyVO();
				vocabularyVO.setVoc_no(rs.getString("voc_no"));
				vocabularyVO.setVoc_name(rs.getString("voc_name"));
				vocabularyVO.setVoc_translate(rs.getString("voc_translate"));
				vocabularyVO.setVoc_desc(rs.getString("voc_desc"));
				vocabularyVO.setVoc_sentence(rs.getString("voc_sentence"));
				vocabularyVO.setVoc_pic(rs.getBytes("voc_pic"));
				vocabularyVO.setCate_no(rs.getString("cate_no"));
				list.add(vocabularyVO); // Store the row in the list
			}

			// Handle any driver errors
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
		return list;
	}

}
