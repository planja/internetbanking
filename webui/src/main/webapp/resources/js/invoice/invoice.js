/**
 * Created by Никита on 13.11.2016.
 */

$(document).ready(function () {

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
                parameterMap: function (model, operation) {
                    if (operation === "destroy" && model) {
                        return model.id;
                    }
                    if (operation === "update" && model) {
                        if ($("#update_money").css("display") == "none") {
                            model.canUse = true;
                        }else{
                            model.canAddMoney = true;
                        }
                        return kendo.stringify(model);
                    }
                }

            },
            requestEnd: function (e) {
                var type = e.type;
                if (type != "read") {
                    $('#grid-invoices').data('kendoGrid').dataSource.read();
                }
            },
            /*requestEnd: function (e) {
             var type = e.type;
             if (type != "read") {
             $('#grid-invoices').data('kendoGrid').dataSource.read();
             }
             },*/
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: {type: "number", editable: false, nullable: false, defaultValue: null},
                        money: {type: "number", editable: true, nullable: false, defaultValue: 0},
                        canUse: {type: "boolean", editable: true, nullable: false, defaultValue: false},
                        canAddMoney: {type: "boolean", editable: true, nullable: false, defaultValue: true},
                        isDeleted: {type: "boolean", editable: true, nullable: false, defaultValue: true}
                    }
                }
            }
        }
    );

    $("#grid-invoices").kendoGrid(
        {
            width: 200,
            height: 380,
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
                },
                {
                    field: "canUse",
                    title: "Confirmed",
                    template: "<input type='checkbox' #= (canUse === true) ? checked='checked' : '' # disabled />",
                    width: 70
                },
                {
                    command: [
                        {name: "destroy", text: "Delete"},
                        {name: "edit", text: {edit: "Edit", update: "Save", cancel: "Cancel"}}
                    ],
                    width: 100
                }
            ],
            dataBound: function () {
                var grid = this;
                var model;
                grid.tbody.find("tr[role='row']").each(function () {
                    $(this).find(".k-grid-delete").hide();
                    $(this).find(".k-grid-edit").hide();
                });

                grid.tbody.find("tr[role='row']").each(function () {
                    model = grid.dataItem(this);
                    if(model.isDeleted){
                        $(this).find(".k-grid-delete").show();
                    }
                    if(!model.canUse||!model.canAddMoney){
                        $(this).find(".k-grid-edit").show();
                    }
                });
            },
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
                if (!e.model.canAddMoney) {
                    $("#confirmed").css("display", "none");
                    $("#update_money").css("display", "");
                } else {
                    $("#confirmed").css("display", "");
                    $("#update_money").css("display", "none");
                }
            }
        }
    );

});