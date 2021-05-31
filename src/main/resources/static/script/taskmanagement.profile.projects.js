$(document).ready(function () {

        const loggedUserId = $("#loggedUserId").text();
        const userProjectsAjaxUrl = "/api/v1/users/" + loggedUserId + "/projects"

        const ctx = {
            dataTableUrl: userProjectsAjaxUrl,
            putUrl: "/api/v1/projects",
            postUrl: "/api/v1/projects",
            deleteUrl: "/api/v1/projects",
            getByIdUrl: "/api/v1/projects",

            datatableId: "#dataTable",
            detailsFormId: "#projectDetailsForm",
            editRowId: "#projectEditRow",

            updFormCallback: function () {
                $.get(userProjectsAjaxUrl, updateTableByData);
            },
            createFormCallback: function () {
                let mangerIdInput = $("input[name='manager.id']")
                mangerIdInput[0].value = $("#loggedUserId").text();
                let managerNameInput = $("input[name='manager.name']")
                managerNameInput[0].value = $("#loggedUserName").text()
                let managerRoleInput = $("input[name='manager.role']")
                managerRoleInput[0].value = $("#loggedUserRole").text()
            }

        }

        makeEditable(ctx, {
            "columns": [
                {
                    data: "name"
                },
                {
                    render: renderEditBtn
                },
                {
                    render: renderDeleteBtn
                },
                {
                    render: function renderEditBtn(data, type, row) {
                        return "<a href='/profile/projects?id=" + row.id + "'>Details</a>";
                    }
                }
            ]
        });
    });