const userAjaxUrl = "/api/v1/users/";

$(document).ready(function () {
    makeEditable(userAjaxUrl, {
        "columns": [
            {
                data: "id"
            },
            {
                data: "name"
            },
            {
                data: "password"
            },
            {
                data: "role"
            },
            {
                render: renderEditBtn
            },
            {
                render: renderDeleteBtn
            }
        ]
    },
    function () {
            $.get(userAjaxUrl, updateTableByData);
        });
});