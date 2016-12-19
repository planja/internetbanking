/**
 * Created by Никита on 11.12.2016.
 */


var invoicesDataSource = dsPayments.getInvoicesDataSource();
var paymentStatusDataSource = dsPayments.getPaymentStatusDataSource();

$(document).ready(function () {
    invoicesDataSource.read();
    paymentStatusDataSource.read();


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
                        },
                    }
                }
            }
        }
    );

    $("#grid-transfers").kendoGrid(
        {
            toolbar: [{name: "create", text: "Add transfer"}, "excel"],
            excel: {
                fileName: "transfers.xlsx",
                allPages: true
            },
            excelExport: function (e) {
                var sheet = e.workbook.sheets[0];

                for (var rowIndex = 1; rowIndex < sheet.rows.length; rowIndex++) {
                    var row = sheet.rows[rowIndex];
                    row.cells[3].value = row.cells[3].value + " $";
                    row.cells[4].value = getValue(row.cells[4].value, paymentStatusDataSource.data());
                }
            },
            width: 600,
            height: 400,
            dataSource: dataSource,
            filterable: true,
            columns: [
                {
                    field: "bankNumber",
                    title: "Bank number",
                    width: 100
                },
                {
                    field: "billNumber",
                    title: "Bill number",
                    width: 100
                },
                {
                    field: "receiverFullName",
                    title: "Receiver",
                    width: 100
                },
                {
                    field: "money",
                    title: "Money",
                    template: function (dataItem) {
                        return dataItem.money + "$";

                    },
                    width: 100
                },
                {
                    field: "status",
                    title: "Status",
                    template: function (dataItem) {
                        return getValue(dataItem.status, paymentStatusDataSource.data());
                    },
                    width: 100
                },
                {
                    field: "created",
                    title: "Date time",
                    format: "{0:dd.MM.yyyy HH:mm}",
                    width: 100
                },
                {
                    hidden: isUser,
                    command: [
                        {name: "edit", text: {edit: "Edit", update: "Save", cancel: "Cancel"}}
                    ],
                    width: 70
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
                e.model.dirty = true;
                if (e.model.id == null) {
                    $("#for_edit").css("display", "none");
                    $("#for_save").css("display", "");
                } else {
                    $("#for_edit").css("display", "");
                    $("#for_save").css("display", "none");
                    invoicesDataSource.add({
                        id: -1,
                        text: "-1"
                    });
                    $("#invoice").data("kendoDropDownList").select(1)

                }
            }
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

});

function getValue(ids, data) {
    return findInDataSourcePropertyByValue(data, 'id', ids, 'text');
}

function findInDataSourcePropertyByValue(data, filter_property, value, return_property) {
    var items = $.grep(data, function (i) {
        return i[filter_property] === value;
    });
    return (items != null & items.length > 0) ? items[0][return_property] : "";
}