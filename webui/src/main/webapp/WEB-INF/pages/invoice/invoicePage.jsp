<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <tag:kendo></tag:kendo>
    <script src="<c:url value="/resources/js/user/datasource.users.js" />"></script>
    <script src="<c:url value="/resources/js/invoice/invoice.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Invoices</title>
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
                            <h1>Invoices</h1>
                            <br><br>

                            <div id="grid-invoices"></div>
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
        <label for="user_id">User</label>
        <select id="user_id"
                name="User"
                data-bind="value:userId"
                data-value-field="id"
                data-text-field="text"
                data-source="usersDataSource"
                data-role="dropdownlist"
                data-value-primitive="true"
                data-autobind="true"
                data-change="change"
                required
                data-option-label="Select user"
                ></select>
        <br><br>

        <label for="currency">Money</label>
        <input id="currency" data-role="numerictextbox"
               data-format="c"
               data-min="0"
               data-max="300000"
               data-bind="value: money"/>


    </div>
</script>

<script type="text/javascript">
    $(document).ready(function () {
        $("#invoices").addClass("over");
    });
</script>




