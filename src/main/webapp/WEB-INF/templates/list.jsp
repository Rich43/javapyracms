<%@ include file="header.jsp" %>
<div class="content">
    <form action="/redirect/article_create" method="post">
        Add a new article: <input type="text" name="pageId">
        <input type="submit" value="Submit">
    </form>
    <c:forEach var="listItem" items="${listItems}">
        <p>
            <a href="/article/item/<c:out value="${listItem.getName()}"/>">
                <c:out value="${listItem.getDisplayName()}"/>
            </a>
        </p>
    </c:forEach>
</div>
<%@ include file="footer.jsp" %>