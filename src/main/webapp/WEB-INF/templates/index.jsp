<%@ include file="header.jsp" %>
    <div class="content">
        <h2><c:out value="${displayName}"/></h2>
        ${content}
        <p><a href="/article/update/<c:out value="${pageName}"/>">Edit</a></p>
    </div>
<%@ include file="footer.jsp" %>