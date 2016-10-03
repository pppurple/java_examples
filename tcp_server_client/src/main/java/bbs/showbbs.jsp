<%@ page contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="bbs.Message" %>
<%!
    private String escapeHtml(String src) {
        return src.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>
<html>
<head>
<title>test bbs</title>
</head>
<body>
<h1>test BBS</h1>
<form action='/testbbs_jsp/PostBBS' method='post'>
title : <input type='text' name='title' size='60'><br/>
handle name : <input type='text' name='handle'><br/>
<textarea name='message' rows='4' cols='60'></textarea><br/>
<input type='submit'/>
</form>
<hr/>
<%
for (Message msg : Message.messageList) {
%>
<p>「<%= escapeHtml(msg.title) %>」&nbsp;&nbsp;
<%= escapeHtml(msg.handle) %> さん&nbsp;&nbsp;
<%= escapeHtml(msg.date.toString()) %></p>
<p>
<%= escapeHtml(msg.message).replace("\r\n", "<br/>") %>
</p><hr/>
<%
}
%>
</body>
</html>
