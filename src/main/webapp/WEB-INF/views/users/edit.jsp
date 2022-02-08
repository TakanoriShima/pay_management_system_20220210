<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />
<c:set var="commLgn" value="${ForwardConst.CMD_SHOW_LOGIN.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>パスワードをお忘れの方</h2>

        <form method="post"
            action="<c:url value='?action=${action}&command=${commUpd}' />">
            <c:import url="passForm.jsp" />
        </form>

        <p class="rightButton"><a href="<c:url value='?action=${actAuth}&command=${commLgn}' />">戻る</a></p>


    </c:param>
</c:import>