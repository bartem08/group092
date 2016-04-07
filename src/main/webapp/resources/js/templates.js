/**
 * Created by Levchynskyi Serhii on 28.03.2016.
 */
var userLogin;
var userId;
var user;

$(document).ready(function () {
    userLogin = $("#userLogin").val();
    $.ajax({
        type: "GET",
        url: "/rest/interviewers/"+ userLogin + "/dto",
        success: function(interviewer) {
            console.log(interviewer);
            userId = interviewer.id;
            user = interviewer;
            console.log("interviewer JSON: " + JSON.stringify(user));
            console.log(userLogin + "---" + userId);
            loadTemplates();
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
        var questionToEditId= $("#editQuestionId");
        console.log(questionToEditId.val());
        if (questionToEditId.val()!=""){
            console.log("Question to edit ID: " + questionToEditId.val());
            $.ajax({
                type: "PUT",
                url: "/rest/questions/" + questionToEditId.val(),
                data: JSON.stringify(question),
                contentType: "application/json",
                success: function (){
                    console.log("Successfully edited question");
                    questionToEditId.val("");
                    getQuestions(templateId, $("#activeTemplate").html());
                }
            });
        } else {
            $.ajax({
                type: "POST",
                url: "/rest/questions/",
                data: JSON.stringify(question),
                contentType: "application/json",
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
        }
        $("#textArea").val("");
    });
});


function loadTemplates () {
    $(".selectedTemplates").find("li:gt(1)").remove();
    $.ajax({
        type: "GET",
        url: "/rest/templates/interviewers/" + userId,
        success: function (result) {
            console.log("/rest/templates/interviewers/" + userId);
            console.log(result);
            var templates = JSON.stringify(result);
            templates = JSON.parse(templates);
            console.log("TEMPLATES JSON: " + templates);
            $.each(templates, function (i, template) {
                $(".selectedTemplates").append('<li onclick="getQuestions(\'' + template.id + '\', \'' + template.name + '\')">' +
                    '<a class="chosenTemplate" href="#">' + template.name + '</a></li>');
            });
        }
    });
}

function addNewTemplate() {
    var template = prompt("Please enter template's name", "");
    if (template != null) {
        var name = template;
        var newTemplate = {
            name: name,
            questions: null,
            favourite: false,
            interviewer: user,
        }
        console.log(newTemplate)
        $.ajax({
            type: "POST",
            url: "/rest/templates/",
            data: JSON.stringify(newTemplate),
            contentType: "application/json",
            success: function () {
                console.log("Template created");
                loadTemplates();
            }
        });
    }
}

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
                $("#templateQuestions").append('<tr><td><a id="questionString" href="/web/questions/' + question.id + '">' +
                    question.questionString + '</a></td>' +
                    '<td><a id="editQuestion" onclick="editQuestion(\'' + id + '\','
                    + '\'' + question.id + '\',' + '\'' + question.questionString + '\')" class="glyphicon glyphicon-pencil">&nbsp;' +
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
            $.ajax({
                type: "DELETE",
                url: "/rest/questions/" + questionId,
                success: function() {
                    console.log("/rest/questions/" + questionId);
                    getQuestions(templateId, $("#activeTemplate").html());
                },
                error: function(result) {
                    console.log(result);
                }
            });
        },
        error: function(result) {
            console.log(result);
        }
    });
}

function editQuestion(templateId, questionId, questionString){
    console.log("Function deleteQuestion binded to ref deleteQuestion fired");
    console.log("Template Id is: " + templateId + " Question Id is: " + questionId +
        " QuestionString is: " + questionString);
    $("#textArea").val(questionString);
    $("#editQuestionId").val(questionId);
}


