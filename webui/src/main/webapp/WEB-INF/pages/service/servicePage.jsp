<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
  <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
  <tag:kendo></tag:kendo>
  <script src="<c:url value="/resources/js/service/service.js" />"></script>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
  <title>Services</title>
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
              <h1>Services</h1>
              <br><br>

              <div id="grid-services"></div>
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

    <label for="service_name">Service name</label>
    <input type="text" class="k-input k-textbox" id="service_name"
           name="Service name"
           data-bind="value: name"
           maxlength="100"
           required/>
    <br><br>

  </div>
</script>

<script type="text/javascript">
  $(document).ready(function () {
    $("#services").addClass("over");
  });
</script>




