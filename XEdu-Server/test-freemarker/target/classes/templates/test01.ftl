<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>

list指令学习
<table>
    <tr>
        <td>编号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>余额</td>
        <td>生日</td>
    </tr>
    <#list students as student>
        <tr>
            <td>${student_index+1}</td>
            <td>${student.name}</td>
            <td>${student.age}</td>
            <td>${student.money}</td>
            <td>${student.birthday?date}</td>
        </tr>
    </#list>
</table>

map指令学习

</body>
</html>