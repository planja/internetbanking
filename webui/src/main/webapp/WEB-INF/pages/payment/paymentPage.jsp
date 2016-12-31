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
    <script src="<c:url value="/resources/js/payment/datasource.payments.js" />"></script>
    <script src="<c:url value="/resources/js/user/datasource.users.js" />"></script>
    <script src="<c:url value="/resources/js/payment/payment.js" />"></script>
    <script src="<c:url value="http://cdnjs.cloudflare.com/ajax/libs/jszip/2.4.0/jszip.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Payments</title>
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
                            <h1>Payments</h1>
                            <br><br>

                            <div id="grid-payments"></div>
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
            <label for="payment_type">Type</label>
            <select id="payment_type"
                    name="payment_type"
                    data-bind="value:paymentType"
                    data-value-field="id"
                    data-text-field="text"
                    data-source="paymentTypesDataSource"
                    data-role="dropdownlist"
                    data-value-primitive="true"
                    data-autobind="true"
                    data-change="change"
                    required validationMessage="Select payment type"
                    ></select>

            <div id="for_phone">
                <br>
                <label for="phone">Phone number</label>

                <input id="phone"
                       type="text"
                       name="Phone"
                       class="k-input k-textbox"
                       data-bind="value:number"
                       required
                       onkeydown="javascript:backspacerDOWN(this,event);"
                       onkeyup="javascript:backspacerUP(this,event);"/>
                <br><br>
                <label for="phone_operator">Mobile operator</label>
                <select id="phone_operator"
                        name="payment_type"
                        data-bind="value:operator"
                        data-value-field="id"
                        data-text-field="text"
                        data-source="mobileOperatorsDataSource"
                        data-role="dropdownlist"
                        data-value-primitive="true"
                        data-autobind="true"
                        required validationMessage="Select mobile operator"
                        ></select>
            </div>
            <div id="for_internet">
                <br>
                <label for="internet">Contract number</label>

                <input id="internet"
                       data-role="numerictextbox"
                       data-bind="value:number"
                       data-format="n0"
                       data-min="1"
                       data-max="99999999999999999"/>
                <br><br>
                <label for="internet_operator">Internet operator</label>
                <select id="internet_operator"
                        name="payment_type"
                        data-bind="value:operator"
                        data-value-field="id"
                        data-text-field="text"
                        data-source="internetOperatorsDataSource"
                        data-role="dropdownlist"
                        data-value-primitive="true"
                        data-autobind="true"
                        required validationMessage="Select internet operator"
                        ></select>
            </div>
            <div id="for_other">
                <br>
                <label for="other_number">Contract number</label>
                <input type="text"
                       name="Contract number"
                       class="k-input k-textbox"
                       id="other_number"
                       data-bind="value: number"
                       maxlength="100"
                       required/>
            </div>
            <br>
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
            <br><br>
            <label for="currency">Money</label>
            <input id="currency" data-role="numerictextbox"
                   data-format="c"
                   data-min="1"
                   data-max="300000"
                   data-bind="value: money"/>


        </div>

        <br>
        <div id="for_edit">
            <label for="payment_status">Payment status</label>
            <select id="payment_status"
                    name="payment_type"
                    data-bind="value:paymentStatus"
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
        $("#payment").addClass("over");
    });


    var zChar = new Array(' ', '(', ')', '-', '.');
    var maxphonelength = 13;
    var phonevalue1;
    var phonevalue2;
    var cursorposition;

    function ParseForNumber1(object) {
        phonevalue1 = ParseChar(object.value, zChar);
    }
    function ParseForNumber2(object) {
        phonevalue2 = ParseChar(object.value, zChar);
    }

    function backspacerUP(object, e) {
        if (e) {
            e = e
        } else {
            e = window.event
        }
        if (e.which) {
            var keycode = e.which
        } else {
            var keycode = e.keyCode
        }

        ParseForNumber1(object)

        if (keycode >= 48) {
            ValidatePhone(object)
        }
    }

    function backspacerDOWN(object, e) {
        if (e) {
            e = e
        } else {
            e = window.event
        }
        if (e.which) {
            var keycode = e.which
        } else {
            var keycode = e.keyCode
        }
        ParseForNumber2(object)
    }

    function GetCursorPosition() {

        var t1 = phonevalue1;
        var t2 = phonevalue2;
        var bool = false
        for (i = 0; i < t1.length; i++) {
            if (t1.substring(i, 1) != t2.substring(i, 1)) {
                if (!bool) {
                    cursorposition = i
                    bool = true
                }
            }
        }
    }

    function ValidatePhone(object) {

        var p = phonevalue1

        p = p.replace(/[^\d]*/gi, "")

        if (p.length < 3) {
            object.value = p
        } else if (p.length == 3) {
            pp = p;
            d4 = p.indexOf('(')
            d5 = p.indexOf(')')
            if (d4 == -1) {
                pp = "(" + pp;
            }
            if (d5 == -1) {
                pp = pp + ")";
            }
            object.value = pp;
        } else if (p.length > 3 && p.length < 7) {
            p = "(" + p;
            l30 = p.length;
            p30 = p.substring(0, 4);
            p30 = p30 + ")"

            p31 = p.substring(4, l30);
            pp = p30 + p31;

            object.value = pp;

        } else if (p.length >= 7) {
            p = "(" + p;
            l30 = p.length;
            p30 = p.substring(0, 4);
            p30 = p30 + ")"

            p31 = p.substring(4, l30);
            pp = p30 + p31;

            l40 = pp.length;
            p40 = pp.substring(0, 8);
            p40 = p40 + "-"

            p41 = pp.substring(8, l40);
            ppp = p40 + p41;

            object.value = ppp.substring(0, maxphonelength);
        }

        GetCursorPosition()

        if (cursorposition >= 0) {
            if (cursorposition == 0) {
                cursorposition = 2
            } else if (cursorposition <= 2) {
                cursorposition = cursorposition + 1
            } else if (cursorposition <= 5) {
                cursorposition = cursorposition + 2
            } else if (cursorposition == 6) {
                cursorposition = cursorposition + 2
            } else if (cursorposition == 7) {
                cursorposition = cursorposition + 4
                e1 = object.value.indexOf(')')
                e2 = object.value.indexOf('-')
                if (e1 > -1 && e2 > -1) {
                    if (e2 - e1 == 4) {
                        cursorposition = cursorposition - 1
                    }
                }
            } else if (cursorposition < 11) {
                cursorposition = cursorposition + 3
            } else if (cursorposition == 11) {
                cursorposition = cursorposition + 1
            } else if (cursorposition >= 12) {
                cursorposition = cursorposition
            }

            var txtRange = object.createTextRange();
            txtRange.moveStart("character", cursorposition);
            txtRange.moveEnd("character", cursorposition - object.value.length);
            txtRange.select();
        }

    }

    function ParseChar(sStr, sChar) {
        if (sChar.length == null) {
            zChar = new Array(sChar);
        }
        else zChar = sChar;

        for (i = 0; i < zChar.length; i++) {
            sNewStr = "";

            var iStart = 0;
            var iEnd = sStr.indexOf(sChar[i]);

            while (iEnd != -1) {
                sNewStr += sStr.substring(iStart, iEnd);
                iStart = iEnd + 1;
                iEnd = sStr.indexOf(sChar[i], iStart);
            }
            sNewStr += sStr.substring(sStr.lastIndexOf(sChar[i]) + 1, sStr.length);

            sStr = sNewStr;
        }

        return sNewStr;
    }

</script>




