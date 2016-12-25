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
                        money: {type: "number", editable: true, nullable: false, defaultValue: 0}
                    }
                },
                parse: function (data) {
                    if (!Array.isArray(data) && data.canUse == false) {
                        $("body").append("<div class=box>Wait while operator confirm your invoice </div>");
                        setTimeout(function () {
                            $('.box').fadeOut('fast')
                        }, 5000);
                    }
                    return data;
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
            toolbar: [{name: "create", text: "Add invoice"}],
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
                    command: [
                        {name: "destroy", text: "Delete"},
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