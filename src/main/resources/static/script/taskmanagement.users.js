$(document).ready(function () {

    const userAjaxUrl = "/api/v1/users";

    const ctx = {
        dataTableUrl: userAjaxUrl,
        putUrl: userAjaxUrl,
        postUrl: userAjaxUrl,
        deleteUrl: userAjaxUrl,
        getByIdUrl: userAjaxUrl,

        datatableId: "#dataTable",
        detailsFormId: "#detailsForm",
        editRowId: "#editRow",

        updFormCallback: function () {
            $.get(userAjaxUrl, updateTableByData);
        }
    }

    makeEditable(ctx, {
        "columns": [
            {
                data: "name"
            },
            {
                render: renderPassword
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
    })
});


function renderPassword(data, type, row) {
    return "ENCRYPTED";
}