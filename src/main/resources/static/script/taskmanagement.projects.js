const tasksAjaxUrl = "/api/v1/projects";

const ctx = {
    dataTableUrl: tasksAjaxUrl,
    putUrl: tasksAjaxUrl,
    postUrl: tasksAjaxUrl,
    deleteUrl: tasksAjaxUrl,
    getByIdUrl: tasksAjaxUrl,

    datatableId: "#dataTable",
    detailsFormId: "#detailsForm",
    editRowId: "#editRow",

    updFormCallback: function () {
        $.get(tasksAjaxUrl, updateTableByData);
    },
    createFormCallback: function () {
        let selectForm = $("select[name='manager.name']")
        selectForm.empty();
        $.ajax({
            type: "GET",
            url: "/api/v1/users?role=ROLE_MANAGER"
        }).done(function (data) {
            $.each(data, function (index, manager) {
                selectForm.append($("<option>", {value: manager.name, html: manager.name}))
            })
        });
    }
}

$(document).ready(function () {
    makeEditable(ctx, {
        "columns": [
            {
                data: "name"
            },
            {
                data: "manager.name"
            },
            {
                render: renderEditBtn
            },
            {
                render: renderDeleteBtn
            }
        ]
    });
});