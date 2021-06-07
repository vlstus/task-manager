$(document).ready(function () {

    const tasksAjaxUrl = "/api/v1/tasks";

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
            let managerSelectForm = $("select[name='manager.name']")
            managerSelectForm.empty();
            $.ajax({
                type: "GET",
                url: "/api/v1/users?role=ROLE_MANAGER"
            }).done(function (data) {
                $.each(data, function (index, manager) {
                    managerSelectForm.append($("<option>", {value: manager.name, html: manager.name}))
                })
            });

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
                url: "/api/v1/projects"
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
        }
    );
});