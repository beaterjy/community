/**
 * 上传评论到服务器
 * @param type
 * @param id
 */
function postComment(type, id) {
    var content;
    if (type === 1) {
        content = $("#content").val();
    } else if (type === 2) {
        content = $("#subCommentContent-" + id).val();
    } else {
        alert("回复类型错误!");
        return;
    }

    if (!content) {
        alert("请输入回复内容");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": id,
            "type": type,
            "content": content
        }),
        success: function (response) {
            if (response.code === 200) {
                // 页面简单刷新一下
                window.location.reload();
            } else {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}

function collapseSubCommentsById(id) {
    var subCommentsBlock = "#subCommentsBlock-" + id;
    var commentList = $("<div/>", {"id": "commentList"});
    var switchON = "#switch-" + id;
    var on = "data-switchON";

    if ($(switchON).attr(on) === "false") {
        // 激活开关
        $(switchON).attr(on, "true");
        $(switchON).addClass("active-blue");
        // 展开
        $(subCommentsBlock).collapse("show");
        // TODO:请求数据
        $.getJSON("/sub_comments/" + id, function (data) {

            $.each(data.data, function (index, comment) {
                var media = $("<div/>", {"class": "media border-bottom"});
                var mediaLeft = $("<div/>", {
                    "class": "media-left"
                }).append($("<a/>").append(
                    $("<img/>", {
                        "class": "media-object w40 h40",
                        "src": comment.user.avatarUrl
                    })
                ));
                var mediaBody = $("<div/>", {
                    "class": "media-body"
                }).append(
                    $("<h5/>", {
                        "class": "media-heading"
                    }).append(
                        $("<a/>", {
                            "class": "font12",
                            "html": comment.user.name,
                            "href": "#",
                        })
                    ).append($("<span/>", {
                            "class": "color999 font12",
                            "html": " • " + moment(comment.gmtCreate).format('YYYY-MM-DD hh:mm:ss')
                        }
                    ))
                );
                mediaBody.append(
                    $("<p/>", {"html": comment.content})
                );
                media.append(mediaLeft).append(mediaBody);

                commentList.append(media);
            });
            $(subCommentsBlock).prepend(commentList);

        });
    } else if ($(switchON).attr(on) === "true") {
        // 关闭开关
        $(switchON).attr(on, "false");
        $(switchON).removeClass("active-blue");
        // 折叠
        $("#commentList").remove();
        $(subCommentsBlock).collapse("hide");
    } else {
        alert("开关参数错误。");
        return;
    }

}


function showTagBox() {
    $(".tag-box").show();
}

/**
 * 选择标签，填充到标签栏
 * @param elem
 */
function selectTag(elem) {
    var tag = $(elem).attr("data-name");
    var input = $("#tag");
    if (input.val().length === 0) {
        input.val(tag);
    } else {
        if (input.val().indexOf(tag) === -1) {
            input.val(input.val() + "," + tag);
        }
    }
}


