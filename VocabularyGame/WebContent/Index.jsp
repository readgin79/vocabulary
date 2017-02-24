<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
</head>
<body>

    <div>
    	<h4>請輸入英文單字</h4>
    	<div>
    		<table border="1" width="80%" id="vocTable">
    			<tr><th colspan="4">分類</th></tr>
    			<tr>
    				<th width="25%">英文單字</th>
    				<th width="25%">中文</th>
    				<th width="40%">範例</th>
    				<th width="10%">圖例</th>
    			</tr>
    			<tr>
    				<td><input type="text" name="" style="width:99%"></td>
    				<td><input type="text" name="" style="width:99%"></td>
    				<td><input type="text" name="" style="width:99%"></td>
    				<td><input type="file" value="選擇圖片"></td>
    			</tr>
    		</table>
    		<input type="button" id="addBtn" value="增加">
    		<input type="button" id="removeBtn" value="減少">
    		<input type="submit" id="btn" value="上傳">
    	</div>
    </div>
    <div>
        <h4>請拼出以下單字</h4>
        <table border="1" width="50%">
            <tr>
                <td><span id="vocChinese"></span></td>
            </tr>
            <tr>
                <td>
                	<input type="hidden" name="" id="vocEng_true" style="width:75%">
                    <input type="text" name="" id="vocEng" style="width:75%">
                    <input type="button" name="" id="sendAns" value="送出答案">
                    <input type="button" name="" id="nextQues" value="下一題">
                </td>
            </tr>
        </table>
        <h2 id="trueOrFalse"></h2>
        <h4 id="sentence"></h4>
    </div>
</body>
<script>
	document.getElementById("addBtn").addEventListener("click",function(){
		var vocTable = document.getElementById("vocTable");
		var tdNode = document.createElement("tr");
		tdNode.innerHTML = "<td><input type='text' name='' style='width:99%'></td><td><input type='text' name='' style='width:99%'></td><td><input type='text' name='' style='width:99%'></td><td><input type='file' value='選擇圖片'></td>";
		document.getElementById("vocTable").appendChild(tdNode);
	})

	document.getElementById("removeBtn").addEventListener("click",function(){
		var vocTable = document.getElementById("vocTable");
		if(vocTable.childNodes.length-1>1){
			document.getElementById("vocTable").removeChild(vocTable.childNodes[vocTable.childNodes.length-1]);
		}
	})

//     window.onload = showQuestion();
//     function randonVoc(){
//         var arrayObj = [
//         {'chinese':'活潑的，有浮力的','english':'buoyant'},
//         {'chinese':'滑稽的','english':'antic'},
//         {'chinese':'浸泡、吸收','english':'soak'},
//         {'chinese':'生動的','english':'vibrant'},
//         {'chinese':'氛圍','english':'ambiance'}
//         ];

//         var randomNum = Math.floor(Math.random()*arrayObj.length);
//         return arrayObj[randomNum];
//     }

//     function showQuestion(){
//         var voc = randonVoc();
//         var vocChinese = document.getElementById("vocChinese");
//         vocChinese.innerText = voc.chinese;

//         var vocEng = document.getElementById("vocEng");

//         var sendAns = document.getElementById("sendAns");
//         sendAns.addEventListener("click",function(){
//             if(vocEng.value == voc.english){
//                 document.getElementById("trueOrFalse").innerHTML = "恭喜答對！"
//             }else{
//                 document.getElementById("trueOrFalse").innerHTML = "厄厄...再想一下吧！"
//             }在
//         })
//     }

// 	document.getElementById("btn").addEventListener("click",function(){
// 		var jsonObj = '{"height":"180","weight":"50"}';
// 		var obj = JSON.parse(jsonObj);
// 		alert("身高" + obj.height + ",體重" + obj.weight);
// 	})
	
	$(document).ready(function (){
	    $("#nextQues").click(function(){  //選擇下一題
	        $.ajax({
	             type:"POST",
	             url: "/VocabularyGame/vocabulary/vocabulary.do", 
	             data:{"action":"getOne_For_Display"},
	             dataType: "json",       
	             
	             success : function(response){
	                 $("#vocChinese").text(response.voc_translate);
	                 $("#vocEng_true").val(response.voc_name);
	             },
	             
	             error:function(xhr, ajaxOptions, thrownError){
	                 alert(xhr.status+"\n"+thrownError);
	             }
	      	});
	     });
	    
	    $("#sendAns").click(function(){	//確認答案
	        $.ajax({
	             type:"POST",
	             url: "/VocabularyGame/vocabulary/vocabulary.do", 
	             data:creatQueryString($("#vocEng").val(),$("#vocEng_true").val()),
	             dataType: "json",       
	             
	             success : function(response){
	                 $("#trueOrFalse").text(response.trueOrFalse);
	             },
	             
	             error:function(xhr, ajaxOptions, thrownError){
	                 alert(xhr.status+"\n"+thrownError);
	             }
	      	});
	     });
	});
	
	function creatQueryString(ans,trueAns){
		var queryString={"action":"isCorrect","answer":ans,"trueAns":trueAns};
		return queryString;
	}
</script>
</html>