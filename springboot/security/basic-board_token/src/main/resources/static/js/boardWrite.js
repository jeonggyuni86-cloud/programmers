let selectedFile = null; // 파일은 1개만 선택 가능


$(document).ready(() => {

    saved();

    fileChaged();

});



let saved = () => {


    $('#submitBtn').on('click', (event) => {


        event.preventDefault();


        let formData =
            new FormData($('#writeForm')[0]);



        $.ajax({

            type: 'POST',

            url: '/api/boards',


            headers: {

                Authorization:
                    'Bearer ' + localStorage.getItem('accessToken')

            },


            data: formData,


            processData: false,


            contentType: false,



            success: function(response) {


                alert(
                    '게시글이 성공적으로 등록되었습니다!'
                );


                window.location.href = '/';

            },



            error: function(error) {


                console.error(
                    '오류 발생:',
                    error
                );


                if (error.status === 401) {

                    alert(
                        '로그인이 필요합니다.'
                    );

                    window.location.href =
                        '/members/login';

                    return;

                }


                alert(
                    '게시글 등록 중 오류가 발생하였습니다.'
                );

            }

        });


    });


};





let fileChaged = () => {


    $('#file').on('change', function(e) {


        const file =
            e.target.files[0];


        selectedFile = file;


        updateFileList();

    });


};





let updateFileList = () => {


    $('#fileList').empty();



    if (selectedFile) {


        $('#fileList').append(

            `
            <li>
                ${selectedFile.name}

                <button 
                    type="button"
                    class="remove-btn">
                    X
                </button>

            </li>
            `

        );



        $('.remove-btn').on('click', function () {


            selectedFile = null;


            $('#file').val('');


            updateFileList();


        });


    }


};