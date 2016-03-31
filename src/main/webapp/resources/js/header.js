$(document).ready(function() {
   //hdrDropdownSchedule
    $.ajax({
        type: "GET",
        url: "/rest/groups/dto",    //all groups so far (not wired with Interviewer)
        success: function(result) {
            var groupDtoList = JSON.stringify(result);
            console.log("groupDtoList JSON: " + groupDtoList);
            groupDtoList = JSON.parse(groupDtoList);

            $.each(groupDtoList, function(i, groupDto) {
                $("#hdrDropdownSchedule").append(
                    '<li>' +
                        '<td><a href="/web/groups/' + groupDto.id + '">' + groupDto.name + '</a></td>' +
                    '</li>'
                );
            });
        }
    });
});