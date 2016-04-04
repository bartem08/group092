<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Interviewer Appending Page</title>

    <link rel="stylesheet" href="../../resources/bootstrap/css/bootstrap.min.css"/>

    <script src="../../resources/jquery/jquery-2.2.2.min.js"></script>
    <script src="../../resources/bootstrap/js/bootstrap.min.js"></script>

    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(document).ready(function() {

            $("#interviewerForm").submit(function(){
                var form = $(this);
                var error = false;
                form.find('input, textarea').each( function(){
                    if ($(this).val() == '') {
                        alert('Fill in the field"'+$(this).attr('placeholder')+'"!');
                        error = true;
                    }
                });
                if (!error) {
                    var data = form.serialize();
                    $.ajax({
                        type: "POST",
                        url: "/rest/interviewers",
                        dataType: 'json',
                        data: data, // дaнныe для oтпрaвки
                        beforeSend: function(data) {
                            form.find('input[type="submit"]').attr('disabled', 'disabled');
                        },
                        success: function(data){
                            if (data['error']) {
                                alert(data['error']);
                            } else {
                                alert("Data has been sent!");
                            }
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                            alert(xhr.status);
                            alert(thrownError);
                        },
                        complete: function(data) { // сoбытиe пoслe любoгo исхoдa
                            form.find('input[type="submit"]').prop('disabled', false);
                        }

                    });
                }
                return false;
            });
        });

    </script>
</head>
<form method="post" action="" id="interviewerForm"> <br />
    <input type="text" size="32" maxlength="36" name="name" placeholder="Вaшe имя" val=""> <br />
    <input type="text" size="32" maxlength="36" name="email" placeholder="Вaш email" val=""> <br />
    <input type="submit" value="GO GO GO"/>
</form>
</html>