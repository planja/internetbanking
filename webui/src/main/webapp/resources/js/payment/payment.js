/**
 * Created by admin on 08.11.2016.
 */

var paymentTypesDataSource = dsPayments.getPaymentTypesDataSource();
var invoicesDataSource = dsPayments.getInvoicesDataSource();
var mobileOperatorsDataSource = dsPayments.getMobileOperatorsDataSource();
var internetOperatorsDataSource = dsPayments.getInternetOperatorsDataSource();
var paymentStatusDataSource = dsPayments.getPaymentStatusDataSource();
var usersDataSource = dsUsers.getUsersDataSource();

$(document).ready(function () {
    paymentTypesDataSource.read();
    invoicesDataSource.read();
    mobileOperatorsDataSource.read();
    internetOperatorsDataSource.read();
    paymentStatusDataSource.read();
    usersDataSource.read();


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
                destroy: {
                    url: function (options) {
                        return "/deletePayment/" + options.id;
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
                        if (model.paymentType == 1) {
                            model.number = $("#phone").val();
                        } else if (model.paymentType == 2) {
                            model.number = $("#internet").data("kendoNumericTextBox").value();
                        } else {
                            model.number = $("#other_number").val();
                        }
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
                        paymentType: {type: "number", editable: true, nullable: false, defaultValue: 1},
                        number: {type: "string", editable: true, nullable: false},
                        money: {type: "number", editable: true, nullable: false, defaultValue: 1},
                        userId: {type: "number", editable: false, nullable: true, defaultValue: null},
                        paymentStatus: {type: "number", editable: true, nullable: false},
                        cause: {type: "string", editable: true, nullable: false},
                        created: {type: "datetime", editable: true, nullable: false, default: new Date()},
                        invoiceId: {type: "number", editable: true, nullable: false, defaultValue: null}
                    }
                }
            },
            pageSize:5
        }
    );

    $("#grid-payments").kendoGrid(
        {
            toolbar: [{name: "create", text: "Add payment"}, "excel"],
            excel: {
                fileName: "payments.xlsx",
                allPages: true
            }
            ,
            pageable: {
                refresh: false,
                pageSize: 5,
                pageSizes: [5, 50, 100, 200, 500],
                messages: {
                    itemsPerPage: "payments",
                    display: "{0}-{1} from {2} payments",
                    empty: "No data",
                    allPages: "Show All"
                }
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
                    row.cells[4].value = getValue(row.cells[4].value, paymentStatusDataSource.data());
                }
            },
            width: 600,
            height: 400,
            scrollable: true,
            resizable: true,
            dataSource: dataSource,
            filterable: {
                mode: "row"
            },
            columns: [
                {
                    field: "paymentType",
                    title: "Payment type",
                    template: function (dataItem) {
                        return getValue(dataItem.paymentType, paymentTypesDataSource.data());
                    },
                    groupHeaderTemplate: function (dataItem) {
                        return getValue(dataItem.value, paymentTypesDataSource.data());
                    },
                    filterable: {
                        cell: {
                            showOperators: false,
                            template: function (container) {
                                getPaymentTypeDropDawnEditor(container, paymentTypesDataSource.data());
                            }
                        }
                    },

                    width: 120
                },
                {
                    filterable: false,
                    field: "operator",
                    title: "Operator",
                    template: function (dataItem) {
                        if (dataItem.paymentType == 1) {
                            return getValue(dataItem.operator, mobileOperatorsDataSource.data());
                        } else if (dataItem.paymentType == 2) {
                            return getValue(dataItem.operator, internetOperatorsDataSource.data());
                        } else {
                            return "NONE";
                        }
                    },
                    width: 70
                },
                {
                    filterable: false,
                    field: "number",
                    title: "Number",
                    width: 90
                },
                {
                    filterable: false,
                    field: "money",
                    title: "Money",
                    template: function (dataItem) {
                        return dataItem.money + "$";

                    },
                    width: 60
                },
                {
                    field: "paymentStatus",
                    title: "Payment status",
                    template: function (dataItem) {
                        return getValue(dataItem.paymentStatus, paymentStatusDataSource.data());
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
                    width: 80
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
                if (e.model.paymentType == 1) {
                    $("#for_internet").css("display", "none");
                    $("#for_other").css("display", "none");
                    $("#other_number").val("1");
                    $("#internet").data("kendoNumericTextBox").value(1);
                } else if (e.model.paymentType == 2) {
                    $("#for_phone").css("display", "none");
                    $("#for_other").css("display", "none");
                    $("#phone").val("029");
                    $("#other_number").val("1");
                } else {
                    $("#for_phone").css("display", "none");
                    $("#for_internet").css("display", "none");
                    $("#internet").data("kendoNumericTextBox").value(1);
                    $("#phone").val("029");
                }


                if (e.model.id == null) {
                    $("#other_number").val("1");
                    $("#internet").data("kendoNumericTextBox").value(1);
                    $("#phone").val("029");
                    $("#for_edit").css("display", "none");
                    $("#for_save").css("display", "");
                } else {
                    var result = $.grep(invoicesDataSource.data(), function(k){ return k.id == e.model.invoiceId; });
                    if(result.length==0){
                        invoicesDataSource.add({
                            id: -1,
                            text: "Other invoice"
                        });
                        $("#invoice").data("kendoDropDownList").select(function(dataItem) {
                            return dataItem.text === "Other invoice";
                        });
                    }
                    if (!isUser)
                        $("#for_edit").css("display", "");
                }


            },
            dataBound: function () {
                var grid = this;
                var model;
                grid.tbody.find("tr[role='row']").each(function () {
                    $(this).find(".k-grid-delete").show();
                    $(this).find(".k-grid-edit").show();
                });

                grid.tbody.find("tr[role='row']").each(function () {
                    model = grid.dataItem(this);
                    if (model.paymentStatus == 3) {
                        $(this).find(".k-grid-edit").hide();
                    }
                });
            },
        }
    );

    $("#grid-payments").kendoTooltip({
        filter: "td",
        content: function (e) {
            var dataItem = $("#grid-payments").data("kendoGrid").dataItem(e.target.closest("tr"));
            if (dataItem.cause != null && dataItem.cause != "") {
                return "Cause: " + dataItem.cause;
            }

        }
    }).data("kendoTooltip");


});

function change() {
    var dropDownList = $("#payment_type").data("kendoDropDownList");
    $("#other_number").val("1");
    $("#internet").data("kendoNumericTextBox").value(1);
    $("#phone").val("029");
    if (dropDownList.dataItem().text == "MOBILE") {
        $("#for_internet").css("display", "none");
        $("#for_other").css("display", "none");
        $("#for_phone").css("display", "");
    } else if (dropDownList.dataItem().text == "INTERNET") {
        $("#for_phone").css("display", "none");
        $("#for_other").css("display", "none");
        $("#for_internet").css("display", "");
    } else {
        $("#for_other").css("display", "");
        $("#for_phone").css("display", "none");
        $("#for_internet").css("display", "none");

    }
}

function getPaymentStatusDropDawnEditor(container, dataSource) {
    getDropDownListFilter(container, dataSource, "id", "text", "Select status");
}

function getPaymentTypeDropDawnEditor(container, dataSource) {
    getDropDownListFilter(container, dataSource, "id", "text", "Select type");
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