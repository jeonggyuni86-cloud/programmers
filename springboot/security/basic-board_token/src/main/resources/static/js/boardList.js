$(document).ready(() => {

    loadBoard(1);


    $('#searchBtn').on('click', () => {

        loadBoard(1);

    });



    $('#searchResetBtn').on('click', () => {

        $('#searchTitle').val('');

        $('#searchUserId').val('');

        $('#searchFrom').val('');

        $('#searchTo').val('');


        loadBoard(1);

    });



    $('#searchTitle, #searchUserId').on(
        'keydown',
        (e) => {

            if (e.key === 'Enter') {

                loadBoard(1);

            }

        }
    );

});



const PAGE_SIZE = 10;



// JWT Header 생성
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



// 검색 조건 생성
const getSearchCondition = () => {


    const condition = {};


    const title =
        $('#searchTitle').val();


    const userId =
        $('#searchUserId').val();


    const from =
        $('#searchFrom').val();


    const to =
        $('#searchTo').val();



    if (title) {

        condition.title = title;

    }



    if (userId) {

        condition.userId = userId;

    }



    if (from) {

        condition.from = from;

    }



    if (to) {

        condition.to = to;

    }



    return condition;

};




// 게시글 조회
const loadBoard = (page) => {


    $.ajax({

        type: 'GET',


        url: '/api/boards/search',



        headers: getAuthHeader(),



        data: {

            page: page,

            size: PAGE_SIZE,

            ...getSearchCondition()

        },



        success: (response) => {


            renderBoards(
                response.content
            );


            renderPagination(
                page,
                response.totalPages
            );

        },



        error: (error) => {


            console.error(
                '게시글 조회 실패',
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
                '게시판 데이터를 불러오는데 실패했습니다.'
            );

        }

    });

};




// 게시글 출력
const renderBoards = (boards) => {


    const $content =
        $('#boardContent');


    $content.empty();



    if (!boards || boards.length === 0) {


        $content.append(

            `
            <tr>
                <td colspan="5">
                    글이 존재하지 않습니다.
                </td>
            </tr>
            `

        );


        return;

    }



    boards.forEach((item) => {



        const author =
            item.userName
                ? `${item.userName} (${item.userId})`
                : item.userId;



        const commentBadge =
            item.commentCount > 0
                ? `<span class="comment-count">${item.commentCount}</span>`
                : '-';



        $content.append(

            `
            <tr>


                <td>
                    ${item.id}
                </td>



                <td>
                    <a href="/detail?id=${item.id}">
                        ${item.title}
                    </a>
                </td>



                <td>
                    ${author}
                </td>



                <td>
                    ${commentBadge}
                </td>



                <td>
                    ${item.created}
                </td>


            </tr>
            `

        );

    });


};





// 페이지 버튼 생성
const renderPagination = (
    currentPage,
    totalPages
) => {


    const $pagination =
        $('#pagination');


    $pagination.empty();



    for (
        let page = 1;
        page <= totalPages;
        page++
    ) {



        const $button =
            $(

                `
                <button class="btn page-btn">
                    ${page}
                </button>
                `

            );



        if (page === currentPage) {


            $button.addClass(
                'active'
            );


            $button.prop(
                'disabled',
                true
            );


        }




        $button.on(
            'click',
            () => loadBoard(page)
        );



        $pagination.append(
            $button
        );

    }

};