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

            url: '/api/members/join',


            data: JSON.stringify(formData),


            contentType: 'application/json; charset=utf-8',



            success: function() {


                alert(
                    '회원가입이 성공했습니다.\n로그인해주세요.'
                );


                window.location.href =
                    '/members/login';


            },


            error: function(error) {


                console.error(
                    '오류 발생:',
                    error
                );


                let message =
                    error.responseJSON &&
                    error.responseJSON.message
                        ? error.responseJSON.message
                        : '회원가입 중 오류가 발생했습니다.';



                alert(message);


            }


        });


    });


});