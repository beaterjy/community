function postComment() {
    var id = $("#questionId").val();
    var content = $("#content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId": id,
            "type": 1,
            "content": content
        }),
        success: function (response) {
            if (response.code === 200 ) {
                $(".reply").hide();
            } else {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}