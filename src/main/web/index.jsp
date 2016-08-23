<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%--<%@ page import="java.util.*, java.text.*" %>--%>

<html>
<head>
    <title>Решебник</title>
    <link rel="stylesheet"  href="css\solver.css" type="text/css">
    <link rel="shortcut icon" href="img\solver_icon.png" type="image/png">
</head>
<style>
    body {background: url(img/solver_background.png);}
</style>
<body>
<form method="post">
    <%--<h1><div align="right">Решебник. Сегодня <%=getFormattedDate()%></div></h1>--%>
    <fieldset id=main-col>
        <small><b>Условия:</b></small>
        <textarea id="task-condition-id" class=task-condition rows="10" placeholder="Введите условия задачи" autofocus>Мальчик курит папиросу.</textarea>
        <small><b>Вопрос:</b></small>
        <textarea id="task-question-id" class=task-question rows="1" placeholder="Введите вопрос">Что курит мальчик?</textarea>
        <small><b>Решение:</b></small>
        <div id="task-solution-id" class=task-solution><span></span></div>
        <small><b>Ответ:</b></small>
        <textarea id="task-answer-id" class=task-answer rows="1" readonly></textarea>
        <div align="right"><button type="button" onclick="solve()" class=task-button><img src="/img/task_button_label.png" class=task-button-image>Решить</button></div>
    </fieldset>

</form>
<script>
    function solve() {
        var xhttp = new XMLHttpRequest();
        var url = "solverServlet";
        var text = document.getElementById("task-condition-id").value;
        var question = document.getElementById("task-question-id").value;

        xhttp.open("POST", url, true);
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                var xmlDoc = xhttp.responseXML;
                document.getElementById("task-solution-id").innerHTML=xmlDoc.getElementsByTagName("solution")[0].innerHTML;
                document.getElementById("task-answer-id").innerHTML = xmlDoc.getElementsByTagName("answer")[0].innerHTML;
            }
        };

        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhttp.setRequestHeader("Content-length", text.length);
        xhttp.send("text="+text+"&question="+question);
    }
</script>
</body>
</html>

<%--<%!--%>
    <%--String getFormattedDate ()--%>
    <%--{--%>
        <%--SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");--%>
        <%--return sdf.format(new Date());--%>
    <%--}--%>
<%--%>--%>