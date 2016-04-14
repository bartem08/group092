$(document).ready(function () {
    var userPrincipal = $("#userPrincipal").val();

    $.ajax({
        type: "GET",
        url: "/rest/interviewers/" + userPrincipal + "/dto",
        success: function(result) {
            var interviewer = JSON.stringify(result);
            console.log("interviewer JSON: " + interviewer);
            interviewer = JSON.parse(interviewer);

            $("#interviewerProfile").attr("href", "/web/interviewer");

            $.ajax({
                type: "GET",
                url: "/rest/groups/interviewer/" + interviewer.id + "/dto",
                success: function(result) {
                    var groupDtoList = JSON.stringify(result);
                    console.log("groupDtoList JSON: " + groupDtoList);
                    groupDtoList = JSON.parse(groupDtoList);

                    $.each(groupDtoList, function(i, group) {
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
        }
    });
});