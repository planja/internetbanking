<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
    <script type="text/javascript">
        var isUser = true;
    </script>
    <c:if test="${pageContext.request.isUserInRole('OPERATOR')}">
        <script type="text/javascript">
            isUser = false;
        </script>
    </c:if>
    <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
        <script type="text/javascript">
            isUser = false;
        </script>
    </c:if>
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <tag:kendo></tag:kendo>
    <script src="<c:url value="/resources/js/user/datasource.users.js" />"></script>
    <script src="<c:url value="/resources/js/payment/datasource.payments.js" />"></script>
    <script src="<c:url value="/resources/js/transfer/transfer.js" />"></script>
    <script src="<c:url value="http://cdnjs.cloudflare.com/ajax/libs/jszip/2.4.0/jszip.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Transfers</title>
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
                            <h1>Transfers</h1>
                            <br><br>

                            <div id="grid-transfers"></div>
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

        <div id="for_save">
            <label for="bankNumber">Bank number</label>
            <input id="bankNumber" data-role="numerictextbox"
                   data-format="n0"
                   data-min="1"
                   data-max="30000000000"
                   data-bind="value: bankNumber"/>
            <br><br>
            <label for="billNumber">Bill number</label>
            <input id="billNumber" data-role="numerictextbox"
                   data-format="n0"
                   data-min="1"
                   data-max="300000000000"
                   data-bind="value: billNumber"/>
            <br><br>
            <label for="receiverFullName">Receiver name</label>
            <input type="text" class="k-input k-textbox" id="receiverFullName"
                   name="Receiver name"
                   data-bind="value: receiverFullName"
                   maxlength="100"
                   required/>
            <br><br>
            <label for="money">Money</label>
            <input id="money" data-role="numerictextbox"
                   data-format="c"
                   data-min="1"
                   data-max="300000"
                   data-bind="value: money"/>
            <br><br>
            <label for="invoice">Invoice</label>
            <select id="invoice"
                    required
                    name="Invoice"
                    data-bind="value:invoiceId"
                    data-value-field="id"
                    data-text-field="text"
                    data-source="invoicesDataSource"
                    data-role="dropdownlist"
                    data-value-primitive="true"
                    data-autobind="true"
                    data-option-label="Select invoice"
                    ></select>
        </div>

        <br>
        <div id="for_edit">
            <label for="payment_status">Transfer status</label>
            <select id="payment_status"
                    name="payment_type"
                    data-bind="value:status"
                    data-value-field="id"
                    data-text-field="text"
                    data-source="paymentStatusDataSource"
                    data-role="dropdownlist"
                    data-value-primitive="true"
                    data-autobind="true"
                    required validationMessage="Select payment status"
                    ></select>
            <br><br>
            <label for="cause">Cause</label>
            <input type="text" class="k-input k-textbox" id="cause" data-bind="value: cause"/>
        </div>


    </div>
</script>

<script type="text/javascript">
    $(document).ready(function () {
        $("#transfer").addClass("over");
    });
</script>




