export function configure(aurelia) {
  aurelia.use
    .standardConfiguration()
    .developmentLogging();
    //.plugin('aurelia-animator-css');

  aurelia.start().then(a => {
  	a.setRoot();

  	setTimeout(function() {
  		fix_height(); //voir bootstrap.js
  	}, 500);
        

  });
}
