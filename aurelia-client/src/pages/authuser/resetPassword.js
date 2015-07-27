import {Router} from 'aurelia-router';
import {WebAPI} from '../../tools/web-api';
import {Util} from '../../tools/util';


export class ResetPassword {

  static inject() { return [Router, WebAPI, Util]; }
  
  resetPasswordUrl = "/resetPassword";
  
  redirecturl = window.location.protocol + "//" + window.location.host + "/login.html"

  command = {
  	mail : "",
    redirecturl: this.redirecturl
  };


  constructor(router, http, util){
    this.router = router;
    this.http = http;
    this.util = util;
  }


  resetPassword() {

  	return this.http.post(this.resetPasswordUrl, this.command).then(response => {
        
        this.router.navigate("login"); //TODO redirect parameters

    }).catch(response => {
      	console.log(response.content);
    });              
  }


}