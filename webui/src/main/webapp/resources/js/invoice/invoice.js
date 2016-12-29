/**
 * Created by Никита on 13.11.2016.
 */

var usersDataSource = dsUsers.getUsersDataSource();

$(document).ready(function () {
    usersDataSource.read();

    var dataSource = new kendo.data.DataSource(
        {
            transport: {
                read: {
                    url: "/getUserInvoicesForNonUser",
                    type: "Get",
                    dataType: "json",
                    contentType: "application/json"
                },
                update: {
                    url: "/updateInvoiceForNonUser",
                    type: "Put",
                    dataType: "json",
                    contentType: "application/json"
                },
                destroy: {
                    url: function (options) {
                        return "/deleteInvoiceForNonUser/" + options.id;
                    },
                    type: "Delete",
                    dataType: "json",
                    contentType: "application/json"
                },
                create: {
                    url: "/createInvoiceForNonUser",
                    type: "Post",
                    dataType: "json",
                    contentType: "application/json"
                },
                parameterMap: function (model, operation) {
                    if (operation === "destroy" && model) {
                        return model.id;
                    }
                    if (operation === "update" && model) {
                        return kendo.stringify(model);
                    }
                    if (operation === "create" && model) {
                        return kendo.stringify(model);
                    }
                }

            },
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: {type: "number", editable: false, nullable: false, defaultValue: null},
                        number: {type: "number", editable: false, nullable: true, defaultValue: null},
                        userId: {type: "number", editable: true, nullable: false, defaultValue: null},
                        money: {type: "number", editable: true, nullable: false, defaultValue: 0},
                        canUse: {type: "boolean", editable: true, nullable: false, defaultValue: false},
                        canAddMoney: {type: "boolean", editable: true, nullable: false, defaultValue: true},
                        isDeleted: {type: "boolean", editable: true, nullable: false, defaultValue: true}
                    }
                }
            },
            pageSize: 5
        }
    );

    $("#grid-invoices").kendoGrid(
        {
            width: 200,
            height: 380,
            dataSource: dataSource,
            groupable: false,
            filterable: {
                mode: "row"
            },
            pageable: {
                refresh: false,
                pageSize: 5,
                pageSizes: [5, 50, 100, 200, 500],
                messages: {
                    itemsPerPage: "invoices",
                    display: "{0}-{1} from {2} invoices",
                    empty: "No data",
                    allPages: "Show All"
                }
            },
            scrollable: true,
            resizable: true,
            toolbar: [{name: "create", text: "Add invoice"}],
            columns: [
                {

                    filterable: {
                        cell: {
                            showOperators: false
                        }
                    },
                    field: "number",
                    title: "Number",
                    width: 75
                },
                {
                    filterable: false,
                    field: "money",
                    title: "Money",
                    template: function (dataItem) {
                        return "$" + dataItem.money;
                    },
                    width: 70
                },
                {
                    field: "userId",
                    title: "User",
                    template: function (dataItem) {
                        return getValue(dataItem.userId, usersDataSource.data());
                    },
                    groupHeaderTemplate: function (dataItem) {
                        return getValue(dataItem.value, usersDataSource.data());
                    },
                    filterable: {
                        cell: {
                            showOperators: false,
                            template: function (container) {
                                var data = usersDataSource.data();
                                data.sort(function(a, b) {
                                    var textA = a.text.toUpperCase();
                                    var textB = b.text.toUpperCase();
                                    return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
                                });
                                getUserDropDawnEditor(container, data);
                            }
                        }
                    },
                    width: 70
                },
                {
                    command: [
                        {name: "edit", text: {edit: "Edit", update: "Save", cancel: "Cancel"}},
                        {name: "destroy", text: "Delete"}
                    ],
                    width: 100
                }
            ],
            editable: {
                confirmation: true,
                mode: "popup",
                template: kendo.template($("#popup_editor").html()),
                window: {
                    title: "Invoice"
                }
            },
            edit: function (e) {
                e.model.dirty = true;
            }
        }
    );

});

function getUserDropDawnEditor(container, dataSource) {
    getDropDownListFilter(container, dataSource, "id", "text", "Select user");
}

function getDropDownListFilter(container, dataSource, dataValueField, dataTextField, optionLabel) {
    container.element.width(135).kendoDropDownList({
        dataTextField: dataTextField,
        dataValueField: dataValueField,
        valuePrimitive: true,
        optionLabel: optionLabel,
        dataSource: dataSource
    });
}


function getValue(ids, data) {
    return findInDataSourcePropertyByValue(data, 'id', ids, 'text');
}

function findInDataSourcePropertyByValue(data, filter_property, value, return_property) {
    var items = $.grep(data, function (i) {
        return i[filter_property] === value;
    });
    return (items != null & items.length > 0) ? items[0][return_property] : "";
}