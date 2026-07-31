let selectedFile = null;


$(document).ready(() => {

    saved();

    fileChanged();

});



// JWT Header
const getAuthHeader = () => {

    const token =
        localStorage.getItem('accessToken');


    if (!token) {

        return {};

    }


    return {

        Authorization:
            'Bearer ' + token

    };

};





let saved = () => {


    $('#submitBtn').on('click', (event) => {


        event.preventDefault();



        let formData =
            new FormData(
                $('#writeForm')[0]
            );




        $.ajax({


            type: 'POST',


            url: '/api/boards',



            headers: getAuthHeader(),



            data: formData,



            processData: false,



            contentType: false,



            success: () => {


                alert(
                    '게시글이 성공적으로 등록되었습니다!'
                );


                window.location.href = '/';

            },



            error: (error) => {


                console.error(
                    '오류 발생:',
                    error
                );



                if (error.status === 401) {


                    alert(
                        '로그인이 필요합니다.'
                    );



                    localStorage.removeItem(
                        'accessToken'
                    );


                    localStorage.removeItem(
                        'refreshToken'
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






let fileChanged = () => {


    $('#file').on(
        'change',
        function(e) {


            const file =
                e.target.files[0];



            selectedFile = file;



            updateFileList();


        }
    );


};






let updateFileList = () => {


    $('#fileList').empty();



    if (!selectedFile) {

        return;

    }



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



    $('.remove-btn').on(
        'click',
        function() {


            selectedFile = null;


            $('#file').val('');


            updateFileList();


        }
    );


};