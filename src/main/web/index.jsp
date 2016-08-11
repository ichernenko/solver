<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.text.*" %>

<html>
<head>
    <title>Решебник</title>
    <link rel="stylesheet"  href="css\solver.css" type="text/css">
    <link rel="shortcut icon" href="img\solver_icon.png" type="image/png">
</head>
<style>
    body {
        background: url(img/solver_background.png);
    }
</style>
<form method="post">
    <h1><div align="right">Решебник. Сегодня <%=getFormattedDate()%></div></h1>
    <fieldset id=main-col>
        <small><b>Задача:</b></small>
        <textarea id="task-condition-id" name=task-condition class=task-condition rows="10" type="html/text" placeholder="Введите условия задачи" autofocus></textarea>
        <small><b>Решение:</b></small>
        <div id="task-solution-id" name=task-solution class=task-solution><span>Test</span></div>
        <small><b>Ответ:</b></small>
        <textarea name=task-result class=task-result rows="1" readonly></textarea>
        <div align="right"><button type="button" onclick="solve()" class=task-button><img src="/img/task_button_label.png" class=task-button-image>Решить</button></div>
    </fieldset>

</form>
<script>
    function solve() {
        var xhttp = new XMLHttpRequest();
        var url = "solverServlet";
        var text = document.getElementById("task-condition-id").value;

        xhttp.open("POST", url, true);
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                document.getElementById("task-solution-id").innerHTML = xhttp.responseText;
            }
        };

        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhttp.setRequestHeader("Content-length", text.length);
        xhttp.setRequestHeader("Connection", "close");
        xhttp.send("text="+text);
    }
</script>
</body>
</html>

<%!
    String getFormattedDate ()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return sdf.format(new Date());
    }
%>