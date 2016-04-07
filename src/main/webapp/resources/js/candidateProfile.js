$(document).ready(function() {
    var id = $("#idHidden").val();
    $.ajax({
        type: "GET",
        url: "/rest/candidates/" + id,
        success: function(result) {
            console.log("RESULT: " + JSON.stringify(result));
            var candidate = JSON.stringify(result);
            candidate = JSON.parse(candidate);
            var surname = candidate.lastName;
            var name = candidate.firstName;
            var englishLevel = candidate.englishLevel;
            var email = candidate.email;
            var skype = candidate.skype;
            var phone = candidate.phoneNumber;
            $("#surname").val(surname);
            $("#name").val(name);
            $("#englishLevel").html(englishLevel);
            $("#eMail").val(email);
            $("#skype").val(skype);
            $("#phone").val(phone);
        }
    });

    $("#save").click(function() {
        console.log("Save function triggered")
        var candidate = {
            lastName : $("#surname").val(),
            firstName : $("#name").val(),
            englishLevel : $("#englishLevel").html(),
            email : $("#eMail").val(),
            skype : $("#skype").val(),
            phoneNumber : $("#phone").val(),
        };
        console.log(candidate);
        var id =$("#idHidden").val();
        var url = "/rest/candidates/"+ id;
        $.ajax({
            type: "PUT",
            data: JSON.stringify(candidate),
            contentType: "application/json;",
            url: url,
            success: function () {
                console.log("Success");
                $("#message").html("Saved");
            },
            error: function () {
                console.log("Error");
                $("#message").html("Can't save");
            }
        });
    });
});



