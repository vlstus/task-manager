const projectsAjaxUrl = "/api/v1/projects/";

$(document).ready(function () {
    makeEditable(projectsAjaxUrl, {
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
            $.get(projectsAjaxUrl, updateTableByData);
        },
    function(){
            form.find(":input").val("");
            form[0]["manager.role"].value = "MANAGER";
            $("#editRow").modal();
        });
});