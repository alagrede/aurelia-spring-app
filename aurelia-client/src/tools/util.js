
export class Util {

  constructor(){

  }

  displayErrors(errors) {
    
    for (var i = 0; i < errors.length; i++) {
      if ($("#"+errors[i].field).length) {
        $("#"+errors[i].field).parent().addClass("has-error");
        $("<span class='form-error'>" + errors[i].defaultMessage + "</span>").insertAfter("#"+errors[i].field);
      } else {
        //alert(errors[i].defaultMessage);
        console.log("control not found:" + errors[i].field + "=>" + errors[i].defaultMessage);
      }
      
    };

  }    

  removeErrors() {
    $(".form-group").removeClass("has-error");
    $(".form-error").remove();
  }

}