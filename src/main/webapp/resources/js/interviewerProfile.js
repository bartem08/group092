$(document).ready(function() {
    var userLogin = $("#userLogin").val();
    var userId;
    $.ajax({
        type: "GET",
        url: "/rest/interviewers/"+ userLogin + "/dto",
        success: function(result) {
            var interviewer = JSON.stringify(result);
            console.log("interviewer JSON: " + interviewer);
            interviewer = JSON.parse(interviewer);
            userId = interviewer.id;

            var firstName = interviewer.firstName;
            var lastName = interviewer.lastName;
            var mail = interviewer.email;
            var skype = interviewer.skype;
            var phone = interviewer.phone;

            $("#fName").val(firstName);
            $("#lName").val(lastName);
            $("#eMail").val(mail);
            $("#skype").val(skype);
            $("#phone").val(phone);
            $("#templates").html('<a href = "/web/templates">Templates</a>');
        }
    });

    $("#save").click(function() {
        console.log("Button save function fired");
        var interviewer = {
            firstName : $("#fName").val(),
            lastName : $("#lName").val(),
            email : $("#eMail").val(),
            skype : $("#skype").val(),
            phone : $("#phone").val(),
            login : userLogin,
        };
        $.ajax({
                type: "PUT",
                data: JSON.stringify(interviewer),
                contentType: "application/json;",
                url: "/rest/interviewers/" + userId,
            })
            .done(function () {
                console.log("/rest/interviewers/" + userId);
                $("#message").html("Saved");
            })
            .fail(function () {
                $("#message").html("Can't save");
            })
    });
});


