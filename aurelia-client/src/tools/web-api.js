import {HttpClient} from 'aurelia-http-client';
import {Router} from 'aurelia-router';

export class WebAPI {

  static inject() { return [Router]; }

  constructor(router){
    this.router = router;
  }

  getToken() {
      if (window.localStorage.getItem("X-AUTH-TOKEN") != null) {
        return window.localStorage.getItem("X-AUTH-TOKEN");
      }
      return "";
  }

  createClient() {
  	  return new HttpClient()
      .configure(x => {
      	x.withBaseUrl('http://localhost:8080/spring-server/ws/');
        x.withHeader("Content-Type", "application/json");
        x.withHeader("X-AUTH-TOKEN", this.getToken());
        //x.withCredentials(true);
      });
  }


  post(url, content){
    return this.createClient().post(url, content);
  }

  get(url) {
  	return this.createClient().get(url);
/*
  	.catch(error => {
    	console.log("error: redirect to login");
    	this.router.navigate("welcome");
    });
*/
  }
    

}