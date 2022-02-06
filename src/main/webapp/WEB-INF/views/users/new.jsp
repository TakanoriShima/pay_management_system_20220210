<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="logAction" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="commLgn" value="${ForwardConst.CMD_SHOW_LOGIN.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>ユーザー新規登録</h2>

        <form method="post" action="<c:url value='?action=${action}&command=${commCrt}' />">
            <c:import url="_form.jsp" />
        </form>

        <p class="rightButton"><a href="<c:url value='?action=${logAction}&command=${commLgn}' />">ログイン画面に戻る</a></p>

    </c:param>
</c:import>


