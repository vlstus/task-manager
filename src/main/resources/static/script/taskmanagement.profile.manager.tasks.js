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

            let mangerIdInput = $("input[name='manager.id']")
            mangerIdInput[0].value = $("#loggedUserId").text();
            let managerNameInput = $("input[name='manager.name']")
            managerNameInput[0].value = $("#loggedUserName").text()
            let managerRoleInput = $("input[name='manager.role']")
            managerRoleInput[0].value = $("#loggedUserRole").text()

            let developerSelectForm = $("select[name='developer.name']")
            developerSelectForm.empty();
            $.ajax({
                type: "GET",
                url: "/api/v1/users?role=ROLE_DEVELOPER"
            }).done(function (data) {
                $.each(data, function (index, developer) {
                    developerSelectForm.append($("<option>", {value: developer.name, html: developer.name}))
                })
            });

            let projectSelectForm = $("select[name='project.name']")
            projectSelectForm.empty();
            $.ajax({
                type: "GET",
                url: "/api/v1/users/" + loggedUserId + "/projects"
            }).done(function (data) {
                $.each(data, function (index, project) {
                    projectSelectForm.append($("<option>", {value: project.name, html: project.name}))
                })
            });
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
        }
    );
});