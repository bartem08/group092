$(document).ready(function() {
    var userPrincipal = $("#userPrincipal").val();
    var groupId = $("#groupId").val();

    $.ajax({
        type: "GET",
        url: "/rest/groups/" + groupId + "/dto",
        success: function(result) {
            var group = JSON.stringify(result);
            console.log("group JSON: " + group);
            group = JSON.parse(group);

            $("#groupName").text(group.name);
        }
    });
});