const projectsAjaxUrl = "/api/v1/projects";

$(document).ready(function () {
    makeEditable(projectsAjaxUrl, {
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
            },
            {
                render: renderDetailsBtn
            }
        ]
    },
    function () {
            $.get(projectsAjaxUrl, updateTableByData);
        },
    function(){
            let selectForm = $("select[name='manager.name']")
            selectForm.empty();
            $.ajax({
                type: "GET",
                url: "/api/v1/users?role=MANAGER"
            }).done(function(data){
                $.each(data, function(index, manager){
                    selectForm.append($("<option>",{value:manager.name, html:manager.name}))
                })
            });
        });
});

function renderDetailsBtn(data, type, row){
    return "<a href=tasks/" + row.id +">Details</a>";
}