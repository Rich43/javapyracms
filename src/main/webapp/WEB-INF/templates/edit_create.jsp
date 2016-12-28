<%@ include file="header.jsp" %>
<div class="content">
    <form id="edit_article" class="editform" method="post" action="">
        <div class="form_description">
            <c:choose>
                <c:when test="${edit == true}">
                    <h2>Editing <c:out value="${name}"/></h2>
                </c:when>
                <c:otherwise>
                    <h2>Creating <c:out value="${name}"/></h2>
                </c:otherwise>
            </c:choose>
        </div>
        <label class="description" for="displayName">Display Name</label>
        <div>
            <input id="displayName" name="displayName" class="element text medium"
                   type="text" maxlength="255" value="<c:out value="${displayName}"/>"/>
        </div>
        <label class="description" for="content">Content</label>
        <div>
            <textarea id="content" name="content" cols="80" rows="20"><c:out value="${content}"/></textarea>
        </div>
        <input id="saveForm" class="button_text" type="submit" name="submit" value="Submit"/>
        </p>
    </form>
</div>
<%@ include file="footer.jsp" %>