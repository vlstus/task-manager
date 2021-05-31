$(document).ready(function () {

    const loggedUserId = $("#loggedUserId").text();
    const tasksAjaxUrl = "/api/v1/users/" + loggedUserId + "/tasks";

    const ctx = {
        dataTableUrl: tasksAjaxUrl,
        putUrl: "/api/v1/tasks",
        postUrl: "/api/v1/tasks",
        deleteUrl: "/api/v1/tasks",
        getByIdUrl: "/api/v1/tasks",

        datatableId: "#dataTable",
        detailsFormId: "#detailsForm",
        editRowId: "#editRow",

        updFormCallback: function () {
            $.get(tasksAjaxUrl, function (data) {

                updateTableByData(data);
            });
        },
        createFormCallback: function () {
            let developerIdInput = $("input[name='developer.id']")
            developerIdInput[0].value = $("#loggedUserId").text();
            let developerNameInput = $("input[name='developer.name']")
            developerNameInput[0].value = $("#loggedUserName").text()
            let developerRoleInput = $("input[name='developer.role']")
            developerRoleInput[0].value = $("#loggedUserRole").text()
        }
    }

    makeEditable(ctx, {
            "columns": [
                {
                    data: "name"
                },
                {
                    data: "status"
                },
                {
                    data: "project.name"
                },
                {
                    render: renderEditBtn
                }
            ]
        }
    );
});