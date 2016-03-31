$(document).ready(function () {
    var userPrincipal = $("#userPrincipal").val();

    $.ajax({
        type: "GET",
        url: "/rest/groups/dto",    //all groups so far (not wired with Interviewer)
        success: function(result) {
            var groupDtoList = JSON.stringify(result);
            console.log("groupDtoList JSON: " + groupDtoList);
            groupDtoList = JSON.parse(groupDtoList);

            $.each(groupDtoList, function(i, groupDto) {
                $("#groupsTable > tbody").append(
                    '<tr>' +
                        '<td>' + groupDto.name + '</td>' +
                        '<td><a href="/web/groups/' + groupDto.id + '">&gt;</a></td>' +
                    '</tr>'
                );
            });
        }
    });
});