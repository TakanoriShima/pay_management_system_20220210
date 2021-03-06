<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="command" value="${ForwardConst.CMD_LOGIN.getValue()}" />
<c:set var="actUser" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${loginError}">
            <div id="flush_error">
                メールアドレスかパスワードが間違っています
            </div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}" ></c:out>
            </div>
        </c:if>
        <h2>ログイン</h2>
        <form method="post" action="<c:url value='/?action=${action}&command=${command}' />">
            <div id="login_fomat">
            <label for="${AttributeConst.USER_EMAIL.getValue()}">メールアドレス</label><br />
            <input type="text" name="${AttributeConst.USER_EMAIL.getValue()}" value="${email}">
            <br /><br />

            <label for="${AttributeConst.USER_PASSWORD.getValue()}">パスワード</label><br />
            <input type="password" name="${AttributeConst.USER_PASSWORD.getValue()}" />
            <br /><br />

            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}">
            <button type="submit">ログイン</button>
            </div>
        </form>
        <p class="leftButton"><a href="<c:url value='?action=${actUser}&command=${commNew}' />">新規登録</a></p>
        <p class="rightButton"><a href="<c:url value='?action=${actUser}&command=${commEdit}' />">パスワードお忘れの方</a></p>
    </c:param>
</c:import>
