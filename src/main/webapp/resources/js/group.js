$(document).ready(function() {
    var userPrincipal = $("#userPrincipal").val();
    var groupId = $("#groupId").val();
    var defTemplateId = "57063f7e866dbe18343aca6a";

    $('body').on('click', '.dayRow', function() {
        var x = $(this).attr("id");
        var sd = x.substr(1);
        if ($("." + sd).is(":visible")) {
            $("." + sd).hide();
        } else {
            $("." + sd).show();
        }
    });

    $('body').on('change', '.selectTemplate', function() {
        var chosenTemplate = $(this).val();
        console.log("***** CHOSEN TEMPLATE: " + chosenTemplate);
        $('.interviewTemplate').val(chosenTemplate);
    });

    //get current interviewer
    $.ajax({
        type: "GET",
        url: "/rest/interviewers/" + userPrincipal + "/dto",
        success: function(result) {
            var interviewer = JSON.stringify(result);
            console.log("interviewer JSON: " + interviewer);
            interviewer = JSON.parse(interviewer);

            //get templates
            $.ajax({
                type: "GET",
                url: "/rest/templates/interviewers/" + interviewer.id,
                success: function(result) {
                    var templates = JSON.stringify(result);
                    console.log("templates JSON: " + templates);
                    templates = JSON.parse(templates);

                    $.each(templates, function(i, template) {
                        $(".selectTemplate").append(
                            '<option value="' + template.id + '">' + template.name + '</option>'
                        );
                    });
                }
            });

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
                        url: "/rest/groups/" + groupId + "/interviewer/" + interviewer.id + "/days",
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
                                    var fullName = candidate.fullName;
                                    var candidateDate = new Date(candidate.date);
                                    var showTime = candidateDate.getHours() + ":" + candidateDate.getMinutes();
                                    $("#groupTable").append(
                                        '<tr class="iw-' + i + '">' +
                                            '<td><a href="/web/candidates/' + candidate.id + '">' + fullName + '</a></td>' +
                                            '<td>' + showTime + '</td>' +
                                            '<td><form action="/interview/' + candidate.interviewId + '" method="POST">' +
                                                    '<input type="hidden" name="template_id" class="interviewTemplate" ' +
                                                    'value="' + defTemplateId + '"/>' +
                                                    '<input type="hidden" name="group_name" value="' + group.name +'"/>' +
                                                    '<input type="image" src="../../../resources/images/icons/Skull-48.png" style="height: 1.5em"/>' +
                                                    //'style="height: 1.8em;/>' +
                                                    //'<input type="submit" value="start"/>' +
                                            '</form></td>' +
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