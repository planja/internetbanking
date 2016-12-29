<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<link rel="stylesheet" href="<c:url value="/resources/styles/css/main.css" />">
<link rel="stylesheet" href="<c:url value="/resources/styles/css/modal-box.css" />">
<div id="main_top_panel">
    <div class="main_top_controller">
        <div id="main_logo_panel">
            <div class="/logout"><a href="<c:url value="/welcome"/>"><img src="/resources/images/logo.gif" alt="" border="0"/></a>
            </div>
        </div>
    </div>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <div id="main_top_search_panel">
            <div class="main_top_search_controller">
                <div class="main_top_search_details">
                    <form action="/logout" method="post">
                        <button class="logout-button" name="submit" type="submit" value="submit">Logout</button>
                    </form>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </c:if>
</div>
<div id="main_navigation_panel">
    <div class="nav">
        <ul>
            <li><a id="welcome" href="<c:url value="/welcome"/>"><span>Home</span> </a></li>

            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <li><a id="registration" href="<c:url value="/registration"/>"><span>Registration</span> </a></li>
            </c:if>

            <c:choose>
                <c:when test="${pageContext.request.userPrincipal.name == null}">
                    <li><a href="<c:url value="#openModal"/>"><span>Login</span> </a></li>
                </c:when>
                <c:otherwise>
                    <li><a id="userinfo" href="<c:url value="/userInfo"/>"><span>User Info</span> </a></li>
                </c:otherwise>
            </c:choose>

            <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
                <li><a id="admin" href="<c:url value="/admin"/>"><span>Admin</span> </a></li>
            </c:if>

            <c:if test="${pageContext.request.isUserInRole('OPERATOR')||pageContext.request.isUserInRole('ADMIN')}">
                <li><a id="invoices" href="<c:url value="/invoice"/>"><span>Invoices</span> </a></li>
            </c:if>

            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><a id="payment" href="<c:url value="/payment"/>"><span>Payments</span> </a></li>
            </c:if>

            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><a id="transfer" href="<c:url value="/transfer"/>"><span>Transfers</span> </a></li>
            </c:if>

            <!--<li><a id="services" href="<c:url value="/services"/>"><span>Services</span> </a></li>

            <li><a id="about" href="<c:url value="/about"/>"><span>About Us</span> </a></li>-->
            <li><a id="contact" href="<c:url value="/contact"/>"><span>Contact</span> </a></li>


        </ul>
        <div class="clear"></div>
    </div>
</div>

<!--Registration window -->
<div id="openModal" class="modalDialog">
    <div>
        <a href="#close" title="Close" class="close">X</a>

        <div class="login-page">
            <div class="form">
                <form action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'
                      class="login-form">
                    <input type='text' name='username' value='' placeholder="Login"
                           pattern=".{5,}"
                           maxlength="100"
                           required="required"
                           oninvalid="setCustomValidity('Minimum length is 5 characters')"
                           oninput="setCustomValidity('')"/>
                    <input type='password' name='password' placeholder="Password"
                           pattern=".{5,}"
                           maxlength="100"
                           required="required"
                           oninvalid="setCustomValidity('Minimum length is 5 characters')"
                           oninput="setCustomValidity('')"/>
                    <button name="submit" type="submit" value="submit">Login</button>
                    <p class="message">Not registered? <a href="<c:url value="/registration"/>">Create an account</a>
                    </p>
                </form>
            </div>
        </div>
    </div>
</div>
