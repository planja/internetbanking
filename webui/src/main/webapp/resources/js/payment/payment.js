/**
 * Created by admin on 08.11.2016.
 */

var paymentTypesDataSource = dsPayments.getPaymentTypesDataSource();
var invoicesDataSource = dsPayments.getInvoicesDataSource();
var mobileOperatorsDataSource = dsPayments.getMobileOperatorsDataSource();
var internetOperatorsDataSource = dsPayments.getInternetOperatorsDataSource();
var paymentStatusDataSource = dsPayments.getPaymentStatusDataSource();

$(document).ready(function () {
    paymentTypesDataSource.read();
    invoicesDataSource.read();
    mobileOperatorsDataSource.read();
    internetOperatorsDataSource.read();
    paymentStatusDataSource.read();


    var dataSource = new kendo.data.DataSource(
        {
            transport: {
                read: {
                    url: "/getPayments",
                    type: "Get",
                    dataType: "json",
                    contentType: "application/json"
                },
                create: {
                    url: "/createPayment",
                    type: "Post",
                    dataType: "json",
                    contentType: "application/json"
                },
                update: {
                    url: "/updatePayment",
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
                        model.number = $("#phone").val();
                        return kendo.stringify(model);
                    }
                }
            },
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: {type: "number", editable: false, nullable: false, defaultValue: null},
                        operator: {type: "number", editable: true, nullable: false},
                        paymentType: {type: "number", editable: true, nullable: false},
                        number: {
                            type: "string", editable: true, nullable: false,
                            validation: {
                                required: true,
                                numberValidator: function (input) {
                                    if (input.is("[name='txtInput']") && $("#for_phone").is(':visible')) {
                                        input.attr("data-numberValidator-msg", "Enter valid phone number");
                                        return input.val().length == 13;
                                    }
                                    return true;
                                }
                            }
                        },
                        money: {type: "number", editable: true, nullable: false, defaultValue: 1},
                        paymentStatus: {type: "number", editable: true, nullable: false},
                        cause: {type: "string", editable: true, nullable: false},
                        created: {type: "datetime", editable: true, nullable: false, default: new Date()},
                        invoiceId: {
                            type: "number", editable: true, nullable: false, defaultValue: null,
                            validation: {
                                required: true,
                                invoiceIdValidator: function (input) {
                                    if (input.is("[name='invoice']"
                                            && ($("#for_phone").is(':visible') || $("#for_internet").is(':visible')))) {
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

    $("#grid-payments").kendoGrid(
        {
            toolbar: [{name: "create", text: "Add payment"}, "excel"],
            excel: {
                fileName: "payments.xlsx",
                allPages: true
            },
            excelExport: function (e) {
                var sheet = e.workbook.sheets[0];

                for (var rowIndex = 1; rowIndex < sheet.rows.length; rowIndex++) {
                    var row = sheet.rows[rowIndex];
                    if (row.cells[0].value == 0) {
                        row.cells[1].value = getValue(row.cells[1].value, mobileOperatorsDataSource.data());
                    } else {
                        row.cells[1].value = getValue(row.cells[1].value, internetOperatorsDataSource.data());
                    }
                    row.cells[0].value = getValue(row.cells[0].value, paymentTypesDataSource.data());
                    row.cells[3].value = row.cells[3].value + " $";
                    row.cells[4].value=getValue(row.cells[4].value, paymentStatusDataSource.data());
                }
            },
            width: 600,
            height: 400,
            dataSource: dataSource,
            groupable: true,
            filterable: true,
            columns: [
                {
                    field: "paymentType",
                    title: "Type",
                    template: function (dataItem) {
                        return getValue(dataItem.paymentType, paymentTypesDataSource.data());
                    },
                    width: 100
                },
                {
                    field: "operator",
                    title: "Operator",
                    template: function (dataItem) {
                        if (dataItem.paymentType == 0) {
                            return getValue(dataItem.operator, mobileOperatorsDataSource.data());
                        } else {
                            return getValue(dataItem.operator, internetOperatorsDataSource.data());
                        }
                    },
                    width: 100
                },
                {
                    field: "number",
                    title: "Number",
                    width: 110
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
                    field: "paymentStatus",
                    title: "Status",
                    template: function (dataItem) {
                        return getValue(dataItem.paymentStatus, paymentStatusDataSource.data());
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
                    title: "Payment"
                }
            },
            edit: function (e) {
                e.model.dirty = true;
                if (e.model.id == null) {
                    $("#for_edit").css("display", "none");
                    $("#for_save").css("display", "");
                    if (e.model.paymentType == 0)
                        $("#for_internet").css("display", "none");
                    else
                        $("#for_phone").css("display", "none");
                } else {
                    $("#for_edit").css("display", "");
                    $("#for_save").css("display", "none");
                }


            }
        }
    );
});

function change() {
    var dropDownList = $("#payment_type").data("kendoDropDownList");
    if (dropDownList.selectedIndex == 1) {
        $("#for_internet").css("display", "none");
        $("#for_phone").css("display", "");
    } else {
        $("#for_phone").css("display", "none");
        $("#for_internet").css("display", "");
    }
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