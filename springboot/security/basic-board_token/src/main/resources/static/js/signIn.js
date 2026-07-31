$(document).ready(() => {

    $('#signin').click(() => {

        const userId = $('#user_id').val();
        const password = $('#password').val();

        const formData = {
            userId: userId,
            password: password
        };


        $.ajax({
            type: 'POST',
            url: '/api/members/login',
            data: JSON.stringify(formData),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',

            success: (response) => {

                console.log('login response :: ', response);

                localStorage.setItem(
                    "accessToken",
                    response.accessToken
                );

                localStorage.setItem(
                    "refreshToken",
                    response.refreshToken
                );

                alert("로그인 성공");

                window.location.href = "/";
            },

            error: (error) => {
                console.error('로그인 오류:', error);

                alert("로그인 실패");
            }
        });

    });

});