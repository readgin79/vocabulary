package com.vocabulary.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vocabulary.model.VocabularyService;
import com.vocabulary.model.VocabularyVO;

import net.sf.json.JSONObject;

public class VocabularyServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD


			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				
				
				/***************************2.�}�l�d�߸��*****************************************/
				VocabularyService vocSvc = new VocabularyService();
				List allVocabulary = vocSvc.getAll();
				int n = (int)Math.floor((Math.random()*allVocabulary.size()));

				VocabularyVO vocabularyVO = (VocabularyVO) allVocabulary.get(n);
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				JSONObject obj = new JSONObject();
				obj.put("voc_name", vocabularyVO.getVoc_name());
				obj.put("voc_translate", vocabularyVO.getVoc_translate());
				obj.put("voc_desc", vocabularyVO.getVoc_desc());
				obj.put("voc_sentence", vocabularyVO.getVoc_sentence());
System.out.println(n + ":" + vocabularyVO.getVoc_name());	
System.out.println(obj);	
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(obj.toString());
				out.flush();
				out.close();

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.getMessage();
			}
		}
		
		if ("isCorrect".equals(action)) { // �Ӧ�select_page.jsp���ШD


			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String answer = req.getParameter("answer");
				String trueAns = req.getParameter("trueAns");
				
				/***************************2.�}�l�d�߸��*****************************************/

				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				JSONObject obj = new JSONObject();
System.out.println("answer=" + answer);
System.out.println("trueAns=" + trueAns);
				if(answer.equals(trueAns)){
					obj.put("trueOrFalse", "���ߵ���I");
				}else{
					obj.put("trueOrFalse", "���~�A�դ@���I");
				}

				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(obj.toString());
				out.flush();
				out.close();

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.getMessage();
			}
		}
		
	}
}
