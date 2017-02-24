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
    	<h4>�п�J�^���r</h4>
    	<div>
    		<table border="1" width="80%" id="vocTable">
    			<tr><th colspan="4">����</th></tr>
    			<tr>
    				<th width="25%">�^���r</th>
    				<th width="25%">����</th>
    				<th width="40%">�d��</th>
    				<th width="10%">�Ϩ�</th>
    			</tr>
    			<tr>
    				<td><input type="text" name="" style="width:99%"></td>
    				<td><input type="text" name="" style="width:99%"></td>
    				<td><input type="text" name="" style="width:99%"></td>
    				<td><input type="file" value="��ܹϤ�"></td>
    			</tr>
    		</table>
    		<input type="button" id="addBtn" value="�W�[">
    		<input type="button" id="removeBtn" value="���">
    		<input type="submit" id="btn" value="�W��">
    	</div>
    </div>
    <div>
        <h4>�Ы��X�H�U��r</h4>
        <table border="1" width="50%">
            <tr>
                <td><span id="vocChinese"></span></td>
            </tr>
            <tr>
                <td>
                	<input type="hidden" name="" id="vocEng_true" style="width:75%">
                    <input type="text" name="" id="vocEng" style="width:75%">
                    <input type="button" name="" id="sendAns" value="�e�X����">
                    <input type="button" name="" id="nextQues" value="�U�@�D">
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
		tdNode.innerHTML = "<td><input type='text' name='' style='width:99%'></td><td><input type='text' name='' style='width:99%'></td><td><input type='text' name='' style='width:99%'></td><td><input type='file' value='��ܹϤ�'></td>";
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
//         {'chinese':'���⪺�A���B�O��','english':'buoyant'},
//         {'chinese':'�ƽ]��','english':'antic'},
//         {'chinese':'���w�B�l��','english':'soak'},
//         {'chinese':'�Ͱʪ�','english':'vibrant'},
//         {'chinese':'�^��','english':'ambiance'}
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
//                 document.getElementById("trueOrFalse").innerHTML = "���ߵ���I"
//             }else{
//                 document.getElementById("trueOrFalse").innerHTML = "�̤�...�A�Q�@�U�a�I"
//             }�b
//         })
//     }

// 	document.getElementById("btn").addEventListener("click",function(){
// 		var jsonObj = '{"height":"180","weight":"50"}';
// 		var obj = JSON.parse(jsonObj);
// 		alert("����" + obj.height + ",�魫" + obj.weight);
// 	})
	
	$(document).ready(function (){
	    $("#nextQues").click(function(){  //��ܤU�@�D
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
	    
	    $("#sendAns").click(function(){	//�T�{����
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