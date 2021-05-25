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
        });
    $.fn.serializeObject = function() {
        var arrayData, objectData;
        arrayData = this.serializeArray();
        objectData = {};

        $.each(arrayData, function() {
            this.value = !this.value ? '' : this.value;
            processObject(objectData, this.name, this.value);
        });

        return objectData;
    };

    function processObject(obj, key, value){
        if(key.indexOf('.') != -1) {
            var attrs = key.split('.');
            var tx = obj;
            for (var i = 0; i < attrs.length - 1; i++) {
                var isArray = attrs[i].indexOf('[') != -1;
                var isNestedArray = isArray && (i != attrs.length-1);
                var nestedArrayIndex = null;
                if(isArray){
                    nestedArrayIndex = attrs[i].substring(attrs[i].indexOf('[') +1 , attrs[i].indexOf(']'));
                    attrs[i] = attrs[i].substring(0, attrs[i].indexOf('['));
                    if (tx[attrs[i]] == undefined){
                        tx[attrs[i]] = [];
                    }
                    tx = tx[attrs[i]];
                    if(isNestedArray){
                        if(tx[nestedArrayIndex] == undefined){
                            tx[nestedArrayIndex] = {};
                        }
                        tx = tx[nestedArrayIndex];
                    }

                }else{
                    if (tx[attrs[i]] == undefined){
                        tx[attrs[i]] = {};
                    }
                    tx = tx[attrs[i]];
                }
            }
            processObject(tx, attrs[attrs.length - 1], value);
        }else{
            var finalArrayIndex = null;
            if(key.indexOf('[') != -1){
                finalArrayIndex = key.substring(key.indexOf('[') +1 , key.indexOf(']'));
                key = key.substring(0, key.indexOf('['));
            }
            if(finalArrayIndex == null){
                obj[key] = value;
            }else{
                if(obj[key] == undefined){
                    obj[key] = [];
                }
                obj[key][finalArrayIndex] = value;
            }
        }
    }

});