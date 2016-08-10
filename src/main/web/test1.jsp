<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ page contentType="text/html; charset=windows-1251" %>
<%@ page import="java.util.*, java.text.*" %>

<html>
<head>
    <title>Решебник</title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
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
        <textarea name=task-condition class=task-condition rows="10" type="text" placeholder="Введите условия задачи" autofocus></textarea>
        <small><b>Решение:</b></small>
        <textarea name=task-solution class=task-solution rows="22" type="text" readonly></textarea>
        <small><b>Ответ:</b></small>
        <textarea name=task-result class=task-result rows="1" readonly></textarea>
        <div align="right"><button type="submit" class=task-button><img src="/img/task_button_label.png" class=task-button-image>Решить</button></div>
    </fieldset>

</form>

</body>
</html>

<%!
    String getFormattedDate ()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return sdf.format(new Date());
    }
%>