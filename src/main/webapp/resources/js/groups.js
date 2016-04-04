$(document).ready(function () {
    var userPrincipal = $("#userPrincipal").val();

    $.ajax({
        type: "GET",
        url: "/rest/interviewers/login/" + userPrincipal,
        success: function(result) {
            var interviewer = JSON.stringify(result);
            console.log("interviewer JSON: " + interviewer);
            interviewer = JSON.parse(interviewer);

            $.each(interviewer.groups, function(i, group) {
                $("#groupsTable > tbody").append(
                    '<tr>' +
                    '<td>' + group.name + '</td>' +
                    '<td><a href="/web/groups/' + group.id + '"><img src="../../../resources/images/icons/Clock-50.png" ' +
                    'alt="&gt;" style="height: 1.8em" title="day"/></a></td>' +
                    '</tr>'
                );
            });
        }
    });
});