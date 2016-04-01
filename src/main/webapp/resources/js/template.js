/**
 * Created by Levchynskyi Serhii on 28.03.2016.
 */

// Function to display all the questions from the chosen template
function getQuestions(id, name) {
    console.log("Function getQuestion binded to template dropdown fired");
    $("#activeTemplate").html(name);
    $("#activeTemplateId").val(id);
    console.log("Template id: " +  id + "  ---- Template name:"+ name);
    $.ajax({
        type: "GET",
        url: "/rest/templates/" + id + "/questions",
        success: function (result) {
            console.log("URL for REST: /rest/templates/" + id + "/questions");
            var questions = JSON.stringify(result);
            questions = JSON.parse(questions);
            $("#tbodyId").empty();
            $.each(questions, function (i, question) {
                console.log("---" + question.id + "---" + question.questionString);
                //Do we need separate question.jsp?
                $("#templateQuestions").append('<tr><td><a href="/web/questions/' + question.id + '">' +
                    question.questionString + '</a></td>' +
                    '<td><a id="editQuestion" class="glyphicon glyphicon-pencil">&nbsp;' +
                    '<a id="deleteQuestion" onclick="deleteQuestion(\'' + id + '\','
                    + '\'' + question.id + '\')" class="glyphicon glyphicon-trash" ></td></tr>');
            });
        }
    });
}

function deleteQuestion(templateId, questionId){
    console.log("Function deleteQuestion binded to ref deleteQuestion fired");
    console.log("Template Id is: " + templateId + " Question Id is: " + questionId);
    $.ajax({
        type: "DELETE",
        url: "/rest/templates/" + templateId + "/questions/" + questionId,
        success: function() {
            console.log("/rest/templates/" + templateId + "/questions/" + questionId);
            getQuestions(templateId, $("#activeTemplate").html());
        },
        error: function(result) {
            console.log(result);
        }
    })
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
                $(".selectTemplate").append('<li onclick="getQuestions(\'' + template.id + '\', \'' + template.name + '\')">' +
                    '<a class="chosenTemplate" href="#">' + template.name + '</a></li>');
            });
        }
    });

    $("#addQuestion").click(function () {
        console.log("Function addQuestion binded to button Add question fired");
        var templateId = $("#activeTemplateId").val();
        var questionString = $("#textArea").val();
        var maxValue = $("#maxValue").val();
        var question = {
            questionString: questionString,
            maxQuestionValue: maxValue,
        }
        console.log(question);

        $.ajax({
            type: "POST",
            url: "/rest/questions/",
            data: JSON.stringify(question),
            contentType: "application/json;",
            success: function (createdQuestion, textStatus, request) {
                var questionUrl = request.getResponseHeader('Location')
                console.log(questionUrl)
                console.log("Done creating question");
                $.ajax({
                    type: "POST",
                    url: "/rest/templates/" + templateId + "/questions",
                    data: JSON.stringify(createdQuestion),
                    contentType: "application/json;",
                    success: function () {
                        console.log("Done saving created question to template");
                        getQuestions(templateId, $("#activeTemplate").html());
                    },
                    error: function () {
                        console.log("Saving question to template failed");
                    }
                });
            },
            error: function () {
                console.log("failed");
            }
        });
    });
});