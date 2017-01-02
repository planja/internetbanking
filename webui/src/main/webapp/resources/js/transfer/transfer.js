/**
 * Created by Никита on 11.12.2016.
 */


var invoicesDataSource = dsPayments.getInvoicesDataSource();
var paymentStatusDataSource = dsPayments.getPaymentStatusDataSource();
var usersDataSource = dsUsers.getUsersDataSource();


$(document).ready(function () {
    invoicesDataSource.read();
    paymentStatusDataSource.read();
    usersDataSource.read();


    var dataSource = new kendo.data.DataSource(
        {
            transport: {
                read: {
                    url: "/getTransfer",
                    type: "Get",
                    dataType: "json",
                    contentType: "application/json"
                },
                create: {
                    url: "/createTransfer",
                    type: "Post",
                    dataType: "json",
                    contentType: "application/json"
                },
                update: {
                    url: "/updateTransfer",
                    type: "Put",
                    dataType: "json",
                    contentType: "application/json"
                },
                destroy: {
                    url: function (options) {
                        return "/deleteTransfer/" + options.id;
                    },
                    type: "Delete",
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
                        money: {type: "number", editable: true, nullable: false, defaultValue: 1},
                        bankNumber: {type: "number", editable: true, nullable: false, defaultValue: 1},
                        billNumber: {type: "number", editable: true, nullable: false, defaultValue: 1},
                        receiverFullName: {type: "string", editable: true, nullable: false},
                        status: {type: "number", editable: true, nullable: false, defaultValue: 0},
                        created: {type: "datetime", editable: true, nullable: true, default: new Date()},
                        cause: {type: "string", editable: true, nullable: true},
                        invoiceId: {
                            type: "number", editable: true, nullable: false, defaultValue: null,
                            validation: {
                                required: true,
                                invoiceIdValidator: function (input) {
                                    if (input.is("[name='invoice']")
                                        && ($("#for_save").is(':visible'))) {
                                        input.attr("data-invoiceIdValidator-msg", "Select invoice");
                                        return input.val() != null;
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                }
            },
            requestEnd: function (e) {
                var type = e.type;
                if (type != "read") {
                    $("#grid-transfers").data('kendoGrid').dataSource.read();
                }
            },
            pageSize: 5
        }
    );

    $("#grid-transfers").kendoGrid(
        {
            toolbar: [{name: "create", text: "Add transfer"}, "excel"],
            excel: {
                fileName: "transfers.xlsx",
                allPages: true
            },
            filterable: {
                mode: "row"
            },
            scrollable: true,
            resizable: true,
            pageable: {
                refresh: false,
                pageSize: 5,
                pageSizes: [5, 50, 100, 200, 500],
                messages: {
                    itemsPerPage: "transfers",
                    display: "{0}-{1} from {2} transfers",
                    empty: "No data",
                    allPages: "Show All"
                }
            },
            excelExport: function (e) {
                var sheet = e.workbook.sheets[0];

                for (var rowIndex = 1; rowIndex < sheet.rows.length; rowIndex++) {
                    var row = sheet.rows[rowIndex];
                    row.cells[3].value = row.cells[3].value + " $";
                    row.cells[4].value = getValue(row.cells[4].value, paymentStatusDataSource.data());
                    if (row.cells.length != 5)
                        row.cells[5].value = getValue(row.cells[5].value, usersDataSource.data());
                }
            },
            width: 600,
            height: 400,
            dataSource: dataSource,
            columns: [
                {
                    filterable: false,
                    field: "bankNumber",
                    title: "Bank number",
                    width: 100
                },
                {
                    filterable: false,
                    field: "billNumber",
                    title: "Bill number",
                    width: 90
                },
                {
                    filterable: false,
                    field: "receiverFullName",
                    title: "Receiver",
                    width: 100
                },
                {
                    filterable: false,
                    field: "money",
                    title: "Money",
                    template: function (dataItem) {
                        return dataItem.money + "$";

                    },
                    width: 70
                },
                {
                    field: "status",
                    title: "Transfer status",
                    template: function (dataItem) {
                        return getValue(dataItem.status, paymentStatusDataSource.data());
                    },
                    groupHeaderTemplate: function (dataItem) {
                        return getValue(dataItem.value, paymentStatusDataSource.data());
                    },
                    filterable: {
                        cell: {
                            showOperators: false,
                            template: function (container) {
                                getPaymentStatusDropDawnEditor(container, paymentStatusDataSource.data());
                            }
                        }
                    },
                    width: 120
                },
                {
                    hidden: isUser,
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
                                data.sort(function (a, b) {
                                    var textA = a.text.toUpperCase();
                                    var textB = b.text.toUpperCase();
                                    return (textA < textB) ? -1 : (textA > textB) ? 1 : 0;
                                });
                                getUserDropDawnEditor(container, data);
                            }
                        }
                    },
                    width: 120
                },
                {
                    command: [
                        {name: "edit", text: {edit: "Edit", update: "Save", cancel: "Cancel"}},
                        {name: "destroy", text: "Delete"}
                    ],
                    width: 90
                }
            ],
            editable: {
                confirmation: true,
                mode: "popup",
                template: kendo.template($("#popup_editor").html()),
                window: {
                    title: "Transfer"
                }
            },
            edit: function (e) {
                var remove = null;
                for (var i = 0; i < invoicesDataSource.data().length; i++) {
                    if (invoicesDataSource.data()[i].id == -1) {
                        remove = invoicesDataSource.data()[i];
                    }
                }
                if (remove != null)
                    invoicesDataSource.remove(remove);


                if (isUser) {
                    $("#for_save").css("display", "");
                    $("#for_edit").css("display", "none");
                } else {
                    $("#for_edit").css("display", "");
                }
                e.model.dirty = true;


                if (e.model.id == null) {
                    $("#for_edit").css("display", "none");
                    $("#for_save").css("display", "");
                } else {
                    var result = $.grep(invoicesDataSource.data(), function (k) {
                        return k.id == e.model.invoiceId;
                    });
                    if (result.length == 0) {
                        invoicesDataSource.add({
                            id: -1,
                            text: "Other invoice"
                        });
                        $("#invoice").data("kendoDropDownList").select(function (dataItem) {
                            return dataItem.text === "Other invoice";
                        });
                    }
                    if (!isUser)
                        $("#for_edit").css("display", "");

                }
            }
            ,
            dataBound: function () {
                var grid = this;
                var model;
                grid.tbody.find("tr[role='row']").each(function () {
                    $(this).find(".k-grid-delete").show();
                    $(this).find(".k-grid-edit").show();
                });

                grid.tbody.find("tr[role='row']").each(function () {
                    model = grid.dataItem(this);
                    if (model.status == 3) {
                        $(this).find(".k-grid-edit").hide();
                    }
                });
            },
        }
    );

    $("#grid-transfers").kendoTooltip({
        filter: "td",
        content: function (e) {
            var dataItem = $("#grid-transfers").data("kendoGrid").dataItem(e.target.closest("tr"));
            if (dataItem.cause != null && dataItem.cause != "") {
                return "Cause: " + dataItem.cause;
            }

        }
    }).data("kendoTooltip");

})
;

function getPaymentStatusDropDawnEditor(container, dataSource) {
    getDropDownListFilter(container, dataSource, "id", "text", "Select status");
}

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