let ajaxUrl, datatableApi, form, updateFormCallback, createFormCallback, editRow;

function makeEditable(ctx, datatableOpts) {
    ajaxUrl = ctx.dataTableUrl;
    $.extend($.fn.dataTable.defaults, {
        "ajax": ajaxUrl,
        "ajaxDataProp": "",
        "order": [[0, "asc"]],
        "columnDefs": [{
            "targets": "_all",
            "defaultContent": ""
        }]
    });
    datatableApi = $(ctx.datatableId).DataTable(datatableOpts);
    form = $(ctx.detailsFormId);
    editRow = ctx.editRowId;
    updateFormCallback = ctx.updFormCallback;
    createFormCallback = ctx.createFormCallback;

    $.fn.serializeObject = function () {
        var arrayData, objectData;
        arrayData = this.serializeArray();
        objectData = {};

        $.each(arrayData, function () {
            this.value = !this.value ? '' : this.value;
            processObject(objectData, this.name, this.value);
        });

        return objectData;
    };

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
    $.ajaxSetup({
        cache: false
    });

}

function add() {
    form.find(":input").val("");
    if (createFormCallback) {
        createFormCallback();
    }
    $(editRow).modal();
}

function sendRequestAndUpdateTable(reqType, reqUrl) {
    let data = $(form).serializeObject();
    $.ajax({
        type: reqType,
        url: reqUrl,
        contentType: "application/json",
        data: JSON.stringify(data),
    }).done(function () {
        $(editRow).modal("hide");
        updateFormCallback();
    });
}

function save() {
    rowId = $("#id")[0].value;
    if (rowId) {
        sendRequestAndUpdateTable("PUT", ctx.putUrl + "/" + rowId);
    } else {
        sendRequestAndUpdateTable("POST", ctx.postUrl);
    }

}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function updateRow(id) {
    form.find(":input").val("");
    if (createFormCallback) {
        createFormCallback();
    }
    $.ajax({
        type: "GET",
        url: ctx.getByIdUrl + "/" + id
    }).done(function (data) {
        let root;
        let process = (key, value) => {
            if (!value) {
                return;
            }
            if (value.constructor === Object) {
                root = key;
                $.each(value, process);
                root = null;
            } else {
                if (root) {
                    if (document.getElementById(root + "." + key)) {
                        document.getElementById(root + "." + key).value = value;
                    }
                } else {
                    if (document.getElementById(key)) {
                        document.getElementById(key).value = value;
                    }
                }

            }
        }
        $.each(data, function (key, value) {
            process(key, value);
        })
        $(editRow).modal();
    });
}

function deleteRow(id) {
    if (confirm('sure?')) {
        $.ajax({
            url: ctx.deleteUrl + "/" + id,
            type: "DELETE"
        }).done(function () {
            updateFormCallback();
        });
    }
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}


function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;",
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    var errorInfo = JSON.parse(jqXHR.responseText);
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}

function renderEditBtn(data, type, row) {
    return "<button class='btn btn-info' onclick='updateRow(" + row.id + ");'>Edit</button>";
}

function renderDeleteBtn(data, type, row) {
    return "<button class='btn btn-danger' onclick='deleteRow(" + row.id + ");'>Delete</button>";
}


function processObject(obj, key, value) {
    if (key.indexOf('.') != -1) {
        var attrs = key.split('.');
        var tx = obj;
        for (var i = 0; i < attrs.length - 1; i++) {
            var isArray = attrs[i].indexOf('[') != -1;
            var isNestedArray = isArray && (i != attrs.length - 1);
            var nestedArrayIndex = null;
            if (isArray) {
                nestedArrayIndex = attrs[i].substring(attrs[i].indexOf('[') + 1, attrs[i].indexOf(']'));
                attrs[i] = attrs[i].substring(0, attrs[i].indexOf('['));
                if (tx[attrs[i]] == undefined) {
                    tx[attrs[i]] = [];
                }
                tx = tx[attrs[i]];
                if (isNestedArray) {
                    if (tx[nestedArrayIndex] == undefined) {
                        tx[nestedArrayIndex] = {};
                    }
                    tx = tx[nestedArrayIndex];
                }

            } else {
                if (tx[attrs[i]] == undefined) {
                    tx[attrs[i]] = {};
                }
                tx = tx[attrs[i]];
            }
        }
        processObject(tx, attrs[attrs.length - 1], value);
    } else {
        var finalArrayIndex = null;
        if (key.indexOf('[') != -1) {
            finalArrayIndex = key.substring(key.indexOf('[') + 1, key.indexOf(']'));
            key = key.substring(0, key.indexOf('['));
        }
        if (finalArrayIndex == null) {
            obj[key] = value;
        } else {
            if (obj[key] == undefined) {
                obj[key] = [];
            }
            obj[key][finalArrayIndex] = value;
        }
    }
}