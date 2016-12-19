<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<head>
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Contact</title>
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
                                    <h1>Contact Us</h1>

                                    <div style="padding:10px 0 10px 0">
                                        <div><strong>
                                            Bank is located in the center. This place is both developed center
                                            and one of the most beautiful corners of the city with a park and quiet by-streets, where a number
                                            of diplomatic missions are located.</strong><br/>
                                            <br/>
                                            We are always glad to answer your questions by phone.
                                        </div>
                                        <div><br/>
                                            <h6>Contact Information: </h6>
                                            <img src="/resources/images/photo-contact.jpg" alt="" width="152"
                                                 height="100" class="project-img"/> Kommunisticheskaia, 49, prem. 1,<br/>
                                            postal code 220002, Minsk, Belarus.<br/>
                                            <br/>

                                            <p><span><img src="/resources/images/ico-phone.png" alt="Phone" width="20"
                                                          height="16" hspace="2"/> Phone:</span> (888) 123 456 789<br/>
                                                <span><img src="/resources/images/ico-fax.png" alt="Fax" width="20"
                                                           height="16" hspace="2"/> Fax:</span> (888) 987 654 321<br/>
                                                <span><img src="/resources/images/ico-website.png" alt="WWW Link"
                                                           width="20" height="16" hspace="2"/> Website:</span> <a
                                                        href="#">www.mycompany.com</a><br/>
                                                <span><img src="/resources/images/ico-email.png" alt="Email" width="20"
                                                           height="16" hspace="2"/> Bank number:</span> 123321<br/>
                                                <br><br>
                                            </p>
                                        </div>


                                    </div>

                                </div>
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

<script type="text/javascript">
    $(document).ready(function () {
        $("#contact").addClass("over");
    });

</script>
