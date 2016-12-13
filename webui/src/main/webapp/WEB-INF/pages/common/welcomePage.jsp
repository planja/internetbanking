<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<head>
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>Welcome</title>
</head>
<body id="body">
<div id="container">
    <div id="main_panel">
        <div class="main_center">
            <div class="main_header_panel">
                <tag:header></tag:header>
                <div class="main_header_bg">
                    <div class="main_header_controller">
                        <div class="main_header_details">

                            <h2>Duis eleifend nisl in tortor iaculis et aliquet urna </h2>

                            <p><span class="code">"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur,
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris viverra congue orci at dapibus.</span>
                            </p>
                            <img src="/resources/images/img6.gif" alt="" id="inverted_code"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="clear"></div>
                <div class="clear"></div>
            </div>
            <div id="main_top_box_panel">
                <div class="main_top_box_controller">
                    <div id="main_top_box_left">
                        <div class="main_top_box_left_controller">
                            <div class="main_top_box_left_details">
                                <h3><span class="heading1">Idea we give</span></h3>

                                <p>Suspendisse ultricies cursus<br/>
                                    arcu, quis porttitor velit olutpat.</p>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="main_top_box_mid">
                        <div class="main_top_box_mid_controller">
                            <div class="main_top_box_mid_details">
                                <h3><span class="heading2">Statistic</span></h3>

                                <p>Suspendisse ultricies cursus<br/>
                                    arcu, quis porttitor velit olutpat.</p>


                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="main_top_box_mid">
                        <div class="main_top_box_mid_controller">
                            <div class="main_top_box_mid_details">
                                <h3><span class="heading3">Time Management</span></h3>

                                <p>Suspendisse ultricies cursus<br/>
                                    arcu, quis porttitor velit olutpat.</p>


                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div id="main_top_box_right">
                        <div class="main_top_box_right_controller">
                            <div class="main_top_box_right_details">
                                <h3><span class="heading4">Portfolio</span></h3>

                                <p>Suspendisse ultricies cursus<br/>
                                    arcu, quis porttitor velit olutpat.</p>


                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
            <div id="main_middle_panel">
                <div class="main_middle_controller">
                    <div class="main_middle_details">
                        <div id="main_left_panel">
                            <div class="main_left_controller">
                                <div class="main_left_details">
                                    <h1>Welcome To Marchent Services</h1>
                                    <h4>Pellentesque pharetra quam eget quam euismod sit amet lobortis </h4>

                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris viverra congue
                                        orci at dapibus. Vestibulum eu rhoncus dui. In hac habitasse platea dictumst.
                                        Morbi volutpat justo ut odio malesuada vehicula. Etiam mollis eros est, quis
                                        accumsan tortor. Quisque vel est felisnisi.</p>

                                    <div class="readmore"><a></a></div>
                                </div>
                            </div>
                            <div class="main_left_sec_controller">
                                <div class="main_left_sec_details">
                                    <h1>Overview</h1>

                                    <div class="img_block">

                                        <img src="/resources/images/img7.gif" alt=""/>
                                    </div>

                                    <div class="list_block_right">
                                        <h4>Pellentesque pharetra quam eget quam euismod sit amet lobortis </h4>

                                        <p>Pellentesque pharetra quam eget quam euismod sit amet lobortis lacus
                                            feugiat.</p>
                                        <ul class="left_list">
                                            <li><span>Sed vel ligula non turpis sagittis aliquet. Cras nisl
                                                arcu, bibendum </span></li>
                                            <li><span>Ac suscipit eget, lacinia sit amet felis. Donec in quam
                                                eget .</span></li>
                                        </ul>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                        <div id="main_right_panel">
                            <div class="main_right_controller">
                                <div class="main_right_details">
                                    <h1>Recent Projects </h1>
                                    <h4><span class="heading4">Sed sit amet neque et massa </span></h4>

                                    <div><img src="/resources/images/img9.gif" alt="" class="right_ing"/></div>
                                    <p>Pellentesque pharetra quam eget quam euismod sit amet lobortis
                                        lacus feugiat.</p>
                                    <h4><span class="heading4">Ut quis erat sit amet magna posuere</span></h4>

                                    <div><img src="/resources/images/img10.gif" alt="" class="right_ing"/></div>
                                    <p>Pellentesque pharetra quam egetquam euismod sit amet lobortis
                                        lacus feugiat.</p>
                                    <h4><span class="heading4">Morbi tempus risus est, eu</span></h4>

                                    <div><img src="/resources/images/img11.gif" alt="" class="right_ing"/></div>
                                    <p>Pellentesqu epharetra quam eget quam euismod sit amet lobortis
                                        lacus feugiat.</p>

                                </div>
                                <div class="clear"></div>
                            </div>

                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </div>

        </div>
    </div>
</div>


<tag:footer></tag:footer>
</body>

<script type="text/javascript">
    $(document).ready(function () {
        $("#welcome").addClass("over");
    });

</script>


<c:if test="${param.error == 'true'}">
    <script type="text/javascript">
        $(document).ready(function () {
            $("body").append("<div class=box>The user name or password is incorrect </div>");
            setTimeout(function () {
                $('.box').fadeOut('fast')
            }, 3000);
        });

    </script>
</c:if>


