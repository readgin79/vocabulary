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
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求


			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				
				/***************************2.開始查詢資料*****************************************/
				VocabularyService vocSvc = new VocabularyService();
				List allVocabulary = vocSvc.getAll();
				int n = (int)Math.floor((Math.random()*allVocabulary.size()));

				VocabularyVO vocabularyVO = (VocabularyVO) allVocabulary.get(n);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
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

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.getMessage();
			}
		}
		
		if ("isCorrect".equals(action)) { // 來自select_page.jsp的請求


			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String answer = req.getParameter("answer");
				String trueAns = req.getParameter("trueAns");
				
				/***************************2.開始查詢資料*****************************************/

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				JSONObject obj = new JSONObject();
System.out.println("answer=" + answer);
System.out.println("trueAns=" + trueAns);
				if(answer.equals(trueAns)){
					obj.put("trueOrFalse", "恭喜答對！");
				}else{
					obj.put("trueOrFalse", "喔喔~再試一次！");
				}

				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(obj.toString());
				out.flush();
				out.close();

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.getMessage();
			}
		}
		
	}
}
