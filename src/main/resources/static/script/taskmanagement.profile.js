const loggedUserId = $("#loggedUserId").text();
const userAjaxUrl = "/api/v1/users/" + loggedUserId + "/projects"

const ctx = {
    dataTableUrl: userAjaxUrl,
    putUrl: "/api/v1/projects",
    postUrl: "/api/v1/projects",
    deleteUrl: "/api/v1/projects",
    getByIdUrl: "/api/v1/projects",

    datatableId: "#projectDataTable",
    detailsFormId: "#projectDetailsForm",
    editRowId: "#projectEditRow",

    updFormCallback: function () {
        $.get(userAjaxUrl, updateTableByData);
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

$(document).ready(function () {
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
            }
        ]
    });
});