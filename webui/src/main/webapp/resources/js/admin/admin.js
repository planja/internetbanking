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
                create: {
                    url: "/saveUserByAdmin",
                    type: "Post",
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
                        if (model.roles == "" || model.roles.length == 0) {
                            model.roles=[1];
                        }
                        return kendo.stringify(model);
                    }
                }
            },
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: {type: "number", editable: false, nullable: false, defaultValue: null},
                        userName: {
                            type: "string", editable: true, nullable: false,
                            validation: {
                                required: true,
                                userNameValidator: function (input) {
                                    if (input.is("[id='user_name']")) {
                                        var items = dataSource.data();

                                        input.attr("data-userNameValidator-msg", "User with current user name exist");
                                        var exists = $.grep(items, function (item) {
                                            return item.userName == input.val();
                                        });
                                        if (exists != null && exists.length > 1)
                                            return false;
                                    }
                                    return true;
                                }
                            }
                        },
                        userPassword: {type: "string", editable: true, nullable: false},
                        name: {type: "string", editable: true, nullable: false},
                        mail: {type: "string", editable: true, nullable: false},
                        passportNumber: {type: "string", editable: true, nullable: false},
                        issuedPassport: {type: "string", editable: true, nullable: false},
                        passportIssuingDate: {type: "datetime", defaultValue: new Date()},
                        roles: [{type: "number", editable: true, nullable: true, defaultValue: null}]
                    }
                }
            },
            pageSize: 5,
            sort: {field: "name", dir: "asc"}
        }
    );

    $("#grid-users").kendoGrid(
        {
            toolbar: [{name: "create", text: "Add user"}],
            width: 600,
            height: 400,
            dataSource: dataSource,
            filterable: {
                extra: false,
                operators: {
                    string: {
                        contains: "Contains",
                        startswith: "Starts with",
                        eq: "Is equal to",
                        neq: "Is not equal to"
                    }
                }
            },
            pageable: {
                refresh: false,
                pageSize: 5,
                pageSizes: [5, 50, 100, 200, 500],
                messages: {
                    itemsPerPage: "users",
                    display: "{0}-{1} from {2} users",
                    empty: "No data",
                    allPages: "Show All"
                }
            },
            columns: [
                {
                    field: "name",
                    title: "Name",
                    width: 120
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
                    filterable: false,
                    field: "roles",
                    title: "Roles",
                    width: 110,
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
                    title: "User"
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
    return items.join(',\n');
}