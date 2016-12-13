<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<head>
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <tag:kendo></tag:kendo>
    <script src="<c:url value="/resources/js/user/datasource.roles.js" />"></script>
    <script src="<c:url value="/resources/js/admin/admin.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Admin page</title>
</head>
<body>
<div id="container">
    <div id="main_panel">
        <div class="main_center">
            <tag:header></tag:header>
            <div id="main_middle_panel">

                <div class="main_middle_controller">
                    <div class="main_middle_details">
                        <div class="main_left_controller">
                            <h1>Welcome ${pageContext.request.userPrincipal.name}</h1>
                            <br><br>
                            <div id="grid-users"></div>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </div>
            <div class="clear"></div>
        </div>
    </div>

</div>
<tag:footer></tag:footer>
</body>

<script id="popup_editor" type="text/x-kendo-template">
    <div id="popup_editor_content" class="popup-editor-container">
        <label for="roles">Roles</label>

        <select id="roles"
                name="roles"
                data-bind="value:roles"
                data-value-field="id"
                data-text-field="text"
                data-source="rolesDataSource"
                data-role="multiselect"
                data-value-primitive="true"
                data-autobind="true"
                data-placeholder="Select roles ..."></select>
    </div>
</script>

<script type="text/javascript">
    $(document).ready(function () {
        $("#admin").addClass("over");
    });

</script>


