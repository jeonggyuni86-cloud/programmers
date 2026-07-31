let selectedFile = null;


$(document).ready(() => {

    loadBoardDetail();

    updated();

    fileChanged();

    $('#hiddenFileFlag').val(false);

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





let updated = () => {


    $('#submitBtn').on('click', (event) => {


        event.preventDefault();



        let hId =
            $('#hiddenId').val();



        let formData =
            new FormData(
                $('#writeForm')[0]
            );



        $.ajax({


            type: 'PUT',


            url:
                '/api/boards/' + hId,



            headers:
                getAuthHeader(),



            data:
            formData,



            processData:
                false,



            contentType:
                false,



            success: () => {


                alert(
                    '게시글이 성공적으로 수정되었습니다!'
                );


                window.location.href =
                    '/';


            },



            error: (error) => {


                console.error(
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
                    '게시글 수정 중 오류가 발생했습니다.'
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



            selectedFile =
                file;



            $('#hiddenFileFlag')
                .val(true);



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



            $('#hiddenFileFlag')
                .val(true);



            updateFileList();


        }
    );


};








let loadBoardDetail = () => {


    let hId =
        $('#hiddenId').val();



    $.ajax({


        type: 'GET',


        url:
            '/api/boards/' + hId,



        headers:
            getAuthHeader(),



        success: (response) => {



            $('#title')
                .val(response.title);



            $('#content')
                .val(response.content);



            $('#userId')
                .val(response.userId);



            $('#fileList')
                .empty();




            if (
                response.filePath &&
                response.filePath.length > 0
            ) {



                let filePath =
                    response.filePath;



                $('#hiddenFilePath')
                    .val(filePath);




                let normalized =
                    filePath.replace(/\\/g, '/');



                let fileName =
                    normalized.substring(
                        normalized.lastIndexOf('/') + 1
                    );




                $('#fileList').append(

                    `
                    <li>

                        ${fileName}

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


                        $('#file')
                            .val('');



                        $('#hiddenFileFlag')
                            .val(true);



                        $('#fileList')
                            .empty();


                    }
                );



            } else {


                $('#fileList').append(

                    '<li>첨부된 파일이 없습니다.</li>'

                );


            }



        },



        error: (error) => {


            console.error(
                error
            );



            if (error.status === 401) {


                localStorage.removeItem(
                    'accessToken'
                );


                window.location.href =
                    '/members/login';


                return;

            }



            alert(
                '상세 데이터를 불러오는데 오류가 발생했습니다.'
            );


        }


    });



};