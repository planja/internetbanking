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

                            <h2>No checks. No cash. No charge.</h2>

                            <p><span class="code">" Now, sending money to friends and family is FAST & FREE with Bank Online and Mobile."</span>
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

                                <p>You set up a payment via Send Money in Bank Online or Mobile<br/>
                                    using your account.</p>
                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="main_top_box_mid">
                        <div class="main_top_box_mid_controller">
                            <div class="main_top_box_mid_details">
                                <h3><span class="heading2">Fast service</span></h3>

                                <p>We transfer your money faster than any other bank.</p>


                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="main_top_box_mid">
                        <div class="main_top_box_mid_controller">
                            <div class="main_top_box_mid_details">
                                <h3><span class="heading3">Time Management</span></h3>

                                <p>Save time by making transfers directly from your computer</p>


                            </div>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div id="main_top_box_right">
                        <div class="main_top_box_right_controller">
                            <div class="main_top_box_right_details">
                                <h3><span class="heading4">Reports</span></h3>

                                <p>Print reports at any time </p>


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
                                    <h4>Community Possible</h4>

                                    <p>We believe all people deserve the opportunity to dream, believe and achieve. The building blocks that made our country great - a stable job, a home
                                        to call your own and a community connected through culture, recreation and play - continue to be at the heart of possibility for all of us.
                                        At Bank, we invest our time, resources and passion to build and support vibrant communities that allow every person to work toward their possible.</p>

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
                                        <h4>
                                            Belonging to an international financial group allows us to solve problems of any
                                            level of complexity, enables the financing of major projects and the provision of
                                            individual tariffs for corporate clients. </h4>

                                        <p>
                                            Bank offers a wide range of services for small businesses and is actively working with individuals.
                                            We aim to create a favorable economic environment, financial empowerment,
                                            development of beneficial programs and products for our customers.</p>
                                        <ul class="left_list">
                                            <li><span> Board of Directors </span></li>
                                            <li><span> leadership </span></li>
                                        </ul>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </div>
                        <div id="main_right_panel">
                            <div class="main_right_controller">
                                <div class="main_right_details">
                                    <h1>Our advantages </h1>
                                    <h4><span class="heading4">All our staff has good skills</span></h4>

                                    <div><img src="/resources/images/img9.gif" alt="" class="right_ing"/></div>
                                    <p>
                                        All our staff has strong skills in internet-banking.
                                        They constantly improve their skills in all areas of banking.</p>
                                    <h4><span class="heading4">High processing speed</span></h4>

                                    <div><img src="/resources/images/img10.gif" alt="" class="right_ing"/></div>
                                    <p> We transfer your money faster than any other bank. We are constantly improving the speed and quality of transactions.</p>

                                    <h4><span class="heading4">Full protection of transactions</span></h4>

                                    <div><img src="/resources/images/img11.gif" alt="" class="right_ing"/></div>
                                    <p>We fully ensure secrecy of your money and your transactions.</p>

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


