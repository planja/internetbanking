/**
 * Created by admin on 08.11.2016.
 */

var rolesDataSource = dsRoles.getRolesTypeDataSource();

$(document).ready(function () {
    rolesDataSource.read();

    var dataSource = new kendo.data.DataSource(
        {
            transport: {
                read: {
                    url: "/findAllUser",
                    type: "Get",
                    dataType: "json",
                    contentType: "application/json"
                },
                destroy: {
                    url: function (options) {
                        return "/deleteUser/" + options.id;
                    },
                    type: "Delete",
                    dataType: "json",
                    contentType: "application/json"
                },
                update: {
                    url: "/updateRoles",
                    type: "Put",
                    dataType: "json",
                    contentType: "application/json"
                },
                parameterMap: function (model, operation) {
                    if (operation === "destroy" && model) {
                        return model.id;
                    }
                    if (operation === "create" || operation === "update" && model) {
                        return kendo.stringify(model);
                    }
                }
            },
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: {type: "number", editable: false, nullable: false, defaultValue: null},
                        userName: {type: "string", editable: true, nullable: false},
                        userPassword: {type: "string", editable: true, nullable: false},
                        name: {type: "string", editable: true, nullable: false},
                        mail: {type: "string", editable: true, nullable: false},
                        passportNumber: {type: "string", editable: true, nullable: false},
                        issuedPassport: {type: "string", editable: true, nullable: false},
                        roles: [{type: "number", editable: true, nullable: true, defaultValue: null}]
                    }
                }
            },
            sort: {field: "name", dir: "asc"}
        }
    );

    $("#grid-users").kendoGrid(
        {
            width: 600,
            height: 400,
            dataSource: dataSource,
            filterable: true,
            columns: [
                {
                    field: "name",
                    title: "Name",
                    width: 100
                },
                {
                    field: "userName",
                    title: "User name",
                    width: 120
                },
                {
                    field: "mail",
                    title: "Mail",
                    width: 150
                },
                {
                    field: "passportNumber",
                    title: "Passport number",
                    width: 150
                },
                {
                    field: "roles",
                    title: "Roles",
                    width: 130,
                    template: function (dataItem) {
                        return getRolesNamesById(dataItem.roles, rolesDataSource.data());
                    }
                },
                {
                    command: [
                        {name: "destroy", text: "Delete"},
                        {name: "edit", text: {edit: "Edit", update: "Save", cancel: "Cancel"}}
                    ],
                    width: 177
                }
            ],
            editable: {
                confirmation: true,
                mode: "popup",
                template: kendo.template($("#popup_editor").html()),
                window: {
                    title: "User roles"
                }
            },
            edit: function (e) {
                e.model.dirty = true;
            }
        }
    );


});

function getRolesNamesById(ids, data) {
    return findInDataSourcePropertiesByValue(data, 'id', ids, 'text');
}

function findInDataSourcePropertiesByValue(data, filter_property, values, return_property) {
    if (data == null) return "";
    var items = [];
    $.each(values, function (index, value) {
        var found = $.grep(data, function (item) {
            return item[filter_property] === value;
        });
        if (found != null && found.length > 0) items.push(found[0][return_property]);
    });
    return items.join(',');
}