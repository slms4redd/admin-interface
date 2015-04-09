import Ember from 'ember';

export default Ember.Route.extend({
  model: function () {
    return this.store.find('group');
  },
	
  setupController: function(controller, model) {
  	this.store.filter('group', function (g) {
  		return g.get('root');
  	}).then(function (root) {
      controller.set('content', root.objectAt(0).get('children')); // TODO: are both needed?
      controller.set('rootElement', root.objectAt(0).get('children'));
    });
  }
});
