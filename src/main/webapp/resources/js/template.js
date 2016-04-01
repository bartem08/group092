/**
 * Created by Levchynskyi Serhii on 28.03.2016.
 */
function getQuestions(id, name) {
    $("#activeTemplate").html(name);
    $("#activeTemplateId").val(id);
    console.log( id + "----" + name + "----" + $("#activeTemplateId").val());
    console.log("Function getQuestion binded to template dropdown fired");
    $.ajax({
        type: "GET",
        url: "/rest/templates/" + id + "/questions",
        success: function (result) {
            console.log("/rest/templates/" + id + "/questions");
            console.log(result);
            var questions = JSON.stringify(result);
            questions = JSON.parse(questions);
            console.log(questions);
            $("#tbodyId").empty();
            $.each(questions, function (i, question) {
                console.log("---" + question.id + "---" + question.questionString);
                $("#templateQuestions").append('<tr><td><a href="/rest/templates/questions/' + question.id + '">' + question.questionString + '</a></td></tr>');
            });
        }
    });
}



$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "/rest/templates/",
        success: function (result) {
            var templates = JSON.stringify(result);
            templates = JSON.parse(templates);
            console.log("TEMPLATES JSON: " + templates);
            $.each(templates, function (i, template) {
                $(".selectTemplate").append('<li onclick="getQuestions(\'' + template.id + '\', \'' + template.name + '\')"><input type="hidden" id="templateId" value="' + template.id + '">' +
                    '<a class="chosenTemplate" href="#">' + template.name + '</a></li>');
            });
        }
    });

    $("#addQuestion").click(function () {
        console.log("Function addQuestion binded to butoon Add question fired");
        var templateId = $("#activeTemplateId").val();
        var questionString = $("#textArea").val();
        var maxValue = $("#maxValue").val();
        var question = {
            questionString: questionString,
            maxQuestionValue: maxValue,
        }
        $.ajax({
            type: "POST",
            url: "/rest/templates/" + templateId + "/questions",
            data: JSON.stringify(question),
            contentType: "application/json;",
            success: function () {
                console.log("done");
            },
            error: function () {
                console.log("failed");
            }
        });
    });


});