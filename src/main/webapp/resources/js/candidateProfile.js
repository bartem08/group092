$(document).ready(function() {
    var id = $("#idHidden").val();
    $.ajax({
        type: "GET",
        url: "/rest/candidates/" + id,
        success: function(result) {
            console.log("RESULT: " + JSON.stringify(result));
            var candidate = JSON.stringify(result);
            candidate = JSON.parse(candidate);
            var surname = candidate.surname;
            var name = candidate.name;
            var patronymic = candidate.patronymic;
            var mail = candidate.email;
            var skype = candidate.skype;
            var phone = candidate.phoneNumber;
            $("#surname").val(surname);
            $("#name").val(name);
            $("#patronymic").val(patronymic);
            $("#eMail").val(mail);
            $("#skype").val(skype);
            $("#phone").val(phone);
        }
    });

    $("#save").click(function() {
        console.log("Save function triggered")
        var candidate = {
            surname : $("#surname").val(),
            name : $("#name").val(),
            patronymic : $("#patronymic").val(),
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



