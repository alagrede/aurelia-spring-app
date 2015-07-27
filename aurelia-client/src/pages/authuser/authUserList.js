import {Router} from 'aurelia-router';
import {WebAPI} from '../../tools/web-api';


export class AuthUserList{

  static inject() { return [Router, WebAPI]; }

  heading = 'Gestion des utilisateurs';
  users = [];
  url = 'authuser/list';
  deleteurl = 'authuser/delete/';

  sortField = "id";
  pageNumber = 0;
  pageSize = 5;
  sortDirection = "Desc";


  constructor(router, http){
    this.router = router;
    this.http = http;
  }


  delete(selectedId) {
    return this.http.get(this.deleteurl + selectedId).then(response => {
        console.log("Delete user:" + selectedId );
        this.refreshPage();                  
    });              
  }
 
  params() {
    return "?sortField="+this.sortField+"&pageNumber="+this.pageNumber+"&pageSize="+this.pageSize+"&sortDirection="+this.sortDirection;
  }

  refreshPage() {
    return this.http.get(this.url + this.params()).then(response => {
        this.users = response.content;
    });              
  }

  nextPage() {
    this.pageNumber += 1;
    return this.refreshPage();              
  }

 previousPage() {
    if(this.pageNumber > 0)
      this.pageNumber -= 1;
    return this.refreshPage();              
  }

  activate(){
    return this.http.get(this.url).then(response => {
        this.users = response.content;
    });
  }

/*  canDeactivate(){
    return confirm('Are you sure you want to leave?');
  }
*/

  staffIcon(bool) {
    if (bool) {
      return "fa fa-check boolean-true";
    } else {
      return "fa fa-times boolean-false"
    }
  }

  navigate(userId) {
    this.router.navigate(this.router.generate("useradd", {id:userId}));
  }

  sort(libelle) {

    // on met à jour l'icone des tris    
    $("th a", this.myTable).removeClass();
    $("th a", this.myTable).addClass("unsorted");

    if (this.sortDirection == "Desc") {
      this.sortDirection = "Asc";
      this.$event.target.className = "sortasc";
    } else {
      this.sortDirection = "Desc";  
      this.$event.target.className = "sortdesc";
    }
      

    // On change la colonne de tri
    this.sortField = libelle;
    
    return this.refreshPage();              
  }

}


