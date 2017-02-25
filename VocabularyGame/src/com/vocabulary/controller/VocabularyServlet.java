package com.vocabulary.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
				HttpSession session = req.getSession();
				
				/***************************2.�}�l�d�߸��*****************************************/
				VocabularyService vocSvc = new VocabularyService();
				List allVocabulary;
				int playNumber;
System.out.println("xxxxx");
System.out.println(req.getParameter("category"));
				if(session.getAttribute("sessionList")==null && session.getAttribute("sessionNumber")==null){
					playNumber = 0;
					if(req.getParameter("category").equals("defult")){
						allVocabulary = vocSvc.getAll();
					}else{
						allVocabulary = vocSvc.getAllByCate(req.getParameter("category"));
					}
					
					Collections.shuffle(allVocabulary);
					session.setAttribute("sessionList", allVocabulary);
					session.setAttribute("sessionNumber", playNumber);
				}else{
					allVocabulary = (List) session.getAttribute("sessionList");
					playNumber = (int) session.getAttribute("sessionNumber");
				}

System.out.println("yyy");				
				//int n = (int)Math.floor((Math.random()*allVocabulary.size()));
				VocabularyVO vocabularyVO = null;;
				try{
					vocabularyVO = (VocabularyVO) allVocabulary.get(playNumber);
					session.setAttribute("sessionNumber", ++playNumber);
				}catch(Exception e){
					session.removeAttribute("sessionList");
					session.removeAttribute("sessionNumber");
				}
				
				
				String sentence = vocabularyVO.getVoc_sentence().replace(vocabularyVO.getVoc_name(), "_____");
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				JSONObject obj = new JSONObject();
				obj.put("voc_name", vocabularyVO.getVoc_name());
				obj.put("voc_translate", vocabularyVO.getVoc_translate());
				obj.put("voc_desc", vocabularyVO.getVoc_desc());
				obj.put("voc_sentence", sentence);
				obj.put("number", playNumber);
//System.out.println(n + ":" + vocabularyVO.getVoc_name());	
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
