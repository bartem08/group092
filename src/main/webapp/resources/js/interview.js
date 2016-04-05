$(function () {

    var  dialog, form, template_id, comments_selector;

    comments_selector = ".custom-comments";

    template_id = $("#template-id").val();

    dialog = $( "#dialog-form" ).dialog({
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

    form = dialog.find("form").on("submit", function(event) {
            event.preventDefault();
            var question_comment = $("#text-question-comment").val() + "\n";
            var comments = $(comments_selector).find("textarea").val();
            comments = comments + question_comment;
            $(comments_selector).find("textarea").text(comments);
    });
    $.ajax({
        type: "GET",
        url: "/rest/templates/" + template_id,
        success: function(result) {
            var template_name = JSON.parse(JSON.stringify(result.name));
            $("#template-name").text(template_name);
            var questions = JSON.parse(JSON.stringify(result.questions));
            $.each(questions, function(i, question) {

                var q_id = question.id,
                    range_selector = "#" + q_id,
                    input_selector = "#value_" + q_id,
                    btn_selector = "#btn_" + q_id,
                    questionString = question.questionString;

                $("#question-table").find("tbody").append(
                    '<tr>' +
                        '<td>' + question.questionString + '</td>' +
                        '<td>' +
                            '<div id="' + q_id + '""></div>' +
                            '<input form="interview" name="values"' +
                                ' class="custom-input" id="value_' + q_id + '" readonly type="text"/>' +
                        '</td>' +
                        '<td>' +
                            '<button class="btn btn-xs btn-primary"' +
                                ' type="submit" id="btn_'+q_id+'">Add Comment</button>' +
                        '</td>' +
                        '<td>' +
                            '<input form="interview" name="skipped" type="checkbox" value="' + q_id + '"/>' +
                        '</td>'+
                    '</tr>'
                );

                $(range_selector).slider({
                    range: "max",
                    min: 0,
                    max: question.maxQuestionValue,
                    value: 0,
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
            });
            $(".loading").remove();
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

xt = setInterval("tm()", 1000), xt2 = 0, xt3 = 0;
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


