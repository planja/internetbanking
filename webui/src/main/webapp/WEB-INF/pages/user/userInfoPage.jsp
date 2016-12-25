<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>


    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/js/user/userinfo.js" />"></script>
    <tag:kendo></tag:kendo>
    <link rel="stylesheet" href="<c:url value="/resources/styles/css/registration.css" />">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>User Info</title>
</head>
<body>
<div id="container">
    <div id="main_panel">
        <div class="main_center">
            <tag:header></tag:header>
            <div id="main_middle_panel">
                <div class="main_middle_controller">
                    <div class="main_middle_details">
                        <div id="main_left_panel">
                            <div class="main_left_controller">
                                <div class="main_left_details">
                                    <h1>${message}</h1>
                                    <form:form class="form-register" method="post" action="/updateUser">
                                        <form:hidden path="id"/>

                                        <div class="form-register-with-email">

                                            <div class="form-white-background">
                                                <div class="form-row">
                                                    <label>
                                                        <span>Full name</span>
                                                        <form:input cssStyle="margin-left: 63px" path="name" type="text"
                                                                    name="name" pattern=".{6,}"
                                                                    maxlength="100"
                                                                    required="required"
                                                                    oninvalid="setCustomValidity('Minimum length is 6 characters')"
                                                                    oninput="setCustomValidity('')"/>
                                                    </label>
                                                </div>
                                                <div class="form-row">
                                                    <label>
                                                        <span>Passport number</span>
                                                        <form:input cssStyle="margin-left: 13px" path="passportNumber"
                                                                    type="text" name="passportNumber"
                                                                    pattern="MP[0-9]{7}" required="required"
                                                                    oninvalid="setCustomValidity('Enter valid passport number')"
                                                                    oninput="setCustomValidity('')"/>
                                                    </label>
                                                </div>
                                                <div class="form-row">
                                                    <label>
                                                        <span>Issued passport</span>
                                                        <form:input cssStyle="margin-left: 20px" path="issuedPassport"
                                                                    type="text" name="issuedPassport"
                                                                    pattern=".{6,}"
                                                                    maxlength="100"
                                                                    required="required"
                                                                    oninvalid="setCustomValidity('Minimum length is 6 characters')"
                                                                    oninput="setCustomValidity('')"/>
                                                    </label>
                                                </div>
                                                <div class="div-for-datepicker">
                                                    <label>
                                                        <span>Passport ussuing date</span>
                                                        <form:input id="datepicker" path="passportIssuingDate"
                                                                    type="text" name="passportIssuingDate"
                                                                    pattern="(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d"
                                                                    required="required"/>
                                                    </label>
                                                </div>
                                                <div class="form-row">
                                                    <label>
                                                        <span>User name</span>
                                                        <form:input cssStyle="margin-left: 58px" path="userName"
                                                                    type="text" name="user name" pattern=".{5,}"
                                                                    maxlength="100"
                                                                    required="required"
                                                                    oninvalid="setCustomValidity('Minimum length is 5 characters')"
                                                                    oninput="setCustomValidity('')"/>
                                                    </label>
                                                </div>

                                                <div class="form-row">
                                                    <label>
                                                        <span>Email</span>
                                                        <form:input path="mail" style="margin-left: 93px" type="email"
                                                                    name="email" required="required"/>
                                                    </label>
                                                </div>

                                                <div class="form-row">
                                                    <label>
                                                        <span>Password</span>
                                                        <form:input cssStyle="margin-left: 64px" path="userPassword"
                                                                    type="password"
                                                                    name="password" pattern=".{5,}"
                                                                    maxlength="100"
                                                                    required="required"
                                                                    oninvalid="setCustomValidity('Minimum length is 5 characters')"
                                                                    oninput="setCustomValidity('')"/>
                                                    </label>
                                                </div>

                                                <div class="form-row">
                                                    <button type="submit">Update</button>
                                                </div>

                                            </div>


                                        </div>


                                    </form:form>
                                </div>
                            </div>

                        </div>
                        <div id="main_right_panel">
                            <div class="main_right_controller">
                                <div class="main_right_details">
                                    <h1>Invoices</h1>

                                    <div id="grid-invoices">
                                    </div>

                                </div>
                                <div class="clear"></div>
                            </div>

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
        <div id="for_edit">
            <label for="currency">Money</label>
            <input id="currency" data-role="numerictextbox"
                   data-format="c"
                   data-bind="value: money"
                   data-min="0"
                   data-max="3000000000"/>
        </div>
        <div id="for_save">
            Are you sure you want to create invoice?
        </div>
    </div>
</script>


<script type="text/javascript">
    $(document).ready(function () {
        $("#userinfo").addClass("over");
        $("#datepicker").kendoDatePicker();
    });
</script>
