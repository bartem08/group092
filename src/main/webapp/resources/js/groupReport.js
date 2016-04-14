$(document).ready(function() {
    var id = $("#idHidden").val();
    var userPrincipal = $("#userPrincipal").val();
     $.ajax({
         type: "GET",
         url: "/rest/groups/" + id,
         success: function (result) {
             var group = JSON.stringify(result);
             group = JSON.parse(group);
             $("#groupNameSpan").text(group.name);
         },
         async: false
     });
    $.ajax({
        type: "GET",
        url: "/rest/groups/" + id + "/" + userPrincipal + "/" + "/groupReport/",
        success: function (result) {
            console.log("RESULT: " + JSON.stringify(result));
            var groupReport = JSON.stringify(result);
            groupReport = JSON.parse(groupReport);
            $.each(groupReport, function (i, candidateReport) {
                var candRank = 100;
                $("#reportTable").append('<tr><td><a href="/web/candidates/' + candidateReport.id + '">' + candidateReport.fullName +
                '</a></td><td>' + candidateReport.result +
                '</td></tr>');
            });
        },
        async: false
    });
});

