/**
 * Created by Никита on 13.11.2016.
 */

$(document).ready(function () {

    var dataSource = new kendo.data.DataSource(
        {
            transport: {
                read: {
                    url: "/getUserInvoices",
                    type: "Get",
                    dataType: "json",
                    contentType: "application/json"
                },
                destroy: {
                    url: function (options) {
                        return "/deleteInvoice/" + options.id;
                    },
                    type: "Delete",
                    dataType: "json",
                    contentType: "application/json"
                },
                create: {
                    url: "/saveInvoice",
                    type: "Post",
                    dataType: "json",
                    contentType: "application/json"
                },
                update: {
                    url: "/updateInvoice",
                    type: "Put",
                    dataType: "json",
                    contentType: "application/json"
                }

            },
            requestEnd: function (e) {
                var type = e.type;
                if (type != "read") {
                    $('#grid-invoices').data('kendoGrid').dataSource.read();
                }
            },
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: {type: "number", editable: false, nullable: false, defaultValue: null},
                        number: {type: "number", editable: false, nullable: false, defaultValue: null},
                        canAddMoney: {type: "boolean", editable: true, nullable: false, defaultValue: true},
                        isDeleted: {type: "boolean", editable: true, nullable: false, defaultValue: false},
                        money: {type: "number", editable: true, nullable: false, defaultValue: 0}
                    }
                }
            }
        }
    );

    $("#grid-invoices").kendoGrid(
        {
            width: 300,
            height: 300,
            dataSource: dataSource,
            groupable: false,
            filterable: false,
            columns: [
                {
                    field: "number",
                    title: "Number",
                    width: 75
                },
                {
                    field: "money",
                    title: "Money",
                    template: function (dataItem) {
                        return "$" + dataItem.money;
                    },
                    width: 70
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
            dataBound: function () {
                var grid = this;
                var model;
                grid.tbody.find("tr[role='row']").each(function () {
                    $(this).find(".k-grid-delete").show();
                    $(this).find(".k-grid-edit").show();
                });

                grid.tbody.find("tr[role='row']").each(function () {
                    model = grid.dataItem(this);
                    if (!model.canAddMoney) {
                        $(this).find(".k-grid-delete").hide();
                        $(this).find(".k-grid-edit").hide();
                    }
                });
            },
            edit: function (e) {
                e.model.dirty = true;
                if (e.model.id == null) {
                    $("#for_edit").css("display", "none");
                    $("#for_save").css("display", "");
                } else {
                    $("#for_edit").css("display", "");
                    $("#for_save").css("display", "none");
                }
            }
        }
    );

});