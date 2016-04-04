$(function () {

    var template_id = $("#template-id").val();

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

                $("#question-table").find("> tbody").append(
                    '<tr>' +
                    '<td>' + question.questionString + '</td>' +
                    '<td><div id="' + q_id + '""></div>' +
                    '<input class="custom-input" id="value_' + q_id + '" readonly type="text" /></td>' +
                    '<td><button class="btn btn-xs btn-primary" type="submit" id="btn_'+q_id+'">Add Comment</button></td>' +
                    '<td><input type="checkbox" name="skipped" /></td>'+
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
        }
    });

    $("#add-common-comment").click(function(){
        var comments = $(".custom-comments > textarea").val();
        var comment = $("#text-common-comment").val() + "\n";
        comments = comments + comment;
        $(".custom-comments > textarea").text(comments);

    });

    var dialog, form;
    function addComment() {
        var comments = $(".custom-comments > textarea").val();
        var question_comment = $("#text-question-comment").val() + "\n";
        comments = comments + question_comment;
        $(".custom-comments > textarea").text(comments);
        dialog.dialog("close");
    }
    dialog = $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 200,
        width: 350,
        modal: true,
        buttons: {
            "Add": addComment,
            Cancel: function() {
                dialog.dialog("close");
            }
        },
        close: function() {
            form[ 0 ].reset();
        }
    });

    form = dialog.find("form").on("submit", function(event) {
        event.preventDefault();
        addComment();
    });


});
var xt = setInterval("tm()", 1000);
var xt2 = 0;
var xt3 = 0;
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
