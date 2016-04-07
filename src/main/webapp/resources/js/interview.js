$(function () {

    var int_id = $("#int_id").val();
    var comments_selector = ".custom-comments";
    var dialog = $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 200,
        width: 350,
        modal: true,
        buttons: {
            "Add": addComment,
            "Cancel": function () {
                dialog.dialog("close");
            }
        },
        close: function() {
            form[ 0 ].reset();
        }
    });
    var form = dialog.find("form").on("submit", function(event) {
        event.preventDefault();
        var question_comment = $("#text-question-comment").val() + "\n";
        var comments = $(comments_selector).find("textarea").val();
        comments = comments + question_comment;
        $(comments_selector).find("textarea").text(comments);
    });
    var max;

    $.ajax({
        type: "GET",
        url: "/rest/interviews/" + int_id,
        success: function (result) {
            var candidate_fn = JSON.parse(JSON.stringify(result.candidate.firstName));
            var candidate_ln = JSON.parse(JSON.stringify(result.candidate.lastName));
            var interviewer_fn = JSON.parse(JSON.stringify(result.interviewer.firstName));
            var interviewer_ln = JSON.parse(JSON.stringify(result.interviewer.lastName));
            var questions = JSON.parse(JSON.stringify(result.questions));
            $("#candidate-firstname").text(candidate_fn);
            $("#candidate-lastname").text(candidate_ln);
            $("#interviewer-firstname").text(interviewer_fn);
            $("#interviewer-lastname").text(interviewer_ln);
            $.each(questions, function(i, question) {
                var q_id = question.question.id,
                    range_selector = "#" + q_id,
                    input_selector = "#value_" + q_id,
                    btn_selector = "#btn_" + q_id,
                    questionString = question.question.questionString;
                max += question.question.maxQuestionValue;
                $("#question-table").find("tbody").append(
                    '<tr>' +
                    '<td>' + questionString + '</td>' +
                    '<td>' +
                    '<div class="f_slider" id="' + q_id + '""></div>' +
                    '<input form="interview" name="values" value="' + question.finalQuestionValue + '"' +
                    ' class="custom-input" id="value_' + q_id + '" readonly type="text"/>' +
                    '</td>' +
                    '<td>' +
                    '<button class="btn btn-xs btn-primary"' +
                    ' type="submit" id="btn_'+q_id+'">Add Comment</button>' +
                    '</td>' +
                    '<td>' +
                    '<input class="f_check" id="ch_' + q_id +'" form="interview" name="skipped" type="checkbox" value="' + q_id + '"/>' +
                    '</td>'+
                    '</tr>'
                );

                $(range_selector).slider({
                    range: "max",
                    min: 0,
                    max: question.question.maxQuestionValue,
                    value: question.finalQuestionValue,
                    step: 0.1,
                    slide: function(event, ui) {
                        $(input_selector).val(ui.value);
                    }
                });

                $(input_selector).val($(range_selector).slider("value"));

                $(btn_selector).click(function (){
                    $("#text-question-comment").text(questionString + ": ");
                    dialog.dialog("open");
                });

                $("#ch_" + q_id).prop("checked", question.skipped);

            });

            $(".loading").remove();
            if (result.result > 0) {
                $(".f_slider").slider({
                    disabled: true
                });
                $(".f_check").prop("disabled", true);
                $("#tm1").remove();
                $("#res").text("Finished");
                $("#f_finish").text("Update");
            }
            $(comments_selector).find("textarea").text(result.comments);

            $(".custom-bar").css("width", result.result + "%");

            $(".result").text(result.result);
        }
    });

    $("#add-common-comment").click(function(){
        var comments = $(comments_selector).find("textarea").val();
        var common_comment = $("#text-common-comment").val() + "\n";
        comments = comments + common_comment;
        $(comments_selector).find("textarea").text(comments);
    });

    function addComment() {
        var comments = $(comments_selector).find("textarea").val();
        var question_comment = $("#text-question-comment").val() + "\n";
        comments = comments + question_comment;
        $(comments_selector).find("textarea").text(comments);
        dialog.dialog("close");
    }

});

var xt = setInterval("tm()", 1000), xt2 = 0, xt3 = 0;
function tm() {
    xt2++;
    if(xt2 > 59) {
        xt3++;
        xt2 = 0;
    }
    if(xt2 > 9) {
        xt = xt2;
    } else {
        xt = "0" + xt2;
    }
    document.getElementById("tm1").innerHTML = xt3 + ":" + xt;
}