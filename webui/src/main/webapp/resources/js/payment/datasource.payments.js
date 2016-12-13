/**
 * Created by admin on 08.11.2016.
 */
var dsPayments = (function () {

    return {
        getInvoicesDataSource: function (options) {
            var settings = $.extend(options,
                {
                    async: false
                });

            return new kendo.data.DataSource({
                transport: {
                    read: {
                        url: "/getInvoicesInfo",
                        dataType: "json",
                        contentType: "application/json",
                        async: settings.async
                    }
                },
                sort: {field: "text", dir: "asc"}
            });
        },
        getPaymentTypesDataSource: function (options) {
            var settings = $.extend(options,
                {
                    async: false
                });

            return new kendo.data.DataSource({
                transport: {
                    read: {
                        url: "/getPaymentTypesInfo",
                        dataType: "json",
                        contentType: "application/json",
                        async: settings.async
                    }
                },
                sort: {field: "text", dir: "asc"}
            });
        },
        getMobileOperatorsDataSource: function (options) {
            var settings = $.extend(options,
                {
                    async: false
                });

            return new kendo.data.DataSource({
                transport: {
                    read: {
                        url: "/getMobileOperatorsInfo",
                        dataType: "json",
                        contentType: "application/json",
                        async: settings.async
                    }
                },
                sort: {field: "text", dir: "asc"}
            });
        }
        ,
        getInternetOperatorsDataSource: function (options) {
            var settings = $.extend(options,
                {
                    async: false
                });

            return new kendo.data.DataSource({
                transport: {
                    read: {
                        url: "/getInternetOperatorsInfo",
                        dataType: "json",
                        contentType: "application/json",
                        async: settings.async
                    }
                },
                sort: {field: "text", dir: "asc"}
            });
        },
        getPaymentStatusDataSource: function (options) {
            var settings = $.extend(options,
                {
                    async: false
                });

            return new kendo.data.DataSource({
                transport: {
                    read: {
                        url: "/getPaymentStatusInfo",
                        dataType: "json",
                        contentType: "application/json",
                        async: settings.async
                    }
                },
                sort: {field: "text", dir: "asc"}
            });
        }
    };


}());