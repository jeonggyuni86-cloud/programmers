$(document).ready(() => {


    $('#signup').click(() => {



        let userId =
            $('#user_id').val();



        let password =
            $('#password').val();



        let userName =
            $('#user_name').val();





        let formData = {


            userId: userId,


            password: password,


            userName: userName


        };






        $.ajax({


            type: 'POST',



            url:
                '/api/members/join',



            data:
                JSON.stringify(formData),



            contentType:
                'application/json; charset=utf-8',



            success: () => {



                alert(
                    '회원가입이 성공했습니다.\n로그인해주세요.'
                );



                window.location.href =
                    '/members/login';



            },



            error: (error) => {



                console.error(
                    '회원가입 실패:',
                    error
                );




                if (error.status === 409) {



                    alert(
                        error.responseJSON?.message
                        ?? '이미 존재하는 회원입니다.'
                    );



                    return;

                }





                if (error.status === 400) {



                    alert(
                        error.responseJSON?.message
                        ?? '입력값을 확인해주세요.'
                    );


                    return;

                }





                alert(

                    error.responseJSON?.message
                    ??
                    '회원가입 중 오류가 발생했습니다.'

                );



            }



        });



    });



});