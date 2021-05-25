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
                data: "status"
            },
            {
                data: "manager.name"
            },
            {
                data: "developer.name"
            },
            {
                data: "project.name"
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
        },
    function(){
                form.find(":input").val("");
                form[0]["manager.role"].value = "MANAGER";
                form[0]["developer.role"].value = "DEVELOPER";
                $("#editRow").modal();
        });
});