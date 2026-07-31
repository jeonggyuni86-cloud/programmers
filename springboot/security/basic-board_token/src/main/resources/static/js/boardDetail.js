$(document).ready(() => {
    loadBoardDetail();
});


let editArticle = () => {
    let resourceId = $('#hiddenId').val();
    window.location.href = "/update/" + resourceId;
}


let deleteArticle = () => {

    let resourceId = $('#hiddenId').val();

    $.ajax({
        type: 'DELETE',
        url: '/api/boards/' + resourceId,

        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('accessToken')
        },

        success: () => {
            alert('게시글이 삭제되었습니다.');
            window.location.href = '/';
        },

        error: (error) => {
            console.error(error);
            alert('게시글 삭제 중 오류가 발생했습니다.');
        }
    });
}



let loadBoardDetail = () => {

    let hId = $('#hiddenId').val();
    let hUserId = $('#hiddenUserId').val();


    $.ajax({

        type: 'GET',

        url: '/api/boards/' + hId,

        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('accessToken')
        },


        success: (response) => {

            $('#title').text(response.title);
            $('#content').text(response.content);
            $('#userId').text(response.userId);
            $('#created').text(response.created);


            if (hUserId !== response.userId) {
                $('#editBtn').prop('disabled', true);
                $('#deleteBtn').prop('disabled', true);
            }


            $('#fileList').empty();


            if (response.filePath && response.filePath.length > 0) {

                let filePath = response.filePath;

                $('#hiddenFilePath').val(filePath);


                let normalized = filePath.replace(/\\/g, '/');

                let fileName =
                    normalized.substring(
                        normalized.lastIndexOf('/') + 1
                    );


                $('#fileList').append(
                    `
                    <li>
                        <a href="/api/boards/file/download/${fileName}">
                            ${fileName}
                        </a>
                    </li>
                    `
                );

            } else {

                $('#fileList').append(
                    '<li>첨부된 파일이 없습니다.</li>'
                );

            }


            // 댓글 데이터가 DTO에 포함되어 있으면 출력
            renderComments(response.comments || []);

        },


        error: (error) => {

            console.error(error);

            alert(
                '상세 데이터를 불러오는데 오류가 발생했습니다.'
            );

        }

    });

};



let renderComments = (comments) => {

    const $list = $('#commentList');

    $list.empty();


    $('#commentCount').text(
        comments.length > 0
            ? comments.length
            : ''
    );


    if (comments.length === 0) {

        $list.append(
            '<li class="no-comment">아직 댓글이 없습니다.</li>'
        );

        return;
    }



    comments.forEach((c) => {

        $list.append(
            `
            <li class="comment-item">

                <div class="comment-meta">

                    <strong>${c.userId}</strong>

                    <span class="comment-date">
                        ${c.created}
                    </span>

                </div>


                <p class="comment-content">
                    ${c.content}
                </p>

            </li>
            `
        );

    });

};




let submitComment = () => {

    let hId = $('#hiddenId').val();

    let hUserId = $('#hiddenUserId').val();

    let content = $('#commentContent').val();



    if (!content || content.trim() === '') {

        alert('댓글 내용을 입력해주세요.');

        return;
    }



    $.ajax({

        type: 'POST',

        url: '/api/boards/' + hId + '/comments',


        headers: {
            Authorization: 'Bearer ' + localStorage.getItem('accessToken')
        },


        contentType: 'application/json',


        data: JSON.stringify({

            userId: hUserId,

            content: content

        }),


        success: () => {

            $('#commentContent').val('');

            loadBoardDetail();

        },


        error: (error) => {

            console.error(error);

            alert('댓글 등록 중 오류가 발생했습니다.');

        }

    });

};