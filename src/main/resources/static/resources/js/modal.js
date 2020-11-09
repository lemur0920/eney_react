$(document).ready(function() {
    var id = null;
    var count = null;
    var number = null;
   /* $(".modal").on("click", function (e) {
        if ($(e.target).hasClass("modal")) {
            modal.hide();
        }
    });*/
})

var modal = {
    show: function(selector) {
        $("#" + selector).show();
    },

    hide: function() {
        $(".modal:visible").hide();
    }

}

