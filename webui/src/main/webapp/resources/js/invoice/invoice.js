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
                parameterMap: function (model, operation) {
                    if (operation === "destroy" && model) {
                        return model.id;
                    }
                    if (operation === "create" || operation === "update" && model) {
                        return kendo.stringify(model);
                    }
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
                        canUse: {type: "boolean", editable: true, nullable: false, defaultValue: false}

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
                    field: "id",
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
                        {name: "edit", text: {edit: "Edit", update: "Save", cancel: "Cancel"}}
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