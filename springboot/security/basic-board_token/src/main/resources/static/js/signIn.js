$(document).ready(() => {

    $('#signin').click(() => {

        let userId = $('#user_id').val();
        let password = $('#password').val();


        let formData = {
            userId: userId,
            password: password
        };


        $.ajax({

            type: 'POST',

            url: '/api/members/login',

            data: JSON.stringify(formData),

            contentType:
                'application/json; charset=utf-8',

            dataType: 'json',


            success: (response) => {

                console.log(response);


                if(response.success){

                    localStorage.setItem(
                        'accessToken',
                        response.accessToken
                    );


                    localStorage.setItem(
                        'refreshToken',
                        response.refreshToken
                    );


                    alert('로그인 성공');

                    window.location.href = '/';

                } else {

                    alert(response.message);

                }

            },


            error: (error) => {

                console.error(error);

                let message =
                    error.responseJSON?.message
                    ?? '로그인 실패';


                alert(message);

            }

        });

    });

});