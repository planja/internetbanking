/**
 * Created by admin on 08.11.2016.
 */
var dsUsers = (function () {

    var path = "/getUserInfo";


    return {
        getUsersDataSource: function (options) {
            var settings = $.extend(options,
                {
                    async: false
                });

            return new kendo.data.DataSource({
                transport: {
                    read: {
                        url: path,
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