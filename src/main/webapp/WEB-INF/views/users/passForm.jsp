<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_USER.getValue()}" />
<c:set var="command" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="${AttributeConst.USER_EMAIL.getValue()}">メールアドレス</label><br />
<input type="text" name="${AttributeConst.USER_EMAIL.getValue()}" value="${user.email}" />
<br /><br />

<label for="${AttributeConst.USER_NAME.getValue()}">名前(変更する場合)</label><br />
<input type="text" name="${AttributeConst.USER_NAME.getValue()}" value="${user.name}" />
<br /><br />

<label for="${AttributeConst.USER_PASSWORD.getValue()}">新しいパスワード</label><br />
<input type="password" name="${AttributeConst.USER_PASSWORD.getValue()}" value="${user.password}" />
<br /><br />

<input type="hidden" name="${AttributeConst.USER_ID.getValue()}" value="${user.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">登録</button>