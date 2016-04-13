<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="col-lg-12">
                <div class="centering text-center error-container">
                    <div class="text-center">
                        <c:if test="${not empty message}">
                            <div style="color:red">
                                <h2 class="without-margin"><big>Dear <strong>${username}.</big></strong>
                                    <span class="text-warning"><big><strong>${message}</strong></big></span>
                                </h2>
                            </div>
                        </c:if>
                    </div>
                    <div class="text-center">
                        <h3><small>Choose an option below</small></h3>
                    </div>
                    <hr>
                    <ul class="pager">
                        <li><a href="/web/logout">Login again</a></li>
                        <li><a href="/web/groups">Access to account</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>