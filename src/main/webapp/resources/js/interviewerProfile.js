$(document).ready(function() {
    var userPrincipal = $("#userPrincipal").val();
    $.ajax({
        type: "GET",
        url: "/rest/interviewers/login/" + userPrincipal,
        success: function(result) {
            var interviewer = JSON.stringify(result);
            console.log("interviewer JSON: " + interviewer);
            interviewer = JSON.parse(interviewer);
            $("#interviewerId").val(interviewer.id);
            var firstName = interviewer.firstName;
            var lastName = interviewer.lastName;
            var mail = interviewer.email;
            var skype = interviewer.skype;
            var phone = interviewer.phone;
            var groups = interviewer.groups;
            var templates = interviewer.templates;
            $("#fName").val(firstName);
            $("#lName").val(lastName);
            $("#eMail").val(mail);
            $("#skype").val(skype);
            $("#phone").val(phone);
/*            $.each(groups, function(i, group){
                $("#groups").append(group.name);
            });*/
            $("#templates").html('<a href = "/web/templates">Templates</a>');
/*            $.each(templates, function(i, template){
                $("#templates").append(template.name);
            });*/

        }
    });

    $("#save").click(function() {
        console.log("Button save function fired");
        var interviewer = {
            firstName : $("#fName").val(),
            lastName : $("#lName").val(),
            email : $("#eMail").val(),
            skype : $("#skype").val(),
            phone : $("#phone").val()
        };
        $.ajax({
                type: "PUT",
                data: JSON.stringify(interviewer),
                contentType: "application/json;",
                url: "/rest/interviewers/" + $("#interviewerId").val(),
            })
            .done(function () {
                $("#message").html("Saved");
            })
            .fail(function () {
                $("#message").html("Can't save");
            })
    });
});


