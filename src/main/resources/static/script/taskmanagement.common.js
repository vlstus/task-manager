let ajaxUrl, datatableApi, form, updateTable;

function makeEditable(aUrl, datatableOpts, updTable) {
    ajaxUrl = aUrl;
    $.extend($.fn.dataTable.defaults, {
        "ajax": ajaxUrl,
        "ajaxDataProp": "",
        "order": [[0, "asc"]]
    });
    datatableApi = $('#dataTable').DataTable(datatableOpts);
    form = $('#detailsForm');
    updateTable = updTable;
}

function add() {
    form.find(":input").val("");
    $("#editRow").modal();
}

function save() {
//    let data = {};
//    $.each(form.serializeArray(), function() { data[this.name] = this.value; });
    let data = $(form).serializeObject();
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        contentType: "application/json",
        data: JSON.stringify(data),
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable();
    });
}

function update() {
//    let data = {};
//    $.each(form.serializeArray(), function() { data[this.name] = this.value; });
    let data = $(form).serializeObject();
    $.ajax({
        type: "PUT",
        url: ajaxUrl + data.id,
        contentType: "application/json",
        data: JSON.stringify(data),
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable();
    });
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function updateRow(id) {
    form.find(":input").val("");
    $.ajax({
        type: "GET",
        url: ajaxUrl + id
    }).done(function(data) {
        let root;
        let process = (key, value) => {
            if(!value){
                return;
            }
            if(value.constructor === Object){
                root = key;
                $.each(value, process);
                root = null;
            } else {
                if(root){
                    form.find("input[name='" + root + "." + key + "']").val(value);
                } else {
                    form.find("input[name='" + key + "']").val(value);
                }

            }
        }
        $.each(data,function(key,value) {
            process(key, value);
        })
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    if (confirm('sure?')) {
        $.ajax({
            url: ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            updateTable();
        });
    }
}

function renderEditBtn(data, type, row) {
    return "<button class='btn btn-info' onclick='updateRow(" + row.id + ");'>Edit</button>";
}

function renderDeleteBtn(data, type, row){
    return "<button class='btn btn-danger' onclick='deleteRow(" + row.id + ");'>Delete</button>";
}