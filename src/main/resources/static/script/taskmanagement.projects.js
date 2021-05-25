const tasksAjaxUrl = "/api/v1/tasks/";

$(document).ready(function () {
    makeEditable(tasksAjaxUrl, {
        "columns": [
            {
                data: "id"
            },
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
    },
    function () {
            $.get(tasksAjaxUrl, updateTableByData);
        });
});