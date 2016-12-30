/**
 * Created by admin on 08.11.2016.
 */

$(document).ready(function () {


    var dataSource = new kendo.data.DataSource(
        {
            transport: {
                read: {
                    url: "/getServices",
                    type: "Get",
                    dataType: "json",
                    contentType: "application/json"
                },
                destroy: {
                    url: function (options) {
                        return "/deleteService/" + options.id;
                    },
                    type: "Delete",
                    dataType: "json",
                    contentType: "application/json"
                },
                create: {
                    url: "/saveService",
                    type: "Post",
                    dataType: "json",
                    contentType: "application/json"
                },
                update: {
                    url: "/updateService",
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
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: {type: "number", editable: false, nullable: false, defaultValue: null},
                        name: {
                            type: "string", editable: true, nullable: false,
                            validation: {
                                required: true,
                                nameValidator: function (input) {
                                    if (input.is("[id='service_name']")) {
                                        var items = dataSource.data();

                                        input.attr("data-nameValidator-msg", "Service with current name exist");
                                        var exists = $.grep(items, function (item) {
                                            return item.name.toUpperCase() == input.val().toUpperCase();
                                        });
                                        if (exists != null && exists.length > 1)
                                            return false;
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                }
            },
            pageSize: 5,
            sort: {field: "name", dir: "asc"}
        }
    );

    $("#grid-services").kendoGrid(
        {
            toolbar: [{name: "create", text: "Add service"}],
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
                    itemsPerPage: "services",
                    display: "{0}-{1} from {2} services",
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
                    title: "Service"
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
                    if (model.id == 1 || model.id == 2) {
                        $(this).find(".k-grid-delete").hide();
                        $(this).find(".k-grid-edit").hide();
                    }
                });
            },
            edit: function (e) {
                e.model.dirty = true;
            }
        }
    );


});
