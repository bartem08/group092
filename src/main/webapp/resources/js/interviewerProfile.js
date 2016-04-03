$(document).ready(function() {
    var id = $("#idHidden").val();
    $.ajax({
        type: "GET",
        url: "/rest/interviewers/" + id,
        success: function(result) {
            console.log("RESULT: " + JSON.stringify(result));
            var interviewer = JSON.stringify(result);
            interviewer = JSON.parse(interviewer);
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
            $.each(groups, function(group){
                $("#groups").append().html(group.name);
            });
            $.each(templates, function(template){
                $("#templates").append().html(template.name);
            });

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
                url: "/rest/interviewers/56f82895d39f2411782b4344"
            })
            .done(function () {
                $("#message").html("Saved");
            })
            .fail(function () {
                $("#message").html("Can't save");
            })
    });
});


