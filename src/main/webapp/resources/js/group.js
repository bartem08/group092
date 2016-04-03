$(document).ready(function() {
    var userPrincipal = $("#userPrincipal").val();
    var groupId = $("#groupId").val();

    $('body').on('click', '.dayRow', function() {
        var x = $(this).attr("id");
        var sd = x.substr(1);
        if ($("." + sd).is(":visible")) {
            $("." + sd).hide();
        } else {
            $("." + sd).show();
        }
    });

    //get current interviewer
    $.ajax({
        type: "GET",
        url: "/rest/interviewers/login/" + userPrincipal,
        success: function(result) {
            var interviewer = JSON.stringify(result);
            console.log("interviewer JSON: " + interviewer);
            interviewer = JSON.parse(interviewer);

            //get current group
            $.ajax({
                type: "GET",
                url: "/rest/groups/" + groupId + "/dto",
                success: function(result) {
                    var group = JSON.stringify(result);
                    console.log("group JSON: " + group);
                    group = JSON.parse(group);

                    $("#groupName").text(group.name);

                    //get group days
                    $.ajax({
                        type: "GET",
                        url: "/rest/groups/" + groupId + "/days",
                        success: function(result) {
                            var groupDayDtoList = JSON.stringify(result);
                            console.log("groupDayDtoList JSON: " + groupDayDtoList);
                            groupDayDtoList = JSON.parse(groupDayDtoList);

                            //fill table
                            $.each(groupDayDtoList, function(i, day) {
                                //console.log("DAY: " + day);

                                var dayDate = new Date(day.date);
                                var showDate = dayDate.getDate() +  "." + (dayDate.getMonth() + 1) + "." +
                                        dayDate.getFullYear();
                                $("#groupTable").append(
                                    '<tr class="dayRow" id="diw-' + i + '">' +
                                        '<td colspan="3" style="background-color: #2f4f4f">' + showDate + '</td>' +
                                    '</tr>'
                                );

                                $.each(day.candidates, function(j, candidate) {
                                    var fullName = candidate.surname + " " + candidate.name;
                                    var candidateDate = new Date(candidate.date);
                                    var showTime = candidateDate.getHours() + ":" + candidateDate.getMinutes();
                                    $("#groupTable").append(
                                        '<tr class="iw-' + i + '">' +
                                            '<td><a href="/web/candidates/' + candidate.id + '">' + fullName + '</a></td>' +
                                            '<td>' + showTime + '</td>' +
                                            '<td><a href="/web/interviews/' + 0 + '">' +
                                        '<img src="../../../resources/images/icons/Skull-48.png" ' +
                                        'alt="&gt;" style="height: 1.5em" title="interview"/>' + '</a></td>' +
                                        '</tr>'
                                    );
                                });
                            });

                        }
                    });
                }
            });
        }
    });
});