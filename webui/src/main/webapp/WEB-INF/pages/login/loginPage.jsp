<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <link rel="stylesheet" href="<c:url value="/resources/styles/css/registration.css" />">
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Login</title>
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
                            <h1>Enter User name and password</h1>
                            <br><br><br>
                            <form class="form-register" name='form'
                                  action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
                                <div class="form-register-with-email">
                                    <div class="form-white-background">
                                        <div class="form-row">
                                            <label>
                                                <span>User name</span>
                                                <input type="text"
                                                       name="username" required="required"/>
                                            </label>
                                        </div>
                                        <div class="form-row">
                                            <label>
                                                <span>Password</span>
                                                <input style="margin-left: 5px" type="password"
                                                       name="password" required="required"/>
                                            </label>
                                        </div>
                                        <div class="form-row">
                                            <button type="submit">Login</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
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


